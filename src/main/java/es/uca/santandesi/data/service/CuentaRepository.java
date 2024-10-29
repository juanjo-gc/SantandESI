package es.uca.santandesi.data.service;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import es.uca.santandesi.data.entity.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, UUID>{
	/*
	@Query("select c from Cuenta c join usuario_cuentas uc where c.id = uc.cuentas_id join Usuario u"
			+ " where uc.usuarios_id = u.id where u.id = :idUsuario")
	List<Cuenta> findCuentasUsuario(@Param("idUsuario")UUID idUsuario);
	*/
	/*
	@Query("select c from Cuenta c join usuario_cuentas uc where c.id = uc.cuentas_id where :idUsuario ="
			+ " uc.usuarios_id")
	*/

	@NotNull
	Cuenta findByIban(String iban);

	@Query("select c from Cuenta c where c.iban like concat(:filtro, '%')")
	List<Cuenta> search(String filtro);
	
}

