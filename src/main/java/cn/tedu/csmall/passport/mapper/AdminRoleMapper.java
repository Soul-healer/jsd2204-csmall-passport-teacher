package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.AdminRole;
import org.springframework.stereotype.Repository;

/**
 * 管理员与角色的关联关系的持久层Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface AdminRoleMapper {

    /**
     * 插入管理员与角色的关联数据
     *
     * @param adminRole 管理员与角色的关联数据
     * @return 受影响的行数
     */
    int insert(AdminRole adminRole);

    /**
     * 批量插入管理员与角色的关联数据
     *
     * @param adminRoleList 若干条管理员与角色的关联数据
     * @return 受影响的行数
     */
    int insertBatch(AdminRole[] adminRoleList);

}
