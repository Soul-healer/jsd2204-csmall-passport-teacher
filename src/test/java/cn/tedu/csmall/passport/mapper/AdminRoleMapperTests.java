package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.AdminRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

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

    @Test
    public void testInsertBatch() {
        Long adminId = 5L;
        Long[] roleIds = {2L, 3L, 4L};
        LocalDateTime now = LocalDateTime.now();

        AdminRole[] adminRoleList = new AdminRole[roleIds.length];
        for (int i = 0; i < roleIds.length; i++) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleIds[i]);
            adminRole.setGmtCreate(now);
            adminRole.setGmtModified(now);
            adminRoleList[i] = adminRole;
        }

        int rows = mapper.insertBatch(adminRoleList);
        log.debug("批量插入管理员与角色的关联数据成功，受影响的行数 = {}", rows);
    }

    @Test
    void testDeleteByAdminId() {
        Long adminId = 24L;
        int rows = mapper.deleteByAdminId(adminId);
        log.debug("根据管理员id={}删除管理员与角色的关联数据，受影响的行数={}", adminId, rows);
    }

}
