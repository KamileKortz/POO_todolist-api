package br.edu.ifpr.todolistif.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpr.todolistif.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>{

}
