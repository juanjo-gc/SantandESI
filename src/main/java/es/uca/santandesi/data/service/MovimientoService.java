package es.uca.santandesi.data.service;

import es.uca.santandesi.controller.transactionApi.TransactionApiController;
import es.uca.santandesi.data.entity.BankTransaction;
import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.data.entity.Movimiento;
import es.uca.santandesi.data.entity.BankTransaction.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class MovimientoService {

	private final MovimientoRepository repository;
	private final CuentaRepository cuentaRepo;

	public class InvalidTransactionException extends Exception {
		public InvalidTransactionException(String message) {
			super(message);
		}
	}

	@Autowired
	public MovimientoService(MovimientoRepository repository, CuentaRepository cuentaRepo) {
		this.repository = repository;
		this.cuentaRepo = cuentaRepo;
	}

	public Optional<Movimiento> get(UUID id) {
		return repository.findById(id);
	}

	public Movimiento update(Movimiento entity) {
		return repository.save(entity);
	}

	public void delete(UUID id) {
		repository.deleteById(id);
	}

	public int count() {
		return (int) repository.count();
	}
	
	public List<Movimiento> getMovimientosCuenta(Cuenta cuenta) {
		List<Movimiento> movimientos = repository.findByBeneficiario(cuenta);
		movimientos.addAll(repository.findByOrdenante(cuenta));
		return movimientos;
	}

	public void procesarTransacion(BigDecimal importe, String concepto, String ibanOrigen, String ibanDestino) throws InvalidTransactionException {

		Cuenta ordenante = cuentaRepo.findByIban(ibanOrigen);
		if(!ibanDestino.matches("^[A-Z]{2}[0-9]{22}$")) {
			throw new InvalidTransactionException("Error, introduzca un IBAN válido para la cuenta destino");
		}
		if (ordenante != null) {
			Movimiento mov = new Movimiento(importe, LocalDate.now(), concepto, ordenante, cuentaRepo.findByIban(ibanDestino));
			
			// Si el iban no es de santandesi, se envia a la app web
			if (!ibanDestino.matches("^ES61.*{20}$")) {
				if (ordenante.getImporte().compareTo(mov.getImporte()) >= 0) {
					//Realiza la operacion POST en la api externa
					TransactionApiController.enviar(new BankTransaction(mov, ibanDestino));
					
					ordenante.setImporte(ordenante.getImporte().add(mov.getImporte().negate()));
					repository.save(mov);
					cuentaRepo.save(ordenante);
				} else {
					throw new InvalidTransactionException("La transación no se pudo completar. "
							+ "Revise que el importe de su cuenta es superior al introducido.");
				}
			} else {

				if (mov.getOrdenante().getImporte().compareTo(mov.getImporte()) >= 0) // El movimiento se puede realizar
				{
					Cuenta beneficiario = mov.getBeneficiario();
					if (beneficiario != null) {
						ordenante.setImporte(ordenante.getImporte().add(mov.getImporte().negate())); // Resta importe
																										// ordenante
						beneficiario.setImporte(beneficiario.getImporte().add(mov.getImporte())); // Suma importe
																									// beneficiario
						repository.save(mov);
						cuentaRepo.save(ordenante);
						cuentaRepo.save(beneficiario);
					} else {
						throw new InvalidTransactionException("Error, la cuenta del beneficiario no existe.");
					}
				} else {
					throw new InvalidTransactionException("La transación no se pudo completar. "
							+ "Revise que el importe de su cuenta es superior al introducido.");
				}
			}
		} else {
			throw new InvalidTransactionException("Error, la cuenta ordenante no existe.");
		}
	}

	public BankTransaction TransactionApi(BankTransaction bankTransaction) {
		
		Movimiento movimiento = new Movimiento(bankTransaction.getValue(), LocalDate.now(),
				bankTransaction.getConcept(), null, cuentaRepo.findByIban(bankTransaction.getIban()));
		if(movimiento.getBeneficiario() != null) {
			movimiento.getBeneficiario().setImporte(
					movimiento.getBeneficiario().getImporte().add(bankTransaction.getValue()));
			
			repository.save(movimiento);
			bankTransaction.setId(movimiento.getId().toString());
			bankTransaction.setTransactionStatus(TransactionStatus.ACCEPTED);
		} else {
			bankTransaction.setTransactionStatus(TransactionStatus.REJECTED);
		}
		return bankTransaction;
	}

}
