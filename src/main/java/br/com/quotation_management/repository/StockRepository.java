package br.com.quotation_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.quotation_management.model.Stock;

public interface StockRepository extends JpaRepository<Stock, String>{
	
	Stock findByStockId(String stockId);

}
