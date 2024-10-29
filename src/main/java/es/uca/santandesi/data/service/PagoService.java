package es.uca.santandesi.data.service;

import es.uca.santandesi.data.entity.CreditCardPayment;
import es.uca.santandesi.data.entity.Pago;
import es.uca.santandesi.data.entity.Tarjeta;
import es.uca.santandesi.data.entity.CreditCardPayment.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    private final PagoRepository repository;
	@Autowired
    private TarjetaRepository tarjetaRepository;

    @Autowired
    public PagoService(PagoRepository repository) {
        this.repository = repository;
    }

    public Optional<Pago> get(UUID id) {
        return repository.findById(id);
    }

    public Pago update(Pago entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public int count() {
        return (int) repository.count();
    }
    
    public List<Pago> getPagosTarjeta(Tarjeta tarjeta) {
    	return repository.findByTarjeta(tarjeta);
    }

	public CreditCardPayment TransactionApi(CreditCardPayment creditCardPayment) {
		Pago pago;
		if(creditCardPayment.getId() == null) {
			pago = new Pago(creditCardPayment.getValue(), creditCardPayment.getShop(), LocalDate.now(),
				tarjetaRepository.findByNumeroTarjeta(creditCardPayment.getCardNumber()));
		} else {
			pago = new Pago(UUID.fromString(creditCardPayment.getId()), creditCardPayment.getValue(), creditCardPayment.getShop(), LocalDate.now(),
					tarjetaRepository.findByNumeroTarjeta(creditCardPayment.getCardNumber()));
		}
		if(pago.getTarjeta() != null || pago.getImporte().compareTo(BigDecimal.valueOf(50)) <= 0) { //Si importe <= 50
			creditCardPayment.setId(pago.getId().toString());
			if(pago.getImporte().compareTo(BigDecimal.valueOf(10)) <= 0) {
				repository.save(pago);
				creditCardPayment.setPaymentStatus(PaymentStatus.ACCEPTED);
			} else {
				//Simulación de envío de SMS si el pago es online y
				// comprobación simulada de un pin si el pago es offline o del SMS si es online
				if(creditCardPayment.getPaymentStatus() == PaymentStatus.REQUESTED) {
					creditCardPayment.setPaymentStatus(PaymentStatus.SECURITY_TOKEN_REQUIRED);
				} else {
					repository.save(pago);
					creditCardPayment.setPaymentStatus(PaymentStatus.ACCEPTED);
				}
			}
		} else {
			creditCardPayment.setPaymentStatus(PaymentStatus.REJECTED);
		}
		return creditCardPayment;
	}
	
}
