package es.uca.santandesi.data.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.uca.santandesi.data.entity.Noticia;

public interface NoticiaRepository extends JpaRepository<Noticia, UUID>{

}
