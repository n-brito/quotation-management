package br.com.inatel.quotation_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.quotation_management.model.Stock;

public interface StockRepository extends JpaRepository<Stock, String>{
	
	List<Stock> findByStockId(String stockId);

}
