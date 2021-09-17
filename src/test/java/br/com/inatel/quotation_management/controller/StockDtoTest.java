package br.com.inatel.quotation_management.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.inatel.quotation_management.model.Stock;
import br.com.inatel.quotation_management.model.StockQuote;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class StockDtoTest {
	
	private Stock stock;
	private StockDto stockDto;
	private Map<String, String> quotes = new HashMap<>();
	
	//testing with mock information if dto is being created correctly
	@Test
	void creatingNewStockDto() {
		String validStockId = "petr4";
		this.stock = new Stock(validStockId);
		
		quotes.put("2021-01-01", "10");
		quotes.put("2021-01-02", "20");
		quotes.put("2021-01-03", "30");
		
		List<StockQuote> quotesList = new ArrayList<>();
		for (Map.Entry<String, String> quoteEntry : this.quotes.entrySet()) {
			StockQuote quote = new StockQuote();
			quote.setDate(LocalDate.parse(quoteEntry.getKey()));
			quote.setValue(new BigDecimal(quoteEntry.getValue()));
			quote.setStock(this.stock);
			
			quotesList.add(quote);
		}
		
		stock.setQuotes(quotesList);
		
		this.stockDto = new StockDto(this.stock);
		Assert.assertEquals(this.stockDto.getId(), this.stock.getId());
		Assert.assertEquals(this.stockDto.getStockId(), this.stock.getStockId());
		Assert.assertTrue(this.stockDto.getQuotes().equals(this.quotes));
	}

}
