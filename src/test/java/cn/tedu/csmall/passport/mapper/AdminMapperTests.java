package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AdminMapperTests {

    @Autowired
    AdminMapper mapper;

    @Test
    void testInsert() {
        Admin admin = new Admin();
        admin.setUsername("test-admin-001");
        admin.setPassword("123456");

        log.debug("插入数据之前，参数={}", admin);
        int rows = mapper.insert(admin);
        log.debug("插入数据完成，受影响的行数={}", rows);
        log.debug("插入数据之后，参数={}", admin);
    }

    @Test
    void testCountByUsername() {
        String username = "test-admin-007";
        int count = mapper.countByUsername(username);
        log.debug("根据用户名【{}】统计，数量={}", username, count);
    }

    @Test
    void testGetLoginInfoByUsername() {
        String username = "test-admin-009";
        AdminLoginInfoVO loginInfo = mapper.getLoginInfoByUsername(username);
        log.debug("根据用户名【{}】查询用户的登录相关信息：{}", username, loginInfo);
    }

}
