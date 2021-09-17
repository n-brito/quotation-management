package br.com.inatel.quotation_management.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.inatel.quotation_management.model.Stock;
import br.com.inatel.quotation_management.repository.StockQuoteRepository;
import br.com.inatel.quotation_management.repository.StockRepository;
import br.com.inatel.quotation_management.service.StockRegister;
import br.com.inatel.quotation_management.service.StockService;

@RestController
@RequestMapping("/quote")
public class StockQuoteController {
	
	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private StockQuoteRepository stockQuoteRepository;
	@Autowired
	private StockService stockService;
	
	//adds a new quotation to the database
	@PostMapping
	@Transactional
	public ResponseEntity<?> createStockQuote(@RequestBody StockForm form, UriComponentsBuilder uriBuilder) {
		
		List<StockRegister> stockList = stockService.listStocks();
		List<String> stockIdList = stockList.stream().map(StockRegister::getId).collect(Collectors.toList());
		
		//allows quote addition to proceed only if stockId passed exists on stock-manager application
		if (!stockIdList.contains(form.getStockId())) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Operation not allowed: No stock registered under the stockId " + form.getStockId());
		}
							
		Stock stock = new Stock(form.getStockId());
		
		stock.setQuotes(form.generateStockQuoteList(stock));
			
		stockRepository.save(stock);
		
		URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(form.getStockId()).toUri();

		return ResponseEntity.created(uri).body(new StockDto(stock));		
	}
	
	//lists all quotes in the database
	@GetMapping()
	public ResponseEntity<?> listAll() {
		List<Stock> stockList = stockRepository.findAll();
		if (stockList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No stocks registered");
		}
		return ResponseEntity.ok(stockList.stream().map(StockDto::new).collect(Collectors.toList()));
	}
	
	//lists all quotes in the database related to the referred stockId
	@GetMapping("/{stockId}")
	public ResponseEntity<?> getByStockId(@PathVariable("stockId") String stockId) {
		List<Stock> stockListByStockId = stockRepository.findByStockId(stockId);
		if (stockListByStockId.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No stock registered under the stockId " + stockId);
		}
		return ResponseEntity.ok(stockListByStockId.stream().map(StockDto::new).collect(Collectors.toList()));
	}
	

}
