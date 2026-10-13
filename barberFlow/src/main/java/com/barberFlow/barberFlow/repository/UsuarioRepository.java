package com.barberFlow.barberFlow.repository;

import com.barberFlow.barberFlow.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;




public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Integer id);
}
