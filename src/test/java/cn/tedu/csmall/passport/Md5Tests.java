package cn.tedu.csmall.passport;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Slf4j
public class Md5Tests {

    @Test
    public void testMd5() {
        for (int i = 0; i < 10; i++) {
            String rawPassword = "123456";
            String salt = UUID.randomUUID().toString();
            String encodedPassword = DigestUtils.md5DigestAsHex(
                    (salt + rawPassword + salt + rawPassword + salt).getBytes());
            // 123456abvckj,fsd789u4rkjldsiu
            log.debug("原文={}, 密文={}", rawPassword, encodedPassword + salt.replaceAll("-", ""));
            // 原文=123456, 密文=e10adc3949ba59abbe56e057f20f883e
            // 0 >> xx
            // 1 >> xx
        }
    }

}
