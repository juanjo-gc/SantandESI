package es.uca.santandesi.data.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.data.entity.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, UUID>{
	//@Query("SELECT m FROM Movimiento  WHERE m.ordenante = :ordenante")
	//Movimiento findMovimientosCuenta(@Param(value = "ordenante") Cuenta ordenante);
	
	List<Movimiento> findByOrdenante(Cuenta Ordenante);
	List<Movimiento> findByBeneficiario(Cuenta beneficiario);
}
