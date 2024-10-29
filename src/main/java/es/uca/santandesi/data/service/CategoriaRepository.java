package es.uca.santandesi.data.service;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.uca.santandesi.data.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID>{
	@Query("SELECT c.nombre FROM Categoria c")
	List<String> findNombresCategorias();
	
	@NotNull
	Categoria findByNombre(String nombre);
}
