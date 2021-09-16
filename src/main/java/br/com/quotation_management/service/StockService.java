package br.com.quotation_management.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockService {

	private RestTemplate restTemplate = new RestTemplate();
	
//	@Autowired
	private String stockManagerURL = "http://localhost:8080";
	
//	@Autowired
//	public StockService(String stockManagerURL) {
//		stockManagerURL = "http://localhost:8080";
//		this.stockManagerURL = stockManagerURL;
//	}
	
	public List<StockRegister> listStocks() {
		StockRegister[] stockList = restTemplate.getForObject(stockManagerURL + "/stock", StockRegister[].class);
		return Arrays.asList(stockList);
	}
	
}
