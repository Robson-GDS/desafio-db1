package com.sistematarefas.sistematarefas.controllers;

import com.sistematarefas.sistematarefas.entities.User;
import com.sistematarefas.sistematarefas.service.UserService;
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
@RequestMapping(value = "/users", produces = {"application/json"})
public class UserController {
  @Autowired
  private UserService service;

  @Operation(summary = "Busca todos os usuarios", method = "GET")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
          @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
          @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
          @ApiResponse(responseCode = "500", description = "Erro ao realizar busca por ID"),
  })
  @GetMapping
  public ResponseEntity<List<User>> findAll() {
    List<User> users = service.findAll();
    return ResponseEntity.ok().body(users);
  }

  @Operation(summary = "Busca um usuario por ID", method = "GET")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
          @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
          @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
          @ApiResponse(responseCode = "500", description = "Erro ao realizar busca por ID"),
  })
  @GetMapping(value = "{id}")
  public ResponseEntity<User> findById(@PathVariable Long id) {
    User user= service.findById(id);
    return ResponseEntity.ok().body(user);
  }

  @Operation(summary = "Cria um usuario", method = "POST")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Usuario criada com sucesso"),
          @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
          @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
          @ApiResponse(responseCode = "500", description = "Erro ao criar task"),
  })
  @PostMapping
  public ResponseEntity<User> insert(@RequestBody User user) {
    user = service.insert(user);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(user.getId()).toUri();
    return ResponseEntity.created(uri).body(user);
  }

  @Operation(summary = "Deleta um usuario por ID", method = "DELETE")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Usuario deletado com sucesso"),
          @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
          @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
          @ApiResponse(responseCode = "500", description = "Erro ao criar task"),
  })
  @DeleteMapping(value = "{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Atualiza usuario por ID", method = "PUT")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso"),
          @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
          @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
          @ApiResponse(responseCode = "500", description = "Erro ao realizar busca por ID"),
  })
  @PutMapping(value = "{id}")
  public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
    user = service.update(id, user);
    return ResponseEntity.ok().body(user);
  }
}
