package br.com.inatel.quotation_management.controller;

import java.util.HashMap;
import java.util.Map;

import br.com.inatel.quotation_management.model.Stock;
import br.com.inatel.quotation_management.model.StockQuote;


public class StockDto {
	
	private String id;
	private String stockId;
	private Map<String, String> quotes = new HashMap<>();
	
	public StockDto(Stock stock) {
		this.id = stock.getId();
		this.stockId = stock.getStockId();		
		for (StockQuote quote : stock.getQuotes()) {
			this.quotes.put(quote.getDate().toString(), quote.getValue().toString());
		}
	}

	public String getId() {
		return id;
	}

	public String getStockId() {
		return stockId;
	}

	public Map<String, String> getQuotes() {
		return quotes;
	}
	
	

}
