package es.uca.santandesi.data.service;

import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.data.entity.Tarjeta;
import es.uca.santandesi.data.entity.Usuario;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaService {

    private final CuentaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    
    public class AddCardException extends Exception {
    	private String motivo;
    	public AddCardException(String motivo) { this.motivo = motivo; }
    	public String getMotivo() { return motivo; }
    }

    @Autowired
    public CuentaService(CuentaRepository repository, UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.repository = repository;
		this.usuarioRepository = usuarioRepository;
		this.usuarioService = usuarioService;
    }

    public Optional<Cuenta> get(UUID id) {
        return repository.findById(id);
    }

    public Cuenta update(Cuenta entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public int count() {
        return (int) repository.count();
    }
    
    public List<Cuenta> cuentasUsuario(UUID idUsuario) {
    	return usuarioRepository.findByid(idUsuario).getCuentas();
    }
    
    public void addCuenta(String tipoCuenta, String dniCliente) {
    	Usuario usuario = usuarioRepository.findByDNI(dniCliente);
    	Cuenta cuenta;
		if(tipoCuenta == "Joven")
    		cuenta = new Cuenta("joven");
    	else
    		cuenta = new Cuenta("estandar");
		cuenta = update(cuenta);
		usuarioService.addCuenta(usuario, cuenta);
	    usuarioService.update(usuario);
    }
    
    public boolean usuarioTieneCuenta(String dni, String iban) {
    	
    	List<Cuenta> cuentas = usuarioRepository.findByDNI(dni).getCuentas();
    	Boolean encontrada = false;
    	
    	for(ListIterator<Cuenta> i = cuentas.listIterator(); i.hasNext() && !encontrada; ) {
    		if(i.next().getIban().equals(iban))
    			encontrada = true;
    	}
    	return encontrada;
    }
    
    public void addTarjetaCuenta(String iban, Tarjeta tarjeta) throws AddCardException {
    	Cuenta cuenta = repository.findByIban(iban);
    	if(cuenta.getTipo() == "joven")
    	{
    		if(cuenta.getTarjetas().isEmpty())
    			cuenta.getTarjetas().add(tarjeta);
    		else {
    			throw new AddCardException("Error, una cuenta joven no puede tener asociada más de una tarjeta.");
    		}
    	} else {
    		cuenta.getTarjetas().add(tarjeta);
    	}
    	repository.save(cuenta);
    }

	public void eliminar(Cuenta cuenta, Usuario usuario) {
		usuario.getCuentas().remove(cuenta);
		cuenta.getUsuarios().remove(usuario);
		usuarioService.update(usuario);
		if(cuenta.getUsuarios().isEmpty())
			repository.delete(cuenta);
	}

	public List<Cuenta> getCuentasFiltro(String filtro) {
		if(filtro == null || filtro.isEmpty()) {
    		return repository.findAll();
    	} else {
    		return repository.search(filtro);
    	}
	}

	public Cuenta getCuentaIban(String iban) {
		return repository.findByIban(iban);
	}

}
