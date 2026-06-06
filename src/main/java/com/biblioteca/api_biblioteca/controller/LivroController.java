package com.biblioteca.api_biblioteca.controller;

import com.biblioteca.api_biblioteca.dto.LivroRequestDTO;
import com.biblioteca.api_biblioteca.dto.LivroResponseDTO;
import com.biblioteca.api_biblioteca.enums.StatusLivro;
import com.biblioteca.api_biblioteca.model.Livro;
import com.biblioteca.api_biblioteca.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService service;

    // Listar Todos
    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listar() {
        List<LivroResponseDTO> livros = service.listarTodos();
        return ResponseEntity.ok(livros);
    }

    // Busca Avançada
    @GetMapping("/busca")
    public ResponseEntity<List<LivroResponseDTO>> buscar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) Integer anoPublicacao,
            @RequestParam(required = false) StatusLivro status) {

        List<LivroResponseDTO> livros = service.buscar(titulo, categoria, autor, anoPublicacao, status);
        return ResponseEntity.ok(livros);
    }

    // Busca por ID
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> buscarPorID(@PathVariable Long id) {
        LivroResponseDTO livro = service.buscarPorIdDTO(id);
        return ResponseEntity.ok(livro);
    }

    // Criar Novo Livro
    @PostMapping
    public ResponseEntity<LivroResponseDTO> criar(@Valid @RequestBody LivroRequestDTO dto) {
        LivroResponseDTO livro = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(livro);
    }

    // Atualizar Dados do Livro
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody LivroRequestDTO dto) {
        LivroResponseDTO livro = service.atualizar(id, dto);
        return ResponseEntity.ok(livro);
    }

    // Deletar livro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}