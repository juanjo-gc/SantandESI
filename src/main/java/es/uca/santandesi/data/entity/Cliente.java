package es.uca.santandesi.data.entity;

import java.time.LocalDate;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("cliente")
public class Cliente extends Usuario {
	
	private LocalDate fecha_alta;
	private LocalDate fecha_baja;
	
	public Cliente() {}
	
	public Cliente(@NotNull String nombre, @NotNull String apellidos, @NotNull String email, @NotNull String contrasenna,
			@NotNull LocalDate fecha_nacimiento, @NotNull String dNI) {
		super("operador", nombre, apellidos, email, dNI,contrasenna, fecha_nacimiento);
		this.fecha_alta = LocalDate.now();
		this.fecha_baja = null;
		
	}

	public LocalDate getFecha_alta() {
		return fecha_alta;
	}
	
	public LocalDate getFecha_baja() {
		return fecha_baja;
	}
	public void setFecha_baja(LocalDate fecha_baja) {
		this.fecha_baja = fecha_baja;
	}
}
