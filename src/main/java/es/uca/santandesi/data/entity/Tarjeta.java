package es.uca.santandesi.data.entity;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
public class Tarjeta {
	@Id
	@GeneratedValue
	@Type(type = "uuid-char")
	private UUID id; 
	
	@NotNull
	@Column(unique=true)
	private String numeroTarjeta;
	@NotNull
	private Integer codigo_seguridad;
	@NotNull
	private Integer pin;
	@NotNull
	private LocalDate fecha_caducidad;
	@NotNull
	private boolean activada;
	
	
	public Tarjeta() {
		super();
		Random random = new Random();
		int numeroAux = random.nextInt(100000000 - 10000000) + 1000000;
		this.numeroTarjeta = Integer.toString(numeroAux);
		numeroAux = random.nextInt(100000000 - 10000000) + 1000000;
		this.numeroTarjeta = this.numeroTarjeta + Integer.toString(numeroAux); // 2 concat de numeros de 8 digitos
		this.codigo_seguridad = random.nextInt(1000 - 100) + 100; // Numero aleatorio 3 digitos
		this.pin = random.nextInt(10000 - 1000) + 1000; // Numero aleatorio 4 digitos
		this.fecha_caducidad = LocalDate.now().plusYears(4); // Fecha de creacion + 4 años
		this.activada = true;
	}
	public String getNumero_tarjeta() {
		return numeroTarjeta;
	}
	public void setNumero_tarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	public Integer getCodigo_seguridad() {
		return codigo_seguridad;
	}
	public void setCodigo_seguridad(Integer codigo_seguridad) {
		this.codigo_seguridad = codigo_seguridad;
	}
	public Integer getPin() {
		return pin;
	}
	public void setPin(Integer pin) {
		this.pin = pin;
	}
	public LocalDate getFecha_caducidad() {
		return fecha_caducidad;
	}
	public void setFecha_caducidad(LocalDate fecha_caducidad) {
		this.fecha_caducidad = fecha_caducidad;
	}
	public boolean isActivada() {
		return activada;
	}
	public void setActivada(boolean activada) {
		this.activada = activada;
	}
	public UUID getId() {
		return id;
	}
	
	
}
