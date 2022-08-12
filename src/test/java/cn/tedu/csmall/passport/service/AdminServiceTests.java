package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.ex.ServiceException;
import cn.tedu.csmall.passport.pojo.dto.AdminAddNewDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class AdminServiceTests {

    @Autowired
    IAdminService service;

    @Test
    void testAddNew() {
        AdminAddNewDTO adminAddNewDTO = new AdminAddNewDTO();
        adminAddNewDTO.setUsername("test-admin-165");
        adminAddNewDTO.setPassword("123456");

        Long[] roleIds = {2L, 4L};
        adminAddNewDTO.setRoleIds(roleIds);

        try {
            service.addNew(adminAddNewDTO);
            log.debug("添加管理员成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void testDeleteById() {
        Long id = 10L;
        try {
            service.deleteById(id);
            log.debug("删除管理员成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void testSetEnable() {
        Long id = 200L;
        try {
            service.setEnable(id);
            log.debug("启用管理员成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void testSetDisable() {
        Long id = 2L;
        try {
            service.setDisable(id);
            log.debug("禁用管理员成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    void testList() {
        List<?> list = service.list();
        log.debug("查询管理员列表，结果集中的数据的数量：{}", list.size());
        for (Object item : list) {
            log.debug("{}", item);
        }
    }

}
