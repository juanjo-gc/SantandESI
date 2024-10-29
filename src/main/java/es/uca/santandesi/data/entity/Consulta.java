package es.uca.santandesi.data.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.vaadin.flow.data.binder.PropertyId;

@Entity
public class Consulta {
	@Id
	@GeneratedValue
	@Type (type="uuid-char")
	private UUID id;

	@NotNull
	private LocalDateTime fecha_apertura;
	private LocalDateTime fecha_cierre;
	@NotNull 
	@NotEmpty
	private String asunto;
	
	@NotNull
	@ManyToOne
	private Usuario gestor;

	@NotNull
	@ManyToOne
	private Usuario cliente;
	
	@NotNull
	@ManyToOne
	private Categoria categoria;
	
	@OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
	@PropertyId("descripcion")
    private List<Mensaje> mensajes;
	
	public Consulta() {}

	public Consulta(@NotNull String asunto, @NotNull Usuario cliente,
			@NotNull Usuario gestor, @NotNull Categoria categoria) {
		this.id = UUID.randomUUID();
		this.fecha_apertura = LocalDateTime.now();
		this.fecha_cierre = null;
		this.asunto = asunto;
		this.cliente = cliente;
		this.gestor = gestor;
		this.categoria = categoria;
		mensajes = new ArrayList<Mensaje>();
	}
	
	public UUID getId() { return id; }
	
	public LocalDateTime getFecha_apertura() { return fecha_apertura; }
	
	public LocalDateTime getFecha_cierre() { return fecha_cierre; }
	public void setFecha_cierre(LocalDateTime fecha_cierre) { this.fecha_cierre = fecha_cierre; }

	public String getAsunto() { return asunto; }

	public Usuario getGestor() { return gestor; }

	public Usuario getCliente() { return cliente; }
	public String getNombreCliente() { return cliente.getNombre(); }

	public Categoria getCategoria() { return categoria; }
	
	public List<Mensaje> getMensajes() { 
		mensajes.sort(new Comparator<Mensaje>() {
			@Override
			public int compare(Mensaje o1, Mensaje o2) {
				if(o1.getFecha_envio().isAfter(o2.getFecha_envio()))
					return 1;
				else if(o1.getFecha_envio().isBefore(o2.getFecha_envio()))
					return -1;
				else
					return 0;
			}
		});
		return mensajes;
	}
	public void addMensaje(Mensaje mensaje) { mensajes.add(mensaje); }
}
