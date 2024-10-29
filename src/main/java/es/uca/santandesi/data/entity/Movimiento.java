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
public class Movimiento {
	@Id
	@GeneratedValue
	@Type(type="uuid-char")
	private UUID id;
	
	@NotNull
	private BigDecimal importe;
	@NotNull
	private LocalDate fecha_realizacion;
	private String concepto;
	
	// Ordenante y beneficiario no son nulos por si alguna cuenta no pertenece al banco (aplicacion web)
	@ManyToOne
	private Cuenta ordenante;
	@ManyToOne
	private Cuenta beneficiario;
	
	public Movimiento() {}
	
	public Movimiento(@NotNull BigDecimal importe, @NotNull LocalDate fecha_realizacion, String concepto,
			Cuenta ordenante, Cuenta beneficiario) {
		super();
		this.id = UUID.randomUUID();
		this.importe = importe;
		this.fecha_realizacion = fecha_realizacion;
		this.concepto = concepto;
		this.ordenante = ordenante;
		this.beneficiario = beneficiario;
	}

	public UUID getId() {
		return id;
	}

	public BigDecimal getImporte() {
		return importe;
	}
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	public LocalDate getFecha_realizacion() {
		return fecha_realizacion;
	}
	public void setFecha_realizacion(LocalDate fecha_realizacion) {
		this.fecha_realizacion = fecha_realizacion;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public Cuenta getOrdenante() {
		return ordenante;
	}
	public void setOrdenante(Cuenta ordenante) {
		this.ordenante = ordenante;
	}
	public Cuenta getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(Cuenta beneficiario) {
		this.beneficiario = beneficiario;
	}
	
	
}
