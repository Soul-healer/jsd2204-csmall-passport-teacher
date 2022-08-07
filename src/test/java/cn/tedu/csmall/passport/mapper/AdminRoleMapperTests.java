package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.AdminRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AdminRoleMapperTests {

    @Autowired
    AdminRoleMapper mapper;

    @Test
    public void testInsert() {
        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(10L);
        adminRole.setRoleId(20L);

        log.debug("插入数据之前，参数：{}", adminRole);
        int rows = mapper.insert(adminRole);
        log.debug("rows = {}", rows);
        log.debug("插入数据之后，参数：{}", adminRole);
    }

}
