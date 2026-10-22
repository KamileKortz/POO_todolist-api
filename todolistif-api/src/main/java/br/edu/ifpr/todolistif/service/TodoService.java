package br.edu.ifpr.todolistif.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifpr.todolistif.dto.TodoResponseDTO;
import br.edu.ifpr.todolistif.model.Todo;
import br.edu.ifpr.todolistif.repository.TodoRepository;

@Service 
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<TodoResponseDTO> listar(){
    
        List<Todo> tarefas = repository.findAll();
        List<TodoResponseDTO> resposta = new ArrayList<>();

        for (Todo tarefa : tarefas) {
            TodoResponseDTO dto = new TodoResponseDTO(tarefa);
            resposta.add(dto);
            
        }
        return resposta;
        
    }

}
