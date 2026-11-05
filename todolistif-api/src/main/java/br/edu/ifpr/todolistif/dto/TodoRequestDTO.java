package br.edu.ifpr.todolistif.dto;

import java.time.LocalDateTime;

public class TodoRequestDTO {
    private String title;
    private LocalDateTime deadLine;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }
}
