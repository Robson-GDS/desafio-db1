package com.sistematarefas.sistematarefas.controllers;

import com.sistematarefas.sistematarefas.entities.Task;
import com.sistematarefas.sistematarefas.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/tasks", produces = {"application/json"})
public class TaskController {
  @Autowired
  private TaskService service;

  @Operation(summary = "Busca todas tasks", method = "GET")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
          @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
          @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
          @ApiResponse(responseCode = "500", description = "Erro ao realizar busca por ID"),
  })
  @GetMapping
  public ResponseEntity<List<Task>> findAll() {
    List<Task> tasks = service.findAll();

    return ResponseEntity.ok().body(tasks);
  }

  @Operation(summary = "Busca uma task por ID", method = "GET")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
          @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
          @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
          @ApiResponse(responseCode = "500", description = "Erro ao realizar busca por ID"),
  })
  @GetMapping(value = "{id}")
  public ResponseEntity<Task> findById(@PathVariable Long id) {
    Task task = service.findById(id);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(task.getId()).toUri();
    return ResponseEntity.created(uri).body(task);
  }

  @Operation(summary = "Cria uma task", method = "POST")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Task criada com sucesso"),
          @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
          @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
          @ApiResponse(responseCode = "500", description = "Erro ao criar task"),
  })
  @PostMapping
  public ResponseEntity<Task> insert(@RequestBody Task task) {
    task = service.insert(task);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(task.getId()).toUri();
    return ResponseEntity.created(uri).body(task);
  }

  @Operation(summary = "Deleta uma task por ID", method = "DELETE")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Task deletada com sucesso"),
          @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
          @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
          @ApiResponse(responseCode = "500", description = "Erro ao criar task"),
  })
  @DeleteMapping(value = "{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Atualiza task por ID", method = "PUT")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso"),
          @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
          @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
          @ApiResponse(responseCode = "500", description = "Erro ao realizar busca por ID"),
  })
  @PutMapping(value = "{id}")
  public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task task) {
    task = service.update(id, task);
    return ResponseEntity.ok().body(task);
  }
}
