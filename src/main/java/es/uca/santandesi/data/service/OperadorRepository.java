package es.uca.santandesi.data.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uca.santandesi.data.entity.Operador;

public interface OperadorRepository extends JpaRepository<Operador, UUID> {

}
