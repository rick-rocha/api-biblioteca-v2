package com.biblioteca.api_biblioteca.dto;

import com.biblioteca.api_biblioteca.enums.StatusLivro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivroResponseDTO {

    private Long id;
    private String titulo;
    private List<String> autores;
    private String genero;
    private Integer anoPublicacao;
    private StatusLivro status;
    private Integer quantidade;

}