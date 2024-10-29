package es.uca.santandesi.data.service;

import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.data.entity.Tarjeta;
import es.uca.santandesi.data.service.CuentaService.AddCardException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaService {

    private final TarjetaRepository repository;
    private UsuarioRepository usuarioRepository;
    private CuentaRepository cuentaRepository;
    private CuentaService cuentaService;
    public class CardCreationException extends Exception {
    	private String motivo;
    	public CardCreationException(String motivo) { this.motivo = motivo; }
    	public String getMotivo() { return motivo; } 
    }

    @Autowired
    public TarjetaService(TarjetaRepository repository, UsuarioRepository usuarioRepository, 
    		CuentaRepository cuentaRepository, CuentaService cuentaService) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.cuentaRepository = cuentaRepository;
        this.cuentaService = cuentaService;
        
    }

    public Optional<Tarjeta> get(UUID id) {
        return repository.findById(id);
    }

    public Tarjeta update(Tarjeta entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public int count() {
        return (int) repository.count();
    }
    
    public List<Tarjeta> getTarjetasUsuario(UUID idUsuario){
    	List<Cuenta> cuentas = usuarioRepository.findByid(idUsuario).getCuentas();
    	List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
    	for(Cuenta cuenta: cuentas) {
    		tarjetas.addAll(cuenta.getTarjetas());
    	}
    	return tarjetas;
    }
    
    public void addTarjetaCuenta(String iban, String dni) throws CardCreationException, AddCardException {
    	if(cuentaRepository.findByIban(iban).equals(null)) {
    		throw new CardCreationException("La cuenta con el IBAN introducido no existe.");
    	}
    	if(usuarioRepository.findByDNI(dni).equals(null)) {
    		throw new CardCreationException("El usuario con el DNI introducido no existe.");
    	}
    	if(!cuentaService.usuarioTieneCuenta(dni, iban)) {
    		throw new CardCreationException("El usuario no posee la cuenta con el IBAN introducido.");
    	}
    	Tarjeta tarjeta = repository.save(new Tarjeta());
    	cuentaService.addTarjetaCuenta(iban, tarjeta);
    }

	public void activarDesactivar(Tarjeta tarjeta) {
		tarjeta.setActivada(!tarjeta.isActivada());
		repository.save(tarjeta);
	}

	public void eliminar(Tarjeta tarjeta, Cuenta cuenta) {
		cuenta.getTarjetas().remove(tarjeta);
		cuentaService.update(cuenta);
		repository.delete(tarjeta);
	}
	/*
	public void eliminar(Cuenta cuenta, Usuario usuario) {
		usuario.getCuentas().remove(cuenta);
		cuenta.getUsuarios().remove(usuario);
		usuarioService.update(usuario);
		if(cuenta.getUsuarios().isEmpty())
			repository.delete(cuenta);
	}
	 */
}
