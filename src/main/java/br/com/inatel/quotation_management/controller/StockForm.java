package br.com.inatel.quotation_management.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.inatel.quotation_management.model.Stock;
import br.com.inatel.quotation_management.model.StockQuote;

public class StockForm {

//	@NotNull
//	@NotEmpty
	private String stockId;
	
//	@NotNull
//	@NotEmpty
	private Map<String, String> quotes;

	public String getStockId() {
		return stockId;
	}

	public Map<String, String> getQuotes() {
		return quotes;
	}

	public List<StockQuote> generateStockQuoteList(Stock stock) {
		List<StockQuote> quotes = new ArrayList<>();
		
		for (Map.Entry<String, String> entry : this.quotes.entrySet()) {
			StockQuote quote = new StockQuote();
			quote.setDate(LocalDate.parse(entry.getKey()));
			quote.setValue(new BigDecimal(entry.getValue()));
			quote.setStock(stock);
			
			quotes.add(quote);
		}
		
		return quotes;
	}
	
}
