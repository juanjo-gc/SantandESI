package es.uca.santandesi.data.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import es.uca.santandesi.data.entity.Tarjeta;

public interface TarjetaRepository extends JpaRepository<Tarjeta, UUID>{
	//@Query("SELECT t FROM Tarjeta t, Cuenta c WHERE t IN c.tarjetas")
	//List<Tarjeta> findTarjetasUsuario();
	
	Tarjeta findByNumeroTarjeta(String cardNumber);
}
