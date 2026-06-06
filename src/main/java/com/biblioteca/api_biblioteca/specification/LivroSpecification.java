package com.biblioteca.api_biblioteca.specification;

import com.biblioteca.api_biblioteca.enums.StatusLivro;
import com.biblioteca.api_biblioteca.model.Livro;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecification {

    public static Specification<Livro> tituloContem(String titulo) {
        return (root, query, cb) ->
                titulo == null ? null : cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%");
    }

    public static Specification<Livro> generoContem(String genero) {
        return (root, query, cb) ->
                genero == null ? null : cb.like(cb.lower(root.get("genero")), "%" + genero.toLowerCase() + "%");
    }

    public static Specification<Livro> autorContem(String autor) {
        return (root, query, cb) -> {
                if (autor == null) return null;
                Join<Livro, String> autoresJoin = root.join("autores");
                return cb.like(cb.lower(autoresJoin), "%" + autor.toLowerCase() + "%");
        };
    }

    public static Specification<Livro> anoIgual(Integer ano) {
        return (root, query, cb) ->
                ano == null ? null : cb.equal(root.get("anoPublicacao"), ano);
    }

    public static Specification<Livro> statusIgual(StatusLivro status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

}