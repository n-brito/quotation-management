package br.com.inatel.quotation_management.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Stock {

	@Id
	private String id;
	private String stockId;
	
	//mappedBy: the quotes will be mapped by their corresponding stock
	@OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
	private List<StockQuote> quotes = new ArrayList<>();
	
	//assigning an id in the required pattern
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

	public List<StockQuote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<StockQuote> quotes) {
		this.quotes = quotes;
	}
	
}
