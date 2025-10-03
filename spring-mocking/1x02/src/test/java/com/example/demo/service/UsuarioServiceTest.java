package com.example.demo.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarUsuarioQuandoIdExistir() {
        Long id = 1L;
        Usuario usuarioEsperado = new Usuario(id, "João Silva", "joao@email.com");
        
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioEsperado));

        Usuario usuarioRetornado = usuarioService.buscarUsuarioPorId(id);

        assertNotNull(usuarioRetornado);
        assertEquals(usuarioEsperado.getId(), usuarioRetornado.getId());
        assertEquals(usuarioEsperado.getNome(), usuarioRetornado.getNome());
        assertEquals(usuarioEsperado.getEmail(), usuarioRetornado.getEmail());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        Long id = 999L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarUsuarioPorId(id);
        });

        assertEquals("Usuário não encontrado com ID: " + id, exception.getMessage());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        Usuario usuarioParaSalvar = new Usuario(null, "Maria Santos", "maria@email.com");
        Usuario usuarioSalvo = new Usuario(2L, "Maria Santos", "maria@email.com");
        
        when(usuarioRepository.save(usuarioParaSalvar)).thenReturn(usuarioSalvo);

        Usuario usuarioRetornado = usuarioService.salvarUsuario(usuarioParaSalvar);

        assertNotNull(usuarioRetornado);
        assertEquals(usuarioSalvo.getId(), usuarioRetornado.getId());
        assertEquals(usuarioSalvo.getNome(), usuarioRetornado.getNome());
        assertEquals(usuarioSalvo.getEmail(), usuarioRetornado.getEmail());
        verify(usuarioRepository, times(1)).save(usuarioParaSalvar);
    }
}