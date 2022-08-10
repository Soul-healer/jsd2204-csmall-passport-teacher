package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.ex.ServiceException;
import cn.tedu.csmall.passport.pojo.dto.AdminAddNewDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AdminServiceTests {

    @Autowired
    IAdminService service;

    @Test
    void testAddNew() {
        AdminAddNewDTO adminAddNewDTO = new AdminAddNewDTO();
        adminAddNewDTO.setUsername("test-admin-167");
        adminAddNewDTO.setPassword("123456");

//        Long[] roleIds = {2L, 4L};
//        adminAddNewDTO.setRoleIds(roleIds);

        try {
            service.addNew(adminAddNewDTO);
            log.debug("添加管理员成功！");
        } catch (ServiceException e) {
            log.debug(e.getMessage());
        }
    }

}
