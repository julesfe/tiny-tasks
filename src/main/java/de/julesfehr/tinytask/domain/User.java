package de.julesfehr.tinytask.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Access(AccessType.FIELD)
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private int id;

  @Column(name = "email", nullable = false, updatable = false)
  @Email(message = "Please provide a valid email")
  @NotEmpty(message = "Please provide an email")
  private String email;

  @Column(name = "username")
  @NotEmpty(message = "Please provide a username")
  private String username;

  @Column(name = "password")
  @Transient
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Task> tasks;

  public void removeTask(Task task) {
    // prevent endless loop
    if (!tasks.contains(task)) {
      return;
    }
    tasks.remove(task);
    task.setUser(null);
  }
}
