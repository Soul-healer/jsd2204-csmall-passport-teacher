package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginInfoVO;
import cn.tedu.csmall.passport.pojo.vo.AdminStandardVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {

    int insert(Admin admin);

    int deleteById(Long id);

    int update(Admin admin);

    int countByUsername(String username);

    AdminStandardVO getStandardById(Long id);

    AdminLoginInfoVO getLoginInfoByUsername(String username);

    /**
     * 查询管理员列表
     *
     * @return 管理员列表
     */
    List<AdminListItemVO> list();

}
