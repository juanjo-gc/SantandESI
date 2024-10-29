package es.uca.santandesi.data.entity;

import java.time.LocalDate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("operador")
public class Operador extends Usuario{

	public Operador() {}
	
	public Operador(@NotNull String nombre, @NotNull String apellidos, @NotNull String email, @NotNull String contrasenna,
			@NotNull LocalDate fecha_nacimiento, @NotNull String dNI) {
		super("operador", nombre, apellidos, email, dNI,contrasenna, fecha_nacimiento);
		
	}
}
