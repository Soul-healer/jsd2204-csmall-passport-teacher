package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmall.passport.pojo.dto.AdminLoginDTO;

/**
 * 管理员业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public interface IAdminService {

    /**
     * 管理员登录
     * @param adminLoginDTO 封装了管理员登录相关数据的对象
     */
    void login(AdminLoginDTO adminLoginDTO);

    /**
     * 增加管理员
     *
     * @param adminAddNewDTO 新增的管理员对象
     */
    void addNew(AdminAddNewDTO adminAddNewDTO);

}
