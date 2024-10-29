package es.uca.santandesi.data.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import es.uca.santandesi.data.entity.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, UUID>{

	List<Consulta> findByCliente_id(UUID cliente_id);

	List<Consulta> findByGestor_id(UUID gestor_id);
	
}
