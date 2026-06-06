package com.biblioteca.api_biblioteca.enums;

public enum StatusLivro {
    ESTOQUE,
    LOCADO,
    INDISPONIVEL;

    public boolean permiteEmprestimo() {
        return this == ESTOQUE;
    }
}
