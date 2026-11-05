package br.edu.ifpr.todolistif.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpr.todolistif.dto.TodoConclusaoDTO;
import br.edu.ifpr.todolistif.dto.TodoRequestDTO;
import br.edu.ifpr.todolistif.dto.TodoResponseDTO;
import br.edu.ifpr.todolistif.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoApiController {

    private final TodoService service;

    public TodoApiController(TodoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TodoResponseDTO> inserir(@RequestBody TodoRequestDTO dto) {
        TodoResponseDTO resposta = service.inserir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/conclusao")
    public ResponseEntity<TodoResponseDTO> concluir(@PathVariable Long id,
            @RequestBody TodoConclusaoDTO dto) {
        TodoResponseDTO resposta = service.concluir(id, dto);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/concluidas")
    public ResponseEntity<List<TodoResponseDTO>> listarConcluidas() {
        return ResponseEntity.ok(service.listarConcluidas());
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<TodoResponseDTO>> listarPendentes() {
        return ResponseEntity.ok(service.listarPendentes());
    }
}
