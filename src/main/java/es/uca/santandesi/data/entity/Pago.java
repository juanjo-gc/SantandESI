package es.uca.santandesi.data.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
public class Pago {
	@Id
	@GeneratedValue
	@Type(type = "uuid-char")
	private UUID id;
	
	@NotNull
	private BigDecimal importe;
	@NotNull
	private String nombre_empresa;
	@NotNull
	private LocalDate fecha;
	
	@ManyToOne
	private Tarjeta tarjeta;

	public Pago() {}
	
	public Pago(@NotNull BigDecimal importe, @NotNull String nombre_empresa, LocalDate fecha, Tarjeta tarjeta) {
		this.id = UUID.randomUUID();
		this.importe = importe;
		this.nombre_empresa = nombre_empresa;
		this.fecha = fecha;
		this.tarjeta = tarjeta;
	}
	
	public Pago(@NotNull UUID id, @NotNull BigDecimal importe, @NotNull String nombre_empresa, LocalDate fecha, Tarjeta tarjeta) {
		this.id = id;
		this.importe = importe;
		this.nombre_empresa = nombre_empresa;
		this.fecha = fecha;
		this.tarjeta = tarjeta;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getNombre_empresa() {
		return nombre_empresa;
	}

	public void setNombre_empresa(String nombre_empresa) {
		this.nombre_empresa = nombre_empresa;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Tarjeta getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	

}
