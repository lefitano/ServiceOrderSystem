 package com.barberflow.repository;

 import com.barberflow.model.Cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

 public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    Optional<Cliente> findByUsuarioId(Integer usuarioId);
 }