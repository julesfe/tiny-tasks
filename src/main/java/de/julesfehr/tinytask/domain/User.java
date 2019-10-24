package de.julesfehr.tinytask.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private int id;

  @Column(name = "email", nullable = false, updatable = false)
  @Email(message = "Please provide a valid email")
  @NotEmpty(message = "Please provide an email")
  private String email;

  @Column(name = "password")
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Task> tasks;

  @Column(name = "confirmation_token")
  private String confirmationToken;

  @Column(name = "enabled")
  private boolean enabled;

  public void removeTask(Task task) {
    // prevent endless loop
    if (!tasks.contains(task)) {
      return;
    }
    tasks.remove(task);
    task.setUser(null);
  }

}
