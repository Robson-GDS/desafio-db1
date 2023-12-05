package com.sistematarefas.sistematarefas.service;

import com.sistematarefas.sistematarefas.entities.Task;
import com.sistematarefas.sistematarefas.entities.User;
import com.sistematarefas.sistematarefas.repositories.TaskRepository;
import com.sistematarefas.sistematarefas.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
  @InjectMocks
  private TaskService service;

  @Mock
  private TaskRepository repository;

  Task task;

  Task taskDois;

  List<Task> tasks;

  User user;

  @BeforeEach
  void setUp() {
    user = new User();
    user.setId(1L);
    user.setName("User Teste");

    task = new Task();
    task.setId(1L);
    task.setTitle("Title teste");
    task.setDescription("Descricao da task teste");
    task.setUser(user);

    taskDois = new Task();
    taskDois.setId(2L);
    taskDois.setTitle("Task dois teste");
    taskDois.setDescription("Task dois descricao");
    taskDois.setUser(user);

    tasks = new ArrayList<>();
    tasks.add(task);
    tasks.add(taskDois);
  }

  @Test
  void deveCriarTask() {
    Mockito.when(repository.save(Mockito.any(Task.class))).thenReturn(task);

    Task response = service.insert(task);

    Assertions.assertEquals(task.getId(), response.getId());
    Assertions.assertEquals(task.getTitle(), response.getTitle());
    Assertions.assertEquals(task.getDescription(), response.getDescription());
    Assertions.assertEquals(task.getUser(), response.getUser());
  }

  @Test
  void deveListarTodasTask() {
    Mockito.when(repository.findAll()).thenReturn(tasks);

    List<Task> allTasks = service.findAll();

    Assertions.assertNotNull(allTasks);
    Assertions.assertEquals(2, allTasks.size());
  }

  @Test
  void deveBuscarTaskPorId() {
    Mockito.when(repository.findById(task.getId())).thenReturn(Optional.of(task));

    Task response = service.findById(task.getId());

    Assertions.assertNotNull(task);
    Assertions.assertEquals(task.getId(), response.getId());
  }

  @Test
  public void deveDeletarTaskPorId() {
    Long id = 1L;

    Mockito.doNothing().when(repository).deleteById(id);

    Assertions.assertDoesNotThrow(() -> service.delete(id));
  }

  @Test
  void deveGerarExceptionQuandoNaoLocalizarATaskPorId() {
    Long id = 3L;

    Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(id);

    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      service.delete(id);
    });
  }

  @Test
  public void deveGerarExceptionQuandoNaoBuscarUsuarioPorId() {
    Mockito.when(repository.findById(3L)).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.findById(3L));

    Assertions.assertEquals("Sem retorno para o ID: 3", exception.getMessage());
  }

  @Test
  void deveAtualizarTask() {
    Long id = 1L;

    Task updateTask = new Task();
    updateTask.setTitle("Titulo atualizado");

    Mockito.when(repository.getReferenceById(id)).thenReturn(task);

    service.update(id, updateTask);

    Mockito.verify(repository).getReferenceById(id);
    Mockito.verify(repository).save(task);
  }

  @Test
  void deveGerarExceptionAoAtualizarTask() {
    Long id = 1L;

    Task updateTask = new Task();
    updateTask.setTitle("Titulo atualizado");

    Mockito.doThrow(EntityNotFoundException.class).when(repository).getReferenceById(id);

    Assertions.assertThrows(ResourceNotFoundException.class, () -> service.update(id, updateTask));
  }
}