package br.com.quotation_management.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	private StockQuoteRepository stockquoteRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> create(@RequestBody @Valid StockForm form, UriComponentsBuilder uriBuilder) {
		// log.info("New Quote Operation creation started");
//		List<Stock> stockList = stockService.getStockList();
//		List<String> stockIdList = stockList.stream().map(Stock::getId).collect(Collectors.toList());
		
//		if (!stockIdList.contains(form.getStockId())) {
////			log.error("Stock Id sent not registered on Stock Manager API");
//			return ResponseEntity
//					.status(HttpStatus.NOT_FOUND)
//					.body(new ErrorFormDto("stockId", "There is no stock registered with the id : " + form.getStockId()));
//		}
//		
//		if(!form.isQuotesDatesValid()) {
////			log.error("Quote with invalid date found");
//			return ResponseEntity
//					.status(HttpStatus.BAD_REQUEST)
//					.body(new ErrorFormDto("quotes", "Invalid quote date found"));
//		}
//		
//		if(!form.isQuotesValuesValid()) {
////			log.error("Quote with invalid value found");
//			return ResponseEntity
//					.status(HttpStatus.BAD_REQUEST)
//					.body(new ErrorFormDto("quotes", "Invalid quote value found"));
//		}
//		
//		for (Map.Entry<String, String> quoteEntry : form.getQuotes().entrySet()) {
//			Quote existingQuote = quoteRepository.findByOperationStockIdAndDate(form.getStockId(), LocalDate.parse(quoteEntry.getKey()));
//			
//			if (existingQuote != null) {
////				log.error("Quote with date already registered found");
//				return ResponseEntity
//						.status(HttpStatus.BAD_REQUEST)
//						.body(new ErrorFormDto("quotes", "There is already a quote registered on the date " + quoteEntry.getKey() + " for the stock of id : " + form.getStockId()));
//			}
//		}
		
		Stock stock = new Stock();
		stock.setStockId(form.getStockId());
		
		List<StockQuote> quotes = form.generateStockQuoteList(stock);
		stock.setStockQuotes(quotes);
		
//		stock = stockRepository.save(stock);
//		log.info("Quote Operation successfully created");
		
		URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(form.getStockId()).toUri();
		return ResponseEntity.created(uri).body(new StockDto(stock));
	}

}
