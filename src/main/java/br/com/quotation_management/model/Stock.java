package br.com.quotation_management.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
;

public class Stock {

	@Id
	private String id;
	private String stockId;
	
	@OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
	private List<StockQuote> quotes = new ArrayList<>();
	
	public Stock() {
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public List<StockQuote> getStockQuotes() {
		return quotes;
	}

	public void setStockQuotes(List<StockQuote> quotes) {
		this.quotes = quotes;
	}
	
}
