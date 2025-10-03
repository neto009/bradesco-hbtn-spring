package com.example.demo.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveRetornarProdutoQuandoIdExistir() {
        Long id = 1L;
        Produto produto = new Produto(id, "Produto A", 100.0);
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));

        Produto resultado = produtoService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(produtoRepository).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() {
        Long id = 2L;
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.buscarPorId(id);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
        verify(produtoRepository).findById(id);
    }
}