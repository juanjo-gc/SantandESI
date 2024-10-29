package es.uca.santandesi.controller.transactionApi;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import es.uca.santandesi.data.entity.BankTransaction;
import es.uca.santandesi.data.entity.BankTransaction.TransactionStatus;
import es.uca.santandesi.data.entity.CreditCardPayment;
import es.uca.santandesi.data.entity.CreditCardPayment.PaymentStatus;
import es.uca.santandesi.data.service.MovimientoService;
import es.uca.santandesi.data.service.PagoService;

@RestController
@RequestMapping("/api")
public class TransactionApiController {

	@Autowired
	private MovimientoService movimientoService;
	@Autowired
	private PagoService pagoService;

	@PostMapping("/transactions")
	BankTransaction procesar(@RequestBody BankTransaction bankTransaction) {
		bankTransaction = movimientoService.TransactionApi(bankTransaction);
		
		if(bankTransaction.getTransactionStatus() == TransactionStatus.ACCEPTED)			
			System.out.println(">>>> Registrado nuevo movimiento...");
		
		System.out.println(">>>> Payload devuelto: BankTransaction"
			+ " [id=" + bankTransaction.getId() 
			+ ", transactionStatus=" + bankTransaction.getTransactionStatus() 
			+ ", issuer=" + bankTransaction.getIssuer() 
			+ ", transactionType=" + bankTransaction.getTransactionType() 
			+ ", concept=" + bankTransaction.getConcept() 
			+ ", iban=" + bankTransaction.getIban() 
			+ ", value=" + bankTransaction.getValue() + "]");
		return bankTransaction;
	}
	
	public static void enviar(BankTransaction bankTransaction) {		
		String uri = "http://localhost:8181/api/transactions";
		RestTemplate restTemplate = new RestTemplate();

	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    headers.setContentType((MediaType.APPLICATION_JSON));
		HttpEntity<BankTransaction> entity = new HttpEntity<>(bankTransaction, headers);
		
		System.out.println("Invocando Operación POST sobre: " + uri);

		restTemplate.exchange(uri, HttpMethod.POST, entity, BankTransaction.class);
		
		System.out.println("Payload enviado: BankTransaction"
			+ " [id=" + bankTransaction.getId() 
			+ ", transactionStatus=" + bankTransaction.getTransactionStatus() 
			+ ", issuer=" + bankTransaction.getIssuer() 
			+ ", transactionType=" + bankTransaction.getTransactionType() 
			+ ", concept=" + bankTransaction.getConcept() 
			+ ", iban=" + bankTransaction.getIban() 
			+ ", value=" + bankTransaction.getValue() + "]");
	}
	
	@PostMapping("/payments")
	CreditCardPayment procesar(@RequestBody CreditCardPayment creditCardPayment) {
		creditCardPayment = pagoService.TransactionApi(creditCardPayment);
		
		if(creditCardPayment.getPaymentStatus() == PaymentStatus.ACCEPTED && creditCardPayment.getSecurityToken() == null)
			System.out.println(">>>> Compra aceptada y no requiere confirmación por ser <=10 euros.");
		else {
			if(creditCardPayment.getPaymentStatus() == PaymentStatus.SECURITY_TOKEN_REQUIRED)
				System.out.println(">>>> Recibida petición de compra. Se requiere autorización mediante token de seguridad...");
			if(creditCardPayment.getPaymentStatus() == PaymentStatus.ACCEPTED)
				System.out.println(">>>> Recibida autorización de compra. Compra aceptada.");
		}
		
		System.out.println(">>>> Payload devuelto: CreditCardPayment"
				+ " [paymentStatus=" + creditCardPayment.getPaymentStatus() 
				+ ", id=" + creditCardPayment.getId() 
				+ ", cardNumber=" + creditCardPayment.getCardNumber()
				+ ", cardholderName=" + creditCardPayment.getCardholderName()
				+ ", month=" + creditCardPayment.getMonth()
				+ ", year=" + creditCardPayment.getYear()
				+ ", csc=" + creditCardPayment.getCsc()
				+ ", value=" + creditCardPayment.getValue()
				+ ", type=" + creditCardPayment.getType()
				+ ", securityToken=" + creditCardPayment.getSecurityToken()
				+ ", shop=" + creditCardPayment.getShop() + "]");
		return creditCardPayment;
	}
	
	/*
	public static void enviar(CreditCardPayment creditCardPayment) {		
		String uri = "http://localhost:8181/api/payments";
		RestTemplate restTemplate = new RestTemplate();

	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    headers.setContentType((MediaType.APPLICATION_JSON));
		HttpEntity<CreditCardPayment> entity = new HttpEntity<>(creditCardPayment, headers);
		
		System.out.println("Invocando Operación POST sobre: " + uri);

		restTemplate.exchange(uri, HttpMethod.POST, entity, BankTransaction.class);
		
		System.out.println("Payload enviado: CreditCardPayment"
			+ " [paymentStatus=" + creditCardPayment.getPaymentStatus() 
			+ ", id=" + creditCardPayment.getId() 
			+ ", cardNumber=" + creditCardPayment.getCardNumber()
			+ ", cardholderName=" + creditCardPayment.getCardholderName()
			+ ", month=" + creditCardPayment.getMonth()
			+ ", year=" + creditCardPayment.getYear()
			+ ", csc=" + creditCardPayment.getCsc()
			+ ", value=" + creditCardPayment.getValue()
			+ ", type=" + creditCardPayment.getType()
			+ ", securityToken=" + creditCardPayment.getSecurityToken()
			+ ", shop=" + creditCardPayment.getShop() + "]");
	}
	*/
}
