package cn.tedu.csmall.passport.controller;

import cn.tedu.csmall.passport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmall.passport.pojo.dto.AdminLoginDTO;
import cn.tedu.csmall.passport.security.LoginPrincipal;
import cn.tedu.csmall.passport.service.IAdminService;
import cn.tedu.csmall.passport.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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

    @ApiOperation("管理员登录")
    @ApiOperationSupport(order = 50)
    @PostMapping("/login")
    public JsonResult<String> login(AdminLoginDTO adminLoginDTO) {
        log.debug("准备处理【管理员登录】的请求：{}", adminLoginDTO);
        String jwt = adminService.login(adminLoginDTO);
        return JsonResult.ok(jwt);
    }

    @ApiOperation("添加管理员")
    @ApiOperationSupport(order = 100)
    @PreAuthorize("hasAuthority('/ams/admin/update')")
    @PostMapping("/add-new")
    public JsonResult<Void> addNew(@Valid AdminAddNewDTO adminAddNewDTO,
            @ApiIgnore @AuthenticationPrincipal LoginPrincipal loginPrincipal) {
        log.debug("准备处理【添加管理员】的请求：{}", adminAddNewDTO);
        log.debug("当前登录的用户（当事人）的id：{}", loginPrincipal.getId());
        log.debug("当前登录的用户（当事人）的用户名：{}", loginPrincipal.getUsername());
        adminService.addNew(adminAddNewDTO);
        return JsonResult.ok();
    }

}
