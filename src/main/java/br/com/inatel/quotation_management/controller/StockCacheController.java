package br.com.inatel.quotation_management.controller;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stockcache")

@Transactional
public class StockCacheController {
	
	@DeleteMapping
	@Transactional
	@CacheEvict(value = "stockCache", allEntries = true)
	public ResponseEntity<?> cleanStockCache() {
		System.out.println("cache cleaned");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Stock Cache cleaned successfully");
	}
	
}
