package com.projetosboot.cadastro_alunoprof.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class User {

    @NotNull(message = "Nome não pode ser nulo")
    @NotBlank(message = "Nome não pode estar vazio")
    private String nome;

    @NotNull(message = "Sobrenome não pode ser nulo")
    @NotBlank(message = "Sobrenome não pode estar vazio")
    private String sobrenome;

    @NotNull(message = "Idade não pode ser NULO")
    private Integer idade;

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
}
