package es.uca.santandesi.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.santandesi.data.entity.Mensaje;

@Service
public class MensajeService {
    private final MensajeRepository repository;

    @Autowired
    public MensajeService(MensajeRepository repository) {
        this.repository = repository;
    }

    public Mensaje update(Mensaje entity) {
        return repository.save(entity);
    }
    
}
