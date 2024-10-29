package es.uca.santandesi.data.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uca.santandesi.data.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{

	Usuario findByDNI(String DNI);
	
	Usuario findByid(UUID id);

	List<Usuario> findByRolEquals(String string);
	
	@Query("select u from Usuario u where u.DNI like concat(:filtro, '%')")
	List<Usuario> search(@Param("filtro") String filtro);
}
