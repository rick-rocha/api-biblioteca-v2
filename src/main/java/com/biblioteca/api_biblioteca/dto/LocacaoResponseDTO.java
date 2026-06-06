package com.biblioteca.api_biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocacaoResponseDTO {

    private Long id;
    private String livroTitulo;
    private String usuarioNome;
    private String usuarioCpf;
    private LocalDate dataLocacao;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolvido;
    private BigDecimal taxaLocacao;
    private BigDecimal taxaAtraso;
    private BigDecimal multaTotal;
    private Boolean emAtraso;
    private Boolean devolvido;
    private Long diasEmAtraso;

}
