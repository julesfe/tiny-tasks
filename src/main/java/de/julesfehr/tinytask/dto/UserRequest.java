package de.julesfehr.tinytask.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserRequest {

  private int id;

  @NotEmpty
  private String email;

  @NotEmpty
  private String password;

}
