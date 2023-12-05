package com.sistematarefas.sistematarefas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sistematarefas.sistematarefas.entities.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "tasks")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String description;
  private TaskStatus taskStatus;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
