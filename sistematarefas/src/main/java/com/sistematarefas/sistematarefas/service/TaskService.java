package com.sistematarefas.sistematarefas.service;

import com.sistematarefas.sistematarefas.entities.Task;
import com.sistematarefas.sistematarefas.repositories.TaskRepository;
import com.sistematarefas.sistematarefas.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
  @Autowired
  private TaskRepository repository;

  public List<Task> findAll() {
    return repository.findAll();
  }

  public Task findById(Long id) {
    return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));
  }

  public Task insert(Task task) {
    return repository.save(task);
  }

  public void delete(Long id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException(id);
    }
  }

  public Task update(Long id, Task obj) {
    try {
      Task entity = repository.getReferenceById(id);
      updateData(entity, obj);
      return repository.save(entity);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException(id);
    }
  }

  private void updateData(Task entity, Task obj) {
    entity.setTitle(obj.getTitle());
    entity.setDescription(obj.getDescription());
    entity.setTaskStatus(obj.getTaskStatus());
  }
}
