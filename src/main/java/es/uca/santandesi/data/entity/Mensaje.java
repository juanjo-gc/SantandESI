package es.uca.santandesi.data.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
public class Mensaje {
	@Id
	@GeneratedValue
	@Type(type = "uuid-char")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "consulta_id")
	private Consulta consulta;
	
	@NotNull
	@ManyToOne
    private Usuario usuario;
	
	@NotNull
	private String texto;

	@NotNull
	private LocalDateTime fecha_envio;
	
	public Mensaje() {}
	
	public Mensaje(@NotNull Consulta consulta,@NotNull String texto,@NotNull Usuario usuario) {
		id = UUID.randomUUID();
		this.consulta = consulta;
		this.texto = texto;
		this.usuario = usuario;
		fecha_envio = LocalDateTime.now();
	}

	public UUID getId() { return id; }

	public Consulta getConsulta() { return consulta; }

	public Usuario getUsuario() { return usuario; }

	public String getTexto() { return texto; }

	public LocalDateTime getFecha_envio() { return fecha_envio; }
}
