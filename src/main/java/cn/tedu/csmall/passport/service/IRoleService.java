package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.pojo.vo.RoleListItemVO;

import java.util.List;

/**
 * 角色业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public interface IRoleService {

    /**
     * 查询角色列表
     *
     * @return 角色列表
     */
    List<RoleListItemVO> list();

}
