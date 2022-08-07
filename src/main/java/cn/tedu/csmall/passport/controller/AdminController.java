package cn.tedu.csmall.passport.controller;

import cn.tedu.csmall.passport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmall.passport.service.IAdminService;
import cn.tedu.csmall.passport.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "1. 管理员管理模块")
@Slf4j
@RequestMapping("/admins")
@RestController
public class AdminController {

    @Autowired
    private IAdminService adminService;

    public AdminController() {
        log.info("创建控制器：AdminController");
    }

    @ApiOperation("添加管理员")
    @ApiOperationSupport(order = 100)
    @PostMapping("/add-new")
    public JsonResult<Void> addNew(@Valid AdminAddNewDTO adminAddNewDTO) {
        log.debug("准备处理【添加管理员】的请求：{}", adminAddNewDTO);
        adminService.addNew(adminAddNewDTO);
        return JsonResult.ok();
    }

}
