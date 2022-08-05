package cn.tedu.csmall.passport.security;

import cn.tedu.csmall.passport.mapper.AdminMapper;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.debug("根据用户名【{}】从数据库查询用户信息……", s);
        // 调用AdminMapper对象，根据用户名（参数值）查询管理员信息
        AdminLoginInfoVO loginInfo = adminMapper.getLoginInfoByUsername(s);
        // 判断是否查询到有效结果
        if (loginInfo == null) {
            // 根据用户名没有找到任何管理员信息
            String message = "登录失败，用户名不存在！";
            log.warn(message);
            throw new UsernameNotFoundException(message);
        }

        // 从查询结果中找出权限信息
        List<String> permissions = loginInfo.getPermissions();

        // 准备返回结果
        log.debug("根据用户名【{}】从数据库查询到有效的用户信息：{}", s, loginInfo);
        UserDetails userDetails = User.builder()
                .username(loginInfo.getUsername())
                .password(loginInfo.getPassword())
                .accountExpired(false) // 账号是否已经过期
                .accountLocked(false) // 账号是否已经锁定
                .credentialsExpired(false) // 认证是否已经过期
                .disabled(loginInfo.getEnable() == 0) // 是否已经禁用
                .authorities(permissions.toArray(new String[]{})) // 权限，注意，此方法的参数值不可以为null
                .build();
        log.debug("即将向Spring Security返回UserDetails：{}", userDetails);
        return userDetails;
    }

}
