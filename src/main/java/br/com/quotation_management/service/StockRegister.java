package br.com.quotation_management.service;

public class StockRegister {
	
	private String id;
	private String description;
	
	public StockRegister() {}
	
	public StockRegister(String id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}

}
