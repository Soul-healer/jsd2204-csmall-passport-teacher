package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmall.passport.pojo.dto.AdminLoginDTO;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理员业务接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Transactional
public interface IAdminService {

    /**
     * 管理员登录
     *
     * @param adminLoginDTO 封装了管理员登录相关数据的对象
     * @return 登录成功后此用户的JWT数据
     */
    String login(AdminLoginDTO adminLoginDTO);

    /**
     * 增加管理员
     *
     * @param adminAddNewDTO 新增的管理员对象
     */
    void addNew(AdminAddNewDTO adminAddNewDTO);

    /**
     * 查询管理员列表
     *
     * @return 管理员列表
     */
    List<AdminListItemVO> list();

}