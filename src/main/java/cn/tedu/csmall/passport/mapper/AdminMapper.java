package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {

    int insert(Admin admin);

    int countByUsername(String username);

}
