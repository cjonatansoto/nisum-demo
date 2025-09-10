package com.nisum.technical.exercise.application.util;

import com.nisum.technical.exercise.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "app.security.jwt.secret=my-super-secret-key-which-is-long-enough-256bit!",
        "app.security.jwt.expirationSeconds=3600"
})
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void testGenerateToken() {
        User user = new User();
        user.setEmail("test@example.com");
        String token = jwtUtil.generateToken(user);
        assertThat(token).isNotNull();
    }
}

