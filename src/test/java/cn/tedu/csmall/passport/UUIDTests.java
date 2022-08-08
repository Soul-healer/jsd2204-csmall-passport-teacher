package cn.tedu.csmall.passport;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
public class UUIDTests {

    @Test
    public void testGenerateUUID() {
        for (int i = 0; i < 5; i++) {
            log.debug("{}", UUID.randomUUID().toString());
        }
    }

}
