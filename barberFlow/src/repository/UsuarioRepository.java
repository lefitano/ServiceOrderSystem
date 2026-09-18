package com.barberflow.repository;

import com.barberFlow.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;




public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
}
