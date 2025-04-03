package com.projetosboot.cadastro_alunoprof.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetosboot.cadastro_alunoprof.models.Aluno;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cadastro")
public class AlunoController {

    // Lista para armazenar os alunos
    private static final List<User> alunos = new ArrayList<>();

    // Endpoint para listar todos os alunos
    @GetMapping("/alunos")
    public ResponseEntity<Object> index() {
        if (alunos.isEmpty()) {
            return ResponseEntity.status(204).body(Map.of("message", "Nenhum aluno cadastrado no sistema."));
        }
        return ResponseEntity.ok(alunos);
    }

    // Endpoint para adicionar um novo aluno
    @PostMapping("/alunos")
    public ResponseEntity<Object> store(@RequestBody @Valid Aluno aluno) {
        // Verifica se o aluno já existe (baseado no ID)
        Optional<Aluno> alunoExistente = alunos.stream()
                .filter(a -> a.getId() == aluno.getId())
                .findFirst();

        if (alunoExistente.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Aluno não cadastrado!",
                "message", "Já existe um aluno com o ID fornecido. Tente um ID diferente."
            ));
        }

        alunos.add(aluno);
        return ResponseEntity.status(201).body(Map.of( //Requisição foi bem-sucedida e um novo recurso foi criado. 
                "success", "Aluno cadastrado com sucesso!",
                "aluno", aluno
        ));
    }

    // Endpoint para mostrar detalhes de um aluno específico
    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<Object> show(@PathVariable int alunoId) {
        Optional<Aluno> aluno = alunos.stream()
                .filter(a -> a.getId() == alunoId)
                .findFirst();

        if (aluno.isPresent()) {
            return ResponseEntity.ok(Map.of(
                    "message", "Aluno encontrado com sucesso!",
                    "aluno", aluno.get()
            ));
        }

        return ResponseEntity.status(404).body(Map.of( //404 Not Found: O servidor não encontrou a representação atual do recurso solicitado 
                "error", "Aluno não encontrado!",
                "message", "Não há aluno cadastrado com o ID fornecido."
        ));
    }

    // Endpoint para atualizar as informações de um aluno
    @PutMapping("/aluno/{id}/edit")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody @Valid Aluno alunoNovo) {
        Optional<Aluno> alunoOpt = alunos.stream()
                .filter(a -> a.getId() == id)
                .findFirst();

        if (alunoOpt.isPresent()) {
            Aluno aluno = alunoOpt.get();
            // Verifica se o aluno tem idade suficiente para alterar os dados
            if (aluno.getIdade() >= 18) {
                aluno.setNome(alunoNovo.getNome());
                aluno.setSobrenome(alunoNovo.getSobrenome());
                aluno.setIdade(alunoNovo.getIdade());
                return ResponseEntity.ok(Map.of(
                        "success", "Informações do aluno atualizadas com sucesso!",
                        "aluno", aluno
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Atualização negada!",
                        "message", "Somente alunos maiores de 18 anos podem alterar suas informações."
                ));
            }
        }

        return ResponseEntity.status(404).body(Map.of(
                "error", "Aluno não encontrado!",
                "message", "Não foi possível localizar o aluno com o ID fornecido para atualização."
        ));
    }

    // Endpoint para deletar um aluno
    @DeleteMapping("/aluno/{id}/delete")
    public ResponseEntity<Object> destroy(@PathVariable int id) {
        Optional<Aluno> alunoOpt = alunos.stream()
                .filter(a -> a.getId() == id)
                .findFirst();

        if (alunoOpt.isPresent()) {
            alunos.remove(alunoOpt.get());
            return ResponseEntity.ok(Map.of(
                    "success", "Aluno deletado com sucesso!",
                    "message", "O aluno foi removido do sistema permanentemente."
            ));
        }

        return ResponseEntity.status(404).body(Map.of(
                "error", "Aluno não encontrado!",
                "message", "Não há aluno com o ID fornecido para remoção."
        ));
    }
}
