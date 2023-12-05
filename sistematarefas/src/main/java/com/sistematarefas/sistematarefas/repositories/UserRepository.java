package com.sistematarefas.sistematarefas.repositories;

import com.sistematarefas.sistematarefas.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  @Override
  Optional<User> findById(Long id);

  @Override
  void deleteById(Long id);
}
