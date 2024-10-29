package es.uca.santandesi.data.service;

import es.uca.santandesi.data.entity.Categoria;
import es.uca.santandesi.data.entity.Consulta;
import es.uca.santandesi.data.entity.Mensaje;
import es.uca.santandesi.data.entity.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.flow.server.VaadinSession;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    public ConsultaService(ConsultaRepository repository) {
        this.repository = repository;
    }

    public Optional<Consulta> get(UUID id) {
        return repository.findById(id);
    }

    public Consulta update(Consulta entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public int count() {
        return (int) repository.count();
    }
    
    public void addConsulta(String asunto, String categ, String descripcion) {
    	Random random = new Random();
    	List<Usuario> gestores = usuarioRepository.findByRolEquals("operador");
    	Usuario operador = gestores.get(random.nextInt(gestores.size()));
    	
    	Categoria categoria = categoriaRepository.findByNombre(categ);
    	Usuario cliente = VaadinSession.getCurrent().getAttribute(Usuario.class);
    	
    	Consulta consulta = new Consulta(asunto, cliente, operador, categoria);
    	consulta = repository.save(consulta);
    	
    	Mensaje mensaje = mensajeRepository.save(new Mensaje(consulta, descripcion, cliente));
    	consulta.addMensaje(mensaje);

    	consulta = repository.save(consulta);
    }
    
    public List<Consulta> getConsultasCliente(UUID ClienteId) {
    	return repository.findByCliente_id(ClienteId);
    }
    
    public List<Consulta> getConsultasGestor(UUID GestorId) {
    	return repository.findByGestor_id(GestorId);
    }
}
