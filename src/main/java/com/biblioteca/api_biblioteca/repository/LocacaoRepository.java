package com.biblioteca.api_biblioteca.repository;

import com.biblioteca.api_biblioteca.model.Locacao;
import com.biblioteca.api_biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, Long> {

    List<Locacao> findByUsuario(Usuario usuario);
    List<Locacao> findByDevolvidoFalse();
    List<Locacao> findByEmAtrasoTrue();
}