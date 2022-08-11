package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginInfoVO;
import cn.tedu.csmall.passport.pojo.vo.AdminStandardVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
    void testDeleteById() {
        Long id = 4L;
        int rows = mapper.deleteById(id);
        log.debug("根据id={}删除管理员成功，受影响的行数={}", id, rows);
    }

    @Test
    void testCountByUsername() {
        String username = "test-admin-007";
        int count = mapper.countByUsername(username);
        log.debug("根据用户名【{}】统计，数量={}", username, count);
    }

    @Test
    void testGetStandardById() {
        Long id = 6L;
        Object queryResult = mapper.getStandardById(id);
        log.debug("根据id={}查询管理员详情，查询结果={}", id, queryResult);
    }

    @Test
    void testGetLoginInfoByUsername() {
        String username = "root";
        AdminLoginInfoVO loginInfo = mapper.getLoginInfoByUsername(username);
        log.debug("根据用户名【{}】查询管理员的登录相关信息：{}", username, loginInfo);
    }

    @Test
    void testList() {
        List<?> list = mapper.list();
        log.debug("查询管理员列表，结果集中的数据的数量：{}", list.size());
        for (Object item : list) {
            log.debug("{}", item);
        }
    }

}
