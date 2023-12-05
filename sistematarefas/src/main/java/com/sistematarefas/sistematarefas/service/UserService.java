package com.sistematarefas.sistematarefas.service;

import com.sistematarefas.sistematarefas.entities.User;
import com.sistematarefas.sistematarefas.repositories.UserRepository;
import com.sistematarefas.sistematarefas.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  @Autowired
  private UserRepository repository;

  public List<User> findAll() {
    return repository.findAll();
  }

  public User findById(Long id) {
    return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));
  }

  public User insert(User user) {
    return repository.save(user);
  }

  public void delete(Long id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException(id);
    }
  }

  public User update(Long id, User obj) {
    try {
      User entity = repository.getReferenceById(id);
      updateData(entity, obj);
      return repository.save(entity);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException(id);
    }
  }

  private void updateData(User entity, User obj) {
    entity.setName(obj.getName());
  }
}
