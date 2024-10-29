package es.uca.santandesi.data.service;

import es.uca.santandesi.data.entity.Categoria;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    @Autowired
    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public Optional<Categoria> get(UUID id) {
        return repository.findById(id);
    }

    public Categoria update(Categoria entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public int count() {
        return (int) repository.count();
    }
    
    public List<String> getCategorias() {
    	return repository.findNombresCategorias();
    }
    
    public Categoria getCategoria(String nombre) {
    	return repository.findByNombre(nombre);
    }
}
