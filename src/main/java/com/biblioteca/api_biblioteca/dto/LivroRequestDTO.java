package com.biblioteca.api_biblioteca.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class LivroRequestDTO {

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @NotEmpty(message = "Informe ao menos um autor")
    private List<String> autores;

    @NotBlank(message = "Gênero é obrigatório")
    private String genero;

    @NotNull(message = "Ano de publicação é obbrigatório")
    @Min(value = 1, message = "Ano de publicação inválido")
    @Max(value = 2100, message = "Ano de publicação inválido")
    private Integer anoPublicacao;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser ao menos 1")
    private Integer quantidade;

}
