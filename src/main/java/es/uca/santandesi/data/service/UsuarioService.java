package es.uca.santandesi.data.service;

import es.uca.santandesi.data.entity.Cliente;
import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.data.entity.Operador;
import es.uca.santandesi.data.entity.Usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
	
	public class DateException extends Exception {}
	public class DuplicateDNIException extends Exception {}
	
    private final UsuarioRepository repository;
    private ClienteRepository clienteRepository;
    private OperadorRepository operadorRepository;
    
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public UsuarioService(UsuarioRepository repository, ClienteRepository clienteRepository, OperadorRepository operadorRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.operadorRepository = operadorRepository;
    }

    public Optional<Usuario> get(UUID id) {
        return repository.findById(id);
    }

    public Usuario update(Usuario entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public int count() {
        return (int) repository.count();
    }
    
    public void addCuenta(Usuario usuario, Cuenta cuenta) {
    	usuario.addCuenta(cuenta);
    }
    
    public List<Usuario> getUsuariosFiltro(String filtro) {
    	if(filtro == null || filtro.isEmpty()) {
    		return repository.findAll();
    	} else {
    		filtro = filtro.replace("'", "''");
    		return repository.search(filtro);
    	}
    }
    
	/*
	 * 	public Usuario(@NotNull String nombre, @NotNull String apellidos, @NotNull String contrasenna_hash,
			@NotNull LocalDate fecha_nacimiento, @NotNull String dNI, String rol)
	 */
    public void addUsuario(String nombre, String apellidos, String email, LocalDate fechaNacimiento,
    		String dni, String rol) throws DateException, DuplicateDNIException {
    	if(LocalDate.now().getYear() - 18 < fechaNacimiento.getYear())
    		throw new DateException();
    	if(repository.findByDNI(dni) != null)
    		throw new DuplicateDNIException();
    	else {
    		//TODO Arreglar passAleatoria para que el operador no tenga que crearla 
    		//String passAleatoria = RandomStringUtils.random(16, true, true); // 16 caracteres usando letras y numeros
    		String passAleatoria = "0000";
    		if(rol == "Cliente")
    			clienteRepository.save(new Cliente(nombre, apellidos, email, passAleatoria, fechaNacimiento, dni));
    		else
    			operadorRepository.save(new Operador(nombre, apellidos, email, passAleatoria, fechaNacimiento, dni));
    		//sendEmail(email, dni, passAleatoria);
    	}
    }
    
    public void sendEmail(String destinatario, String dni, String pass) {
    	SimpleMailMessage correo = new SimpleMailMessage();
    	
    	correo.setFrom("info@santandesi.es");
    	correo.setTo(destinatario);
    	correo.setSubject("Bienvenido a SantandESI");
    	correo.setText("Nos alegra mucho darle la bienvenida a nuestra banca digital\n"
    			+ "Para acceder a la gestión de su cuenta y realizar operaciones, utilice los siguientes credenciales:\n"
    			+ "DNI: " + dni + "\nContraseña: " + pass + "\nMuchas gracias por confiar en nosotros.");
    	mailSender.send(correo);
    	
    }

	public void eliminar(Usuario usuario) {
		repository.delete(usuario);	
	}

	public Usuario getByDNI(String dni) {
		return repository.findByDNI(dni);
	}
    
    //private static List<GrantedAuthority> getAuthorities(Usuario usr) {
    //	
    //}

}
