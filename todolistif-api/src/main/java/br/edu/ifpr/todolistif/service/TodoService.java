package br.edu.ifpr.todolistif.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifpr.todolistif.dto.TodoConclusaoDTO;
import br.edu.ifpr.todolistif.dto.TodoRequestDTO;
import br.edu.ifpr.todolistif.dto.TodoResponseDTO;
import br.edu.ifpr.todolistif.model.Todo;
import br.edu.ifpr.todolistif.repository.TodoRepository;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<TodoResponseDTO> listar() {

        List<Todo> tarefas = repository.findAll();
        List<TodoResponseDTO> resposta = new ArrayList<>();

        for (Todo tarefa : tarefas) {
            TodoResponseDTO dto = new TodoResponseDTO(tarefa);
            resposta.add(dto);
        }
        return resposta;
    }

    public TodoResponseDTO inserir(TodoRequestDTO dto) {
        Todo tarefa = new Todo();
        tarefa.setTitle(dto.getTitle());
        tarefa.setDeadLine(dto.getDeadLine());

        Todo salva = repository.save(tarefa);
        return new TodoResponseDTO(salva);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public TodoResponseDTO concluir(Long id, TodoConclusaoDTO dto) {
        Todo tarefa = repository.findById(id).get();
        tarefa.setFinishedAt(dto.getFinishedAt());

        Todo salva = repository.save(tarefa);
        return new TodoResponseDTO(salva);
    }

    public List<TodoResponseDTO> listarConcluidas() {
        List<Todo> tarefas = repository.findByFinishedAtIsNotNull();
        return converter(tarefas);
    }

    public List<TodoResponseDTO> listarPendentes() {
        List<Todo> tarefas = repository.findByFinishedAtIsNull();
        return converter(tarefas);
    }

    private List<TodoResponseDTO> converter(List<Todo> tarefas) {
        List<TodoResponseDTO> resposta = new ArrayList<>();

        for (Todo tarefa : tarefas) {
            resposta.add(new TodoResponseDTO(tarefa));
        }
        return resposta;
    }
}
