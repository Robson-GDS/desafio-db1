package com.sistematarefas.sistematarefas.service;

import com.sistematarefas.sistematarefas.entities.User;
import com.sistematarefas.sistematarefas.repositories.UserRepository;
import com.sistematarefas.sistematarefas.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @InjectMocks
  private UserService service;

  @Mock
  private UserRepository repository;

  User user;

  User userDois;

  List<User> users;

  @BeforeEach
  void setUp() {
    user = new User();
    user.setId(1L);
    user.setName("User Teste");

    userDois = new User();
    userDois.setId(2L);
    userDois.setName("UseDois Teste");

    users = new ArrayList<>();
    users.add(user);
    users.add(userDois);
  }

  @Test
  void deveCriarUsuario() {
    Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);

    User response = service.insert(user);

    Assertions.assertEquals(user.getId(), response.getId());
    Assertions.assertEquals(user.getName(), response.getName());
  }

  @Test
  void deveListarTodosOsUsuarios() {
    Mockito.when(repository.findAll()).thenReturn(users);

    List<User> allUsers = service.findAll();

    Assertions.assertNotNull(allUsers);
    Assertions.assertEquals(2, allUsers.size());
  }

  @Test
  void deveProcurarUsuarioPorId() {
    Mockito.when(repository.findById(user.getId())).thenReturn(Optional.of(user));

    User response = service.findById(user.getId());

    Assertions.assertNotNull(user);
    Assertions.assertEquals(user.getId(), response.getId());
  }

  @Test
  public void deveGerarExceptionQuandoNaoLocalizarUsuarioPorId() {
    Mockito.when(repository.findById(3L)).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.findById(3L));

    Assertions.assertEquals("Sem retorno para o ID: 3", exception.getMessage());
  }

  @Test
  public void deveDeletarUsuarioPorId() {
    Long id = 1L;

    Mockito.doNothing().when(repository).deleteById(id);

    Assertions.assertDoesNotThrow(() -> service.delete(id));
  }

  @Test
  void deveGerarExceptionQuandoNaoLocalizarIdAoDeletarUsuario() {
    Long id = 3L;

    Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(id);

    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      service.delete(id);
    });
  }

  @Test
  void deveAtualizarUsuario() {
    Long id = 1L;

    User updateUser = new User();
    updateUser.setId(1L);
    updateUser.setName("User Atualizado");

    Mockito.when(repository.getReferenceById(id)).thenReturn(user);

    service.update(id, updateUser);

    Mockito.verify(repository).getReferenceById(id);
    Mockito.verify(repository).save(user);
  }

  @Test
  void deveGerarExceptionAoAtualizarUsuario() {
    Long id = 1L;

    User updateUser = new User();
    updateUser.setId(1L);
    updateUser.setName("User Atualizado");

    Mockito.doThrow(EntityNotFoundException.class).when(repository).getReferenceById(id);

    Assertions.assertThrows(ResourceNotFoundException.class, () -> service.update(id, updateUser));
  }
}