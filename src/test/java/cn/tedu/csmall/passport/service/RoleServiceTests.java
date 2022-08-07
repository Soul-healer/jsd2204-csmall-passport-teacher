package cn.tedu.csmall.passport.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class RoleServiceTests {

    @Autowired
    IRoleService service;

    @Test
    public void testList() {
        List<?> list = service.list();
        log.debug("查询角色列表完成，结果集中的数据的数量={}", list.size());
        for (Object item : list) {
            log.debug("{}", item);
        }
    }

}
