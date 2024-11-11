package com.projet.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.projet.task.models.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Tarefa t WHERE t.nomeDatarefa = :nomeDatarefa")
    boolean existsByNomeDatarefa(String nomeDatarefa);

    @Query("SELECT MAX(t.ordemDeSequenciaDaTarefa) FROM Tarefa t")
    Long buscarMaiorOrdemSequencial();
}
