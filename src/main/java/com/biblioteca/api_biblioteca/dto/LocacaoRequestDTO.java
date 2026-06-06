package com.biblioteca.api_biblioteca.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocacaoRequestDTO {

    @NotNull(message = "ID do livro é obrigatório")
    private Long livroId;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "Prazo em dias é obrigatório")
    @Min(value = 1, message = "Prazo deve ser ao menos 1 dia")
    private Integer prazoDias;

    @NotNull(message = "Taxa de locação é obrigatória")
    private java.math.BigDecimal taxaLocacao;

    @NotNull(message = "Taxa de atraso é obrigatória")
    private java.math.BigDecimal taxaAtraso;

}
