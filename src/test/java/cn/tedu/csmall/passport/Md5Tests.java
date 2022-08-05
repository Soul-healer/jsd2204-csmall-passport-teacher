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
            String salt = UUID.randomUUID().toString().replaceAll("-","");
            String encodedPassword = DigestUtils.md5DigestAsHex(
                    (salt + rawPassword + salt + rawPassword + salt).getBytes());
            // 123456abvckj,fsd789u4rkjldsiu
            log.debug("原文={}, 密文={}", rawPassword, encodedPassword+salt);
            // 原文=123456, 密文=e10adc3949ba59abbe56e057f20f883e
            // 0 >> xx
            // 1 >> xx
        }

        // R() >>> 约简函数 reduce
        // 123456
        // e10adc3949ba59abbe56e057f20f883e  >>>   e10adc
        // 96bf38d01b84aa16cf2bb9f55c61ac85  >>>   96bf38
        // c6349b59d5ca7f5fa05de13d26fcf20c  >>>   c6349b

        // 96bf38d01b84aa16cf2bb9f55c61ac85  >>>   96bf38

        // 123456  --(100000) (e10adc) (96bf38) -->   c6349b
        // 6位  MD5算法
    }

    @Test
    public void testMatches() {
        // c4094897737bc93e26f948cd3a25ece3ab0879a27c9248cc9629ed53d228c90d
        // f4e8ad198464a23acba3b561ae776ad42f6cd100b69d4fd8810a207e67fca0d2
        // c44a1f0bbe374ca7699f0102fe07599ffdfe2e737026408097195ef46af58640
        // 3b6dea9c6ae79c72406407ec84417e2a699fb2f1206c4216b579cedff06d014a
        // f2f3f98eddb42a394a157ad3aab32d0371fe16745233442ca69e0dc5a314a85e
        String rawPassword = "123456";
        String dbPassword = "f2f3f98eddb42a394a157ad3aab32d0371fe16745233442ca69e0dc5a314a85e";

        String salt = dbPassword.substring(32);
        String encodedPassword = DigestUtils.md5DigestAsHex(
                (salt + rawPassword + salt + rawPassword + salt).getBytes());
        System.out.println(dbPassword.equals(encodedPassword + salt));
    }

}
