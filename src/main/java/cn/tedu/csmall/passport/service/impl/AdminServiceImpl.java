package cn.tedu.csmall.passport.service.impl;

import cn.tedu.csmall.passport.ex.ServiceException;
import cn.tedu.csmall.passport.mapper.AdminMapper;
import cn.tedu.csmall.passport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.service.IAdminService;
import cn.tedu.csmall.passport.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void addNew(AdminAddNewDTO adminAddNewDTO) {
        // 日志
        log.debug("开始处理【添加管理员】的业务，参数：{}", adminAddNewDTO);
        // 从参数中获取尝试添加的管理员的用户名
        String username = adminAddNewDTO.getUsername();
        // 调用adminMapper对象的countByUsername()方法进行统计
        int countByUsername = adminMapper.countByUsername(username);
        // 判断统计结果是否大于0
        if (countByUsername > 0) {
            // 是：日志，抛出ServiceException
            String message = "添加管理员失败，用户名【" + username + "】已经被占用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 创建新的Admin对象
        Admin admin = new Admin();
        // 调用BeanUtils.copyProperties()方法将参数的属性值复制到以上Admin对象中
        BeanUtils.copyProperties(adminAddNewDTO, admin);
        // 补全Admin对象的属性值：loginCount >>> 0
        admin.setLoginCount(0);
        // 日志
        log.debug("即将插入管理员数据：{}", admin);
        // 调用adminMapper对象的insert()方法插入数据，并获取返回的受影响的行数
        int rows = adminMapper.insert(admin);
        // 判断受影响的行数是否不等于1
        if (rows != 1) {
            // 是：日志，抛出ServiceException
            String message = "添加管理员失败，服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

}
