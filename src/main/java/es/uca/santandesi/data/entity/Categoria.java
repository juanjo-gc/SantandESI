package es.uca.santandesi.data.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
public class Categoria {

	@Id
	@GeneratedValue
	@Type(type="uuid-char")
	private UUID id;
	
	@NotNull
	@Column(unique=true)
	private String nombre;
	
	public Categoria() {}
	
	public Categoria(String nombre) {
		id = UUID.randomUUID();
		this.nombre = nombre;
		
	}
}
