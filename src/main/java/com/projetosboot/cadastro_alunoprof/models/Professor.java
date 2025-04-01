package com.projetosboot.cadastro_alunoprof.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public class Professor extends User {

    @NotNull(message = "ID não pode ser nulo")
    private Integer id;

    @NotNull(message = "CPF não pode ser nulo")
    @NotBlank(message = "CPF não pode ficar em branco")
    @CPF(message = "CPF Inválido")
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
