package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginInfoVO;
import cn.tedu.csmall.passport.pojo.vo.AdminStandardVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {

    /**
     * 插入管理员数据
     *
     * @param admin 管理员数据
     * @return 受影响的行数
     */
    int insert(Admin admin);

    /**
     * 根据id删除管理员
     *
     * @param id 管理员id
     * @return 受影响的行数
     */
    int deleteById(Long id);

    /**
     * 更新管理员数据
     *
     * @param admin 包含了id和新数据的管理员数据对象
     * @return 受影响的行数
     */
    int update(Admin admin);

    /**
     * 根据用户名统计管理员的数量
     *
     * @param username 用户名
     * @return 匹配用户名的管理员的数据
     */
    int countByUsername(String username);

    /**
     * 根据id获取管理员详情
     *
     * @param id 管理员id
     * @return 匹配的管理员详情，如果没有匹配的数据，则返回null
     */
    AdminStandardVO getStandardById(Long id);

    /**
     * 根据用户名获取管理员的登录信息
     *
     * @param username 用户名
     * @return 管理员的登录信息，通常包含密码、权限等
     */
    AdminLoginInfoVO getLoginInfoByUsername(String username);

    /**
     * 查询管理员列表
     *
     * @return 管理员列表
     */
    List<AdminListItemVO> list();

}
