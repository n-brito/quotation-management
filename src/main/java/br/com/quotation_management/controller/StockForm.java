package br.com.quotation_management.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.quotation_management.model.Stock;
import br.com.quotation_management.model.StockQuote;

public class StockForm {

	@NotNull
	@NotEmpty
	private String stockId;
	
	@NotNull
	@NotEmpty
	private Map<String, String> quotes;

	public String getStockId() {
		return stockId;
	}

	public Map<String, String> getStockQuotes() {
		return quotes;
	}

	public List<StockQuote> generateStockQuoteList(Stock stock) {
		List<StockQuote> quotes = new ArrayList<>();
		
		for (Map.Entry<String, String> quoteEntry : this.quotes.entrySet()) {
			StockQuote quote = new StockQuote();
			quote.setDate(LocalDate.parse(quoteEntry.getKey()));
			quote.setValue(new BigDecimal(quoteEntry.getValue()));
			quote.setStock(stock);
			
			quotes.add(quote);
		}
		
		return quotes;
	}
	
}
