package cn.tedu.csmall.passport;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class BCryptTests {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void testEncode() {
        for (int i = 0; i < 10; i++) {
            String rawPassword = "123456";
            String encodedPassword = passwordEncoder.encode(rawPassword);
            log.debug("原文={}, 密文={}", rawPassword, encodedPassword);
        }
    }

    @Test
    void testMatches() {
        // $2a$10$zqizYVHdw1VA5xf3hUGb3e7rv4TEv4KisSIVxvOJYLwVgDUwYfXSq
        // $2a$10$urORu6xPREon46gyqLztGO4AiQlFoul.W3wkFX3FjnLMQRqWnQ7sa
        String rawPassword = "123456";
        String encodedPassword = "$2a$10$urORu6xPREon46gyqLztGO4AiQlFoul.W3wkFX3FjnLMQRqWnQ7sa";
        boolean result = passwordEncoder.matches(rawPassword, encodedPassword);
        log.debug("原文={}, 密文={}, 验证结果={}", rawPassword, encodedPassword, result);
    }

}
