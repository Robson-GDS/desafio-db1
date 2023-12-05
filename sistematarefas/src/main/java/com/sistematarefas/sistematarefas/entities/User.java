package com.sistematarefas.sistematarefas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<Task> tasks = new ArrayList<>();

  public User(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
