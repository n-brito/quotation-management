package br.com.quotation_management.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.quotation_management.model.StockQuote;

public interface StockQuoteRepository extends JpaRepository<StockQuote, Long>{
	
	//StockQuote findByOperationStockIdAndDate(String stockId, LocalDate date);

}
