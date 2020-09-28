package pa.pavelan.demowebapp;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pa.pavelan.demowebapp.controllers.IndexController;

@SpringBootTest
class SmokeTest {
    @Autowired
    IndexController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
