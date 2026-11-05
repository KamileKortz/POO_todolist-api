# POO_todolist-api

API de tarefas com Spring Boot (Controller, Service, Repository e DTO).

## Exemplos de requisições (Thunder Client)

### 1. Inserir tarefa
`POST http://localhost:8080/api/todos`
```json
{
  "title": "Estudar Spring Boot",
  "deadLine": "2026-10-30T18:00:00"
}
```
Resposta: `201 Created`
```json
{
  "id": 1,
  "title": "Estudar Spring Boot",
  "createdAt": "2026-10-22T10:00:00",
  "deadLine": "2026-10-30T18:00:00",
  "finishedAt": null
}
```

### 2. Excluir tarefa
`DELETE http://localhost:8080/api/todos/1`

Resposta: `204 No Content` (sem corpo).

### 3. Informar conclusão
`PATCH http://localhost:8080/api/todos/1/conclusao`
```json
{
  "finishedAt": "2026-10-22T10:30:00"
}
```
Resposta: `200 OK` com a tarefa, já mostrando o `finishedAt` registrado.

### 4. Listar concluídas e pendentes
- `GET http://localhost:8080/api/todos/concluidas` -> `200` (tarefas com `finishedAt` preenchido)
- `GET http://localhost:8080/api/todos/pendentes` -> `200` (tarefas com `finishedAt` null)

Se não houver resultados, as duas rotas devolvem `[]`.

## Solução adotada para os DTOs

O problema: o `TodoResponseDTO` não tinha `finishedAt`, então a API não conseguia
receber a data de conclusão nem mostrá-la na resposta. A solução foi separar
os DTOs por função:

| DTO | Função | Campos |
| --- | --- | --- |
| `TodoRequestDTO` | Receber os dados para cadastrar (POST) | `title`, `deadLine` |
| `TodoConclusaoDTO` | Receber a conclusão (PATCH) | `finishedAt` |
| `TodoResponseDTO` | Devolver a tarefa ao cliente | `id`, `title`, `createdAt`, `deadLine`, `finishedAt` |

- O cliente nunca envia `id`, `createdAt` nem `finishedAt` no cadastro: o ID é gerado pelo banco e o `createdAt` pelo construtor de `Todo`. Assim `finishedAt` começa null.
- O PATCH recebe um DTO só com `finishedAt`, por isso atualiza apenas esse campo e preserva o resto.
- Foi acrescentado `finishedAt` ao `TodoResponseDTO`, para o cliente conferir a data registrada.

## Caminho de uma requisição e responsabilidade de cada classe

Cliente -> **Controller** -> **Service** -> **Repository** -> banco, e a resposta volta pelo mesmo caminho.

- `TodoApiController` (Controller): recebe a requisição HTTP, lê o JSON/URL e define o status da resposta (201, 204, 200).
- `TodoService` (Service): executa as operações (criar `Todo`, buscar por id, alterar `finishedAt`, converter para DTO).
- `TodoRepository` (Repository): acessa o banco (`save`, `findById`, `deleteById` e as consultas por `finishedAt`).
- DTOs: transportam os dados de entrada e saída da API.
- `Todo` (model): entidade que representa a tarefa no banco.
