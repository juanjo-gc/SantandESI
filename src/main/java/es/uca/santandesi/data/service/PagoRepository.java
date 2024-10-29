package es.uca.santandesi.data.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uca.santandesi.data.entity.Pago;
import es.uca.santandesi.data.entity.Tarjeta;

public interface PagoRepository extends JpaRepository<Pago, UUID> {
	List<Pago> findByTarjeta(Tarjeta tarjeta);
}
