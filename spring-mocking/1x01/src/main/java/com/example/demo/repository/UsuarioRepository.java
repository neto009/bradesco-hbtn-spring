package com.example.demo.repository;

import java.util.Optional;

import com.example.demo.model.Usuario;

public interface UsuarioRepository {
    
    Optional<Usuario> findById(Long id);
    
    Usuario save(Usuario usuario);
}