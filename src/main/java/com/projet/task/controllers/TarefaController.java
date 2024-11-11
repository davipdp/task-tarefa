package com.projet.task.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projet.task.models.Tarefa;
import com.projet.task.services.TarefaService;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<?> addTarefa(@RequestBody Tarefa tarefa) {
        try {
            Tarefa novaTarefa = tarefaService.adicionarTarefa(tarefa);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaTarefa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> findById(@PathVariable Long id) {
        Tarefa tarefa = tarefaService.buscarTarefaPorId(id);
        if (tarefa != null) {
            return ResponseEntity.ok(tarefa);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> findAll() {
        List<Tarefa> tarefas = tarefaService.listarTodasAsTarefas();
        return ResponseEntity.ok(tarefas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        try {
            Tarefa tarefa = tarefaService.atualizarTarefa(id, tarefaAtualizada);
            return ResponseEntity.ok(tarefa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTarefa(@PathVariable Long id) {
        try {
            tarefaService.deletarTarefa(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
