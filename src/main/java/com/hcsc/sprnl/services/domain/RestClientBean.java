package com.hcsc.sprnl.services.domain;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class RestClientBean {
	
	private Long id;
	private String from;
	private String to;
	private int conversionMultiple;
	private int amount;
	private String details;
	  private int port;
	private double totalCalculatedAmount;
	
/*	private Long id;
	  private String from;
	  private String to;
	  private BigDecimal quantity;
	  private BigDecimal conversionMultiple;
	  private BigDecimal totalCalculatedAmount;
	  private int port;
	  private String details;
	  */
	
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public int getConversionMultiple() {
		return conversionMultiple;
	}
	public void setConversionMultiple(int conversionMultiple) {
		this.conversionMultiple = conversionMultiple;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public double getTotalCalculatedAmount() {
		return totalCalculatedAmount;
	}
	public void setTotalCalculatedAmount(double totalCalculatedAmount) {
		this.totalCalculatedAmount = totalCalculatedAmount;
	}
	
	public RestClientBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}
	public RestClientBean(Long id, String from, String to, int conversionMultiple, int amount, String details, int port,
			double totalCalculatedAmount) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
		this.amount = amount;
		this.details = details;
		this.port = port;
		this.totalCalculatedAmount = totalCalculatedAmount;
	}
	@Override
	public String toString() {
		return "RestClientBean [id=" + id + ", from=" + from + ", to=" + to + ", conversionMultiple="
				+ conversionMultiple + ", amount=" + amount + ", details=" + details + ", port=" + port
				+ ", totalCalculatedAmount=" + totalCalculatedAmount + "]";
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	

	
}

