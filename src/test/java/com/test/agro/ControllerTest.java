package com.test.agro;

import com.test.agro.controller.AuthController;
import com.test.agro.controller.FieldController;
import com.test.agro.controller.TractorController;
import com.test.agro.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ControllerTest {
    @Autowired
    AuthController authController;
    @Autowired
    UserController userController;
    @Autowired
    FieldController fieldController;
    @Autowired
    TractorController tractorController;

    @Test
    public void contextLoads() {
        assertThat(authController).isNotNull();
        assertThat(userController).isNotNull();
        assertThat(fieldController).isNotNull();
        assertThat(tractorController).isNotNull();
    }
}
