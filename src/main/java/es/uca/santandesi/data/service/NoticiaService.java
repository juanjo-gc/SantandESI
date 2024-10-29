package es.uca.santandesi.data.service;

import es.uca.santandesi.data.entity.Noticia;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticiaService {

    private final NoticiaRepository repository;

    @Autowired
    public NoticiaService(NoticiaRepository repository) {
        this.repository = repository;
    }

    public Optional<Noticia> get(UUID id) {
        return repository.findById(id);
    }

    public Noticia update(Noticia entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public int count() {
        return (int) repository.count();
    }
    
    public List<Noticia> getNoticias() {
    	return repository.findAll();
    }
    
    public void registrarNoticia(String titulo, String cuerpo, InputStream datos, String nombreImagen) throws IOException {
    	File ficheroDestino = new File("images/" + nombreImagen);
    	FileUtils.copyInputStreamToFile(datos, ficheroDestino);
    	repository.save(new Noticia(titulo, null, cuerpo, nombreImagen));
    }
    
    public void registrarNoticia(String titulo, String encabezado, String cuerpo) {
    	repository.save(new Noticia(titulo, encabezado, cuerpo, null));
    }
}
