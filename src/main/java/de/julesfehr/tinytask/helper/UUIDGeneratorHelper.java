package de.julesfehr.tinytask.helper;

import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.util.IdGenerator;

@Component
public class UUIDGeneratorHelper implements IdGenerator {

  @Override
  public UUID generateId() {
    return UUID.randomUUID();
  }

}
