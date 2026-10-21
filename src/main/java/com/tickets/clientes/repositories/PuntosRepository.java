package com.tickets.clientes.repositories;

import com.tickets.clientes.entities.Puntos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PuntosRepository extends JpaRepository<Puntos, Integer> {
    Optional<Puntos> findTopByOrderByIdDesc(Integer id); // obtiene el registro con el id mas alto
}
