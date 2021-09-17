package br.com.inatel.quotation_management.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.inatel.quotation_management.service.StockRegister;
import br.com.inatel.quotation_management.service.StockService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class StockQuoteControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private StockService stockService;
	
	private List<StockRegister> stockList;
	
	private JSONObject quotes;
	private JSONObject body;
	
	//creating mock test information
	@Before
	void populateMockStockList() {
		Mockito.when(stockService.listStocks()).thenReturn(stockList);
		
		this.stockList = new ArrayList<>();
		this.stockList.add(new StockRegister("petr4", "Petrobras PN"));
		this.stockList.add(new StockRegister("vale5", "Vale do Rio Doce PN"));
	}
	
	//mock test that should create new stock quotes
	@Test
	void shouldGenerateNewOperation() throws Exception {
		
		populateMockStockList();
		
		quotes = new JSONObject();		//creating json quote list with mock test information
		quotes.put("2021-01-01", "10");
		quotes.put("2021-01-02", "20");
		quotes.put("2021-01-03", "30");

		body = new JSONObject();		//creating json body with valid mock test information
		body.put("stockId", "petr4");
		body.put("quotes", quotes);
		
		mockMvc.perform(
				MockMvcRequestBuilders.post("/quote").content(body.toString())	// mocks access to /quote uri
				.contentType(MediaType.APPLICATION_JSON))						// passes json with content in body to test
				.andExpect(MockMvcResultMatchers.status().is(201));				// should return status created
		
		body = new JSONObject();		//creating json body with invalid mock test information
		body.put("stockId", "test1");
		body.put("quotes", quotes);
				
		mockMvc.perform(
				MockMvcRequestBuilders.post("/quote").content(body.toString())	// mocks access to /quote uri
				.contentType(MediaType.APPLICATION_JSON))						// passes json with content in body to test
				.andExpect(MockMvcResultMatchers.status().is(502));				// should return status bad gateway
	}

	//mock test that should return all stock quotes
	@Test
	void listsAllStockQuotes() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/quote")	// mocks access to /quote uri
			.contentType(MediaType.APPLICATION_JSON))			// expects valid information json 
			.andExpect(MockMvcResultMatchers.status().is(200));	// should return status ok
	}
	
	//mock test that should return all stock quotes by the referred stockId
	@Test
	void listsStockQuotesByStockId() throws Exception {
		
		populateMockStockList();
		
		String validStockId = "petr4";
		String invalidStockId = "test1";
		
		if(stockList.toString().contains(validStockId)) {
			mockMvc.perform(MockMvcRequestBuilders.get("/quote/"+validStockId)	// testing stockId declared in stock-manager	
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200));			// should return status ok
		}
		
		if(!stockList.toString().contains(invalidStockId)) {
			mockMvc.perform(MockMvcRequestBuilders.get("/quote/"+invalidStockId) // testing stockId not declared in stock-manager
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().is(404));			// should return status not found
		}		
	}

}

