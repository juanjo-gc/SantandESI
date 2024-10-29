package es.uca.santandesi.data.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
public class Cuenta {
	@Id
	@GeneratedValue
	@Type(type = "uuid-char")
	private UUID id;
	
	@NotNull
	private BigDecimal importe;
	@NotNull
	private String iban;
	@NotNull
	private Float comision;
	@NotNull
	private String tipo;
	@NotNull
	private LocalDate fecha_creacion;
	
	@ManyToMany(fetch = FetchType.EAGER, //cascade = { CascadeType.PERSIST, CascadeType.MERGE },
		mappedBy = "cuentas")
	private List<Usuario> usuarios;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cuenta_id")
	private List<Tarjeta> tarjetas;
	
	public Cuenta () {}
	
	public Cuenta(@NotNull String tipo) {
		Random random = new Random();
		this.id = UUID.randomUUID();
		this.tipo = tipo;
		this.importe = BigDecimal.ZERO;
		
		BigDecimal ibanNumerico;
		String ibanAux;
		do {
			ibanAux = "";
			for(int i = 0; i < 20; i++) // 20 numeros (+4) en españa
				ibanAux += random.nextInt(10); // numero entre 0 y 9
			ibanNumerico = new BigDecimal(ibanAux + "142800"); // ES -> 14 28 + 00
		} while(ibanNumerico.remainder(new BigDecimal(97)).compareTo(new BigDecimal(37)) != 0); 
		// 97 - mod = 61(banco) - 1		| MOD-97-10
		this.iban = "ES61" + ibanAux;

		if(tipo == "joven")
			comision = 0f;
		else
			comision = 1.2f;
		
		this.fecha_creacion = LocalDate.now();
		
		this.usuarios = new ArrayList<Usuario>();
		
		this.tarjetas = new ArrayList<Tarjeta>();
	}
	
	public Cuenta(String tipo, BigDecimal importe) {
		this(tipo);
		this.importe = importe;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String numero_cuenta) {
		this.iban = numero_cuenta;
	}

	public Float getComision() {
		return comision;
	}

	public void setComision(Float comision) {
		this.comision = comision;
	}

	public List<Usuario> getUsuarios() { return usuarios; }
	public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
	public void addUsuario(Usuario usuario) {
		usuarios.add(usuario);
	}
	public void removeUsuario(UUID usuario_id) {
		Usuario usuario = usuarios.stream().filter(t -> t.getId() == usuario_id).findFirst().orElse(null);
	    if (usuario != null) {
	      usuarios.remove(usuario);
	      System.out.println("Removeusuario");
	    }
	}
	
	public List<Tarjeta> getTarjetas() {
		return tarjetas;
	}

	public void setTarjetas(List<Tarjeta> tarjetas) {
		this.tarjetas = tarjetas;
	}
	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDate getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(LocalDate fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public UUID getId() {
		return id;
	}
	
	
}
