package com.biblioteca.api_biblioteca.model;

import com.biblioteca.api_biblioteca.enums.StatusLivro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "livro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos do Livro
    private String titulo;
    @ElementCollection
    @CollectionTable(name = "livro_autores",  joinColumns = @JoinColumn(name = "livro_id"))
    @Column(name = "autor")
    private List<String> autores;
    private String genero;
    private Integer anoPublicacao;
    private StatusLivro status;
    private Integer quantidade;
}