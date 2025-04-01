package com.projetosboot.cadastro_alunoprof.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetosboot.cadastro_alunoprof.models.Professor;
import com.projetosboot.cadastro_alunoprof.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cadastro")
public class ProfessorController {

    private static final List<User> pessoas = new ArrayList<>();

    // Endpoint para listar todos os professores
    @GetMapping("/professores")
    public ResponseEntity<Object> index() {
        List<User> professores = pessoas.stream()
                .filter(user -> user instanceof Professor)
                .collect(Collectors.toList());

        if (professores.isEmpty()) {
            return ResponseEntity.status(204).body(Map.of("message", "Nenhum professor cadastrado no sistema."));
        }
        return ResponseEntity.ok(Map.of("professores", professores));
    }

    // Endpoint para adicionar um novo professor
    @PostMapping("/professores")
    public ResponseEntity<Object> store(@RequestBody @Valid Professor professor) {
        pessoas.add(professor);
        return ResponseEntity.status(201).body(Map.of(
                "success", "Professor cadastrado com sucesso!",
                "professor", professor
        ));
    }

    // Endpoint para mostrar detalhes de um professor específico
    @GetMapping("/professores/{professorID}")
    public ResponseEntity<Object> show(@PathVariable int professorID) {
        for (User user : pessoas) {
            if (user instanceof Professor) {
                Professor professor = (Professor) user;
                if (professor.getId() == professorID) {
                    return ResponseEntity.ok(Map.of(
                            "message", "Professor encontrado com sucesso!",
                            "professor", professor
                    ));
                }
            }
        }
        return ResponseEntity.status(404).body(Map.of(
                "error", "Professor não encontrado!",
                "message", "Não há professor cadastrado com o ID fornecido."
        ));
    }

    // Endpoint para atualizar as informações de um professor
    @PutMapping("/professores/{professorID}/edit")
    public ResponseEntity<Object> update(@PathVariable int professorID, @RequestBody Professor professorNovo) {
        for (User user : pessoas) {
            if (user instanceof Professor) {
                Professor professor = (Professor) user;
                if (professor.getId() == professorID) {
                    professor.setNome(professorNovo.getNome());
                    professor.setSobrenome(professorNovo.getSobrenome());
                    professor.setIdade(professorNovo.getIdade());
                    professor.setCpf(professorNovo.getCpf());
                    return ResponseEntity.ok(Map.of(
                            "success", "Informações do professor atualizadas com sucesso!",
                            "professor", professor
                    ));
                }
            }
        }
        return ResponseEntity.status(404).body(Map.of(
                "error", "Professor não encontrado!",
                "message", "Não foi possível localizar o professor com o ID fornecido para atualização."
        ));
    }

    // Endpoint para deletar um professor
    @DeleteMapping("/professores/{professorID}/delete")
    public ResponseEntity<Object> delete(@PathVariable int professorID) {
        for (User user : pessoas) {
            if (user instanceof Professor) {
                Professor professor = (Professor) user;
                if (professor.getId() == professorID) {
                    pessoas.remove(professor);
                    return ResponseEntity.ok(Map.of(
                            "success", "Professor deletado com sucesso!",
                            "message", "O professor foi removido do sistema permanentemente."
                    ));
                }
            }
        }
        return ResponseEntity.status(404).body(Map.of(
                "error", "Professor não encontrado!",
                "message", "Não há professor com o ID fornecido para remoção."
        ));
    }
}
