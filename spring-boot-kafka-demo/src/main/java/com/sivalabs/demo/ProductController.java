package com.sivalabs.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

	final ProductRepository productRepository;

	@PostMapping
	public Map<String, String> create(@RequestBody Product product) {
		productRepository.save(product);
		return Map.of("result", "done!");
	}

	@GetMapping
	public List<Product> findAll() {
		return productRepository.findAll();
	}

}
