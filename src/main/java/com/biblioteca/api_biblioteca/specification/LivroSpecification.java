package com.biblioteca.api_biblioteca.specification;

import com.biblioteca.api_biblioteca.enums.StatusLivro;
import com.biblioteca.api_biblioteca.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecification {

    public static Specification<Livro> tituloContem(String titulo) {
        return (root, query, cb) -> {
            if (titulo == null) return null;
            return cb.like(cb.lower(root.<String>get("titulo")),
                    "%" + titulo.toLowerCase() + "%");
        };
    }

    public static Specification<Livro> generoContem(String genero) {
        return (root, query, cb) -> {
            if (genero == null) return null;
            return cb.like(cb.lower(root.<String>get("genero")),
                    "%" + genero.toLowerCase() + "%");
        };
    }

    public static Specification<Livro> autorContem(String autor) {
        return (root, query, cb) -> {
            if (autor == null) return null;
            Join<Livro, String> autoresJoin = root.join("autores", JoinType.INNER);
            return cb.like(cb.lower(autoresJoin.as(String.class)),
                    "%" + autor.toLowerCase() + "%");
        };
    }

    public static Specification<Livro> anoIgual(Integer ano) {
        return (root, query, cb) -> {
            if (ano == null) return null;
            return cb.equal(root.<Integer>get("anoPublicacao"), ano);
        };
    }

    public static Specification<Livro> statusIgual(StatusLivro status) {
        return (root, query, cb) -> {
            if (status == null) return null;
            return cb.equal(root.<StatusLivro>get("status"), status);
        };
    }
}