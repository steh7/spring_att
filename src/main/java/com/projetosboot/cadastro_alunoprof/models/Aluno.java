package com.projetosboot.cadastro_alunoprof.models;

import jakarta.validation.constraints.NotNull;

public class Aluno extends User {

    @NotNull(message = "ID não pode ser nulo")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}