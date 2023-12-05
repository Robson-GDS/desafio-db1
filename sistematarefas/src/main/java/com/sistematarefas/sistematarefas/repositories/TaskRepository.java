package com.sistematarefas.sistematarefas.repositories;

import com.sistematarefas.sistematarefas.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
  @Override
  Optional<Task> findById(Long id);

  @Override
  void deleteById(Long id);
}
