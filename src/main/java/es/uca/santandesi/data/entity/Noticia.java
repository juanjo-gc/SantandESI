package es.uca.santandesi.data.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
public class Noticia {
	@Id
	@GeneratedValue
	@Type (type="uuid-char")
	private UUID id;
	
	@NotNull 
	private String titulo;
	@NotNull
	private LocalDate fecha_publicacion;
    @NotNull
	private String cuerpo;
    @NotNull
    private String encabezado;
    private String imagen;

	public Noticia() {}

	public Noticia(@NotNull String titulo, @NotNull String encabezado, @NotNull String cuerpo, String imagen) {
		super();
        this.id = UUID.randomUUID();
		this.titulo = titulo;
		this.encabezado = encabezado;
        this.cuerpo = cuerpo;
        this.fecha_publicacion = LocalDate.now();
        this.imagen = imagen;
	}
	
	

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDate getFecha_publicacion() {
		return fecha_publicacion;
	}

	public String getEncabezado() {
		return encabezado;
	}

	public void setEncabezado(String encabezado) {
		this.encabezado = encabezado;
	}

	public void setFecha_publicacion(LocalDate fecha_publicacion) {
		this.fecha_publicacion = fecha_publicacion;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public UUID getId() {
		return id;
	}
	
	
	 
}
