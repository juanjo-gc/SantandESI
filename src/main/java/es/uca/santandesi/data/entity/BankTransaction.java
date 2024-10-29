package es.uca.santandesi.data.entity;

import java.math.BigDecimal;

public class BankTransaction {
	
	private String id;
	public enum TransactionStatus {REQUESTED, ACCEPTED, REJECTED}
	private TransactionStatus transactionStatus;
	private String issuer;
	public enum TransactionType {WITHDRAWAL, DEPOSIT}
	private TransactionType transactionType;
	private String concept;
	private String iban;
	private BigDecimal value;

	public BankTransaction() {}

	public BankTransaction(Movimiento movimiento, String ibanDestino) {
		id = null;
		transactionStatus = TransactionStatus.REQUESTED;
		//issuer = movimiento.getOrdenante().getUsuarios().get(0).getNombre();
		issuer = "prueba";//prueba
		transactionType = TransactionType.DEPOSIT;
		concept = movimiento.getConcepto();
		iban = ibanDestino;
		value = movimiento.getImporte();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	public String getIssuer() {
		return issuer;
	}
	
	public TransactionType getTransactionType() {
		return transactionType;
	}
	
	public String getConcept() {
		return concept;
	}
	
	public String getIban() {
		return iban;
	}
	
	public BigDecimal getValue() {
		return value;
	}
}
