package cn.tedu.csmall.passport.controller;

import cn.tedu.csmall.passport.pojo.vo.RoleListItemVO;
import cn.tedu.csmall.passport.service.IRoleService;
import cn.tedu.csmall.passport.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "2. 角色管理模块")
@Slf4j
@RequestMapping("/roles")
@RestController
public class RoleController {

    @Autowired
    private IRoleService roleService;

    public RoleController() {
        log.info("创建控制器：RoleController");
    }

    @ApiOperation("查询角色列表")
    @ApiOperationSupport(order = 401)
    @PostMapping("")
    public JsonResult<List<RoleListItemVO>> list() {
        log.debug("准备处理【查询角色列表】的请求");
        List<RoleListItemVO> list = roleService.list();
        return JsonResult.ok(list);
    }

}
