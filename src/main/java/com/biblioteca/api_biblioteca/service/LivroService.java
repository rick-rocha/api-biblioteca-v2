package com.biblioteca.api_biblioteca.service;

import com.biblioteca.api_biblioteca.dto.LivroResponseDTO;
import com.biblioteca.api_biblioteca.dto.LivroRequestDTO;
import com.biblioteca.api_biblioteca.enums.StatusLivro;
import com.biblioteca.api_biblioteca.model.Livro;
import com.biblioteca.api_biblioteca.repository.LivroRepository;
import com.biblioteca.api_biblioteca.specification.LivroSpecification;
import com.biblioteca.api_biblioteca.exception.RecursoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    private LivroResponseDTO toDTO(Livro livro) {
        return new LivroResponseDTO(
                livro.getId();
                livro.getTitulo();
                livro.getAutores();
                livro.getGenero();
                livro.getAnoPublicacao();
                livro.getStatus();
                livro.getQuantidade();
        );
    }

    private Livro toEntity(LivroRequestDTO dto) {
        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setAutores(dto.getAutores());
        livro.setGenero(dto.getGenero());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setQuantidade(dto.getQuantidade());
        return livro;
    }

    public List<LivroResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Livro buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado com id: " + id));
    }

    public LivroResponseDTO buscarPorIdDTO(Long id) {
        return toDTO(buscarPorId(id));
    }

    public LivroResponseDTO criar(LivroRequestDTO dto) {
        Livro livro = toEntity(dto);
        livro.setStatus(StatusLivro.ESTOQUE);
        return toDTO(repository.save(livro));
    }

    public LivroResponseDTO atualizar(Long id, LivroRequestDTO dto) {
        Livro livro = buscarPorId(id);
        livro.setTitulo(dto.getTitulo());
        livro.setAutores(dto.getAutores());
        livro.setGenero(dto.getGenero());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setQuantidade(dto.getQuantidade());
        return toDTO(repository.save(livro));
    }

    public List<LivroResponseDTO> buscar(String titulo, String genero,
                                         String autor, Integer ano,
                                         StatusLivro status) {
        Specification<Livro> spec = Specification
                .where(LivroSpecification.tituloContem(titulo))
                .and(LivroSpecification.generoContem(genero))
                .and(LivroSpecification.autorContem(autor))
                .and(LivroSpecification.anoIgual(ano))
                .and(LivroSpecification.statusIgual(status));

        return repository.findAll(spec)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Livro salvar(Livro livro) {
        return repository.save(livro);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

}