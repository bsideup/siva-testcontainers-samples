package com.sivalabs.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Slf4j
class ProductPriceChangedEventHandlerTest implements IntegrationTest {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private ProductRepository productRepository;

	@BeforeEach
	void setUp() {
		Product product = new Product(null, "P100", "Product One", BigDecimal.TEN);
		productRepository.save(product);
	}

	@Test
	void shouldHandleProductPriceChangedEvent() {
		ProductPriceChangedEvent event = new ProductPriceChangedEvent("P100", new BigDecimal("14.50"));

		log.info("Publishing ProductPriceChangedEvent with ProductCode: {}", event.getProductCode());
		kafkaTemplate.send("product-price-changes", event.getProductCode(), event);

		await().pollInterval(Duration.ofSeconds(3)).atMost(10, SECONDS).untilAsserted(() -> {
			Optional<Product> optionalProduct = productRepository.findByCode("P100");
			assertThat(optionalProduct).isPresent();
			assertThat(optionalProduct.get().getCode()).isEqualTo("P100");
			assertThat(optionalProduct.get().getPrice()).isEqualTo(new BigDecimal("14.50"));
		});
	}

}