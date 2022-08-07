package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.pojo.dto.AdminAddNewDTO;

/**
 * 管理员业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public interface IAdminService {

    /**
     * 增加管理员
     *
     * @param adminAddNewDTO 新增的管理员对象
     */
    void addNew(AdminAddNewDTO adminAddNewDTO);

}
