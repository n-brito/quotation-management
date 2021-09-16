package br.com.quotation_management.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.quotation_management.model.Stock;
import br.com.quotation_management.model.StockQuote;
import br.com.quotation_management.repository.StockQuoteRepository;
import br.com.quotation_management.repository.StockRepository;

@RestController
@RequestMapping("/quote")
public class StockQuoteController {
	
	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private StockQuoteRepository stockQuoteRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> createStockQuote(@RequestBody StockForm form, UriComponentsBuilder uriBuilder) {
					
		Stock stock = stockRepository.findByStockId(form.getStockId());
		
		if (stock == null) {
			stock = new Stock();
			stock.setStockId(form.getStockId());
			
			List<StockQuote> quotes = form.generateStockQuoteList(stock);
			stock.setQuotes(quotes);
			
			stock = stockRepository.save(stock);
									
		} else {
			List<StockQuote> quotesList = stock.getQuotes();
			List<StockQuote> newQuotesList = form.generateStockQuoteList(stock);
			
			quotesList.addAll(newQuotesList);
			stock.setQuotes(quotesList);
		}
		
		URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(form.getStockId()).toUri();

		return ResponseEntity.created(uri).body(new StockDto(stock));		
	}
	
	@GetMapping()
	public ResponseEntity<?> listAll() {
		return ResponseEntity.ok(stockRepository.findAll().stream().map(StockDto::new).collect(Collectors.toList()));
	}
	
	@GetMapping("/{stockId}")
	public ResponseEntity<?> getByStockId(@PathVariable("stockId") String stockId) {
		return ResponseEntity.ok(new StockDto(stockRepository.findByStockId(stockId)));
	}
	

}
