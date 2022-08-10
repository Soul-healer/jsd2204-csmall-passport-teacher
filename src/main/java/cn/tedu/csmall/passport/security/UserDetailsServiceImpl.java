package cn.tedu.csmall.passport.security;

import cn.tedu.csmall.passport.mapper.AdminMapper;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        log.debug("根据用户名【{}】从数据库查询到有效的用户信息：{}", s, loginInfo);
        // 从查询结果中找出权限信息，转换成Collection<? extends GrantedAuthority>
        List<String> permissions = loginInfo.getPermissions(); // /ams/admin/delete
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }

        // 返回AdminDetails类型的对象
        AdminDetails adminDetails = new AdminDetails(
                loginInfo.getUsername(), loginInfo.getPassword(),
                loginInfo.getEnable() == 1, authorities);
        adminDetails.setId(loginInfo.getId());

        log.debug("即将向Spring Security返回UserDetails：{}", adminDetails);
        return adminDetails;
    }

}
