package com.projet.task.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.task.models.Tarefa;
import com.projet.task.repository.TarefaRepository;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public boolean tarefaComMesmoNomeExiste(String nomeDatarefa) {
        return tarefaRepository.existsByNomeDatarefa(nomeDatarefa);
    }

    public Tarefa adicionarTarefa(Tarefa tarefa) {
        if (tarefaComMesmoNomeExiste(tarefa.getNomeDatarefa())) {
            throw new IllegalArgumentException("Já existe uma tarefa com o mesmo nome.");
        }

        tarefa.setOrdemDeSequenciaDaTarefa(obterOrdemSequencial());

        return tarefaRepository.save(tarefa);
    }

    private Long obterOrdemSequencial() {
        Long ordemMaxima = tarefaRepository.buscarMaiorOrdemSequencial();
        return (ordemMaxima != null) ? ordemMaxima + 1 : 0L; // Caso não tenha tarefas, começa com 0
    }

    public Tarefa buscarTarefaPorId(Long id) {
        return tarefaRepository.findById(id).orElse(null);
    }

    public List<Tarefa> listarTodasAsTarefas() {
        return tarefaRepository.findAll();
    }

    public Tarefa atualizarTarefa(Long id, Tarefa tarefaAtualizada) {
        Tarefa tarefaExistente = tarefaRepository.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Tarefa não encontrada.")
        );

        if (tarefaAtualizada.getNomeDatarefa() != null && 
            !tarefaAtualizada.getNomeDatarefa().equals(tarefaExistente.getNomeDatarefa()) &&
            tarefaComMesmoNomeExiste(tarefaAtualizada.getNomeDatarefa())) {
            throw new IllegalArgumentException("Já existe uma tarefa com o mesmo nome. Não é possível atualizar.");
        }

        if (tarefaAtualizada.getNomeDatarefa() != null) {
            tarefaExistente.setNomeDatarefa(tarefaAtualizada.getNomeDatarefa());
        }
        if (tarefaAtualizada.getCustoDaTarefa() != null) {
            tarefaExistente.setCustoDaTarefa(tarefaAtualizada.getCustoDaTarefa());
        }
        if (tarefaAtualizada.getDataLimiteDaTarefa() != null) {
            tarefaExistente.setDataLimiteDaTarefa(tarefaAtualizada.getDataLimiteDaTarefa());
        }

        return tarefaRepository.save(tarefaExistente);
    }

    public void deletarTarefa(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Tarefa não encontrada.")
        );
        tarefaRepository.delete(tarefa);
    }
}
