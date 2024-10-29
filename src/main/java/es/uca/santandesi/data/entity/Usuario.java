package es.uca.santandesi.data.entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.Type;

@Entity
@DiscriminatorColumn(name="rol")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Usuario {
	@Id
	@GeneratedValue
	@Type(type = "uuid-char")
	private UUID id;
	@NotNull
	@Column(insertable=false, updatable=false)
	private String rol;
	
	@NotNull
	private String nombre;
	@NotNull
	private String apellidos;
	@NotNull
	private String email;
	@NotNull
	@Column(unique=true)
	private String DNI;
	@NotNull
	private String contrasenna_sal;
	@NotNull
	private String contrasenna_hash;
	@NotNull
	private LocalDate fecha_nacimiento;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_cuentas", 
		joinColumns = {@JoinColumn(name = "usuarios_id")},
		inverseJoinColumns = {@JoinColumn(name = "cuentas_id")})
	private List<Cuenta> cuentas;
	
	public Usuario() {}
	
	public Usuario(@NotNull String rol, @NotNull String nombre, @NotNull String apellidos, @NotNull String email, 
			@NotNull String dni, @NotNull String contrasenna, @NotNull LocalDate fecha_nacimiento) {
		this.id = UUID.randomUUID();
		this.rol = rol;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		DNI = dni;
		this.contrasenna_sal = RandomStringUtils.random(32);
		this.contrasenna_hash = DigestUtils.sha1Hex(contrasenna + contrasenna_sal);
		this.fecha_nacimiento = fecha_nacimiento;
		this.cuentas = new ArrayList<Cuenta>();
	}

	public UUID getId() { return id; }
	public void setId(UUID id) { this.id = id; }
	
	public String getRol() { return rol; }
	public void setRol(String rol) { this.rol = rol; }

	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

	public String getApellidos() { return apellidos; }
	public void setApellidos(String apellidos) { this.apellidos = apellidos; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getDNI() { return DNI; }
	public void setDNI(String dni) { DNI = dni; }
	
	public String getContrasenna_hash() { return contrasenna_hash; }
	public void setContrasenna_hash(String contrasenna_hash) { this.contrasenna_hash = contrasenna_hash; }

	public LocalDate getFecha_nacimiento() { return fecha_nacimiento; }
	public void setFecha_nacimiento(LocalDate fecha_nacimiento) { this.fecha_nacimiento = fecha_nacimiento; }

	public List<Cuenta> getCuentas() { return cuentas; }
	public void setCuentas(List<Cuenta> cuentas) { this.cuentas = cuentas; }
	public void addCuenta(Cuenta cuenta) { 
		cuentas.add(cuenta);
		cuenta.addUsuario(this);
	}
	public void removeCuenta(UUID cuenta_id) { 
		Cuenta cuenta = cuentas.stream().filter(t -> t.getId() == cuenta_id).findFirst().orElse(null);
	    if (cuenta != null) {
	      cuentas.remove(cuenta);
	      cuenta.removeUsuario(id);
	      System.out.println("removecuenta");
	    }
	}
	
	public void removeCuenta(Cuenta cuenta) {
		this.cuentas.remove(cuenta);
		cuenta.getUsuarios().remove(this);
	}

	public boolean checkContrasenna(String contrasenna) {
		return DigestUtils.sha1Hex(contrasenna + this.contrasenna_sal).equals(contrasenna_hash);
	}
	
}
