package es.uca.santandesi.data.entity;

import java.math.BigDecimal;

public class CreditCardPayment {
	
	public enum PaymentStatus {REQUESTED, SECURITY_TOKEN_REQUIRED, ACCEPTED, REJECTED}
	private PaymentStatus paymentStatus;
	private String id;
	private String cardNumber;
	private String cardholderName;
	private int month;
	private int year;
	private String csc;
	private BigDecimal value;	
	public enum Type {OFFLINE, ONLINE}
	private Type type;
	private Integer securityToken;
	private String shop;

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public String getCardholderName() {
		return cardholderName;
	}
	
	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}
	
	public String getCsc() {
		return csc;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	public Type getType() {
		return type;
	}
	
	public Integer getSecurityToken() {
		return securityToken;
	}
	public void setSecurityToken(Integer securityToken) {
		this.securityToken = securityToken;
	}
	
	public String getShop() {
		return shop;
	}
}
