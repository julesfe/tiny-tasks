package de.julesfehr.tinytask;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureEmbeddedDatabase
public class TinyTaskApplicationTest {

  @Test
  public void contextLoads() {
  }

}
