package br.com.inatel.quotation_management.service;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockService {

	private RestTemplate restTemplate = new RestTemplate();
	
	private String stockManagerURL = "http://localhost:8080";
	
	@Cacheable(value = "stockCache")
	public List<StockRegister> listStocks() {
		System.out.println("cache accessed");
		StockRegister[] stockList = restTemplate.getForObject(stockManagerURL + "/stock", StockRegister[].class);
		return Arrays.asList(stockList);
	}
	
	//triggered whenever the application is started
	@EventListener(ApplicationReadyEvent.class)
	public void registerApplicationInStockManager() {
		JSONObject body = new JSONObject();
		body.put("host", "localhost");
		body.put("port", 8081);
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(body.toString(), header);
		restTemplate.postForObject(stockManagerURL + "/notification", request, String.class);
	}
	
}
