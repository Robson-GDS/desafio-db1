package com.sistematarefas.sistematarefas.config;

import com.sistematarefas.sistematarefas.entities.Task;
import com.sistematarefas.sistematarefas.entities.User;
import com.sistematarefas.sistematarefas.entities.enums.TaskStatus;
import com.sistematarefas.sistematarefas.repositories.TaskRepository;
import com.sistematarefas.sistematarefas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("dev")
public class TestConfig implements CommandLineRunner {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TaskRepository taskRepository;

  @Override
  public void run(String... args) throws Exception {
//    User u1 = new User(1L, "Robson");
//    User u2 = new User(2L, "Gomes");
//
//    userRepository.saveAll(Arrays.asList(u1, u2));
//
//    Task t1 = new Task(1L, "Remover bugs", "necessario remover todos os bug do projeto X", TaskStatus.TEST,  u1);
//    Task t2 = new Task(2L, "incluir nome no formulario", "no formlulario x o campo nome esta errado", TaskStatus.DONE, u2);
//    Task t3 = new Task(1L, "Melhorar o codigo", "Realizar melhorias de codigo", TaskStatus.DEVELOPMENT, u1);
//
//    taskRepository.saveAll(Arrays.asList(t1, t2, t3));
  }
}
