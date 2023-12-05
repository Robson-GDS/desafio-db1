package com.sistematarefas.sistematarefas.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(Object id) {
    super("Sem retorno para o ID: " + id);
  }
}
