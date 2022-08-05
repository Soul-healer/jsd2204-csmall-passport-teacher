package cn.tedu.csmall.passport.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 假设root是可用的用户名，其它用户名均不可用
        if ("root".equals(s)) {
            // 返回模拟的root用户信息
            UserDetails userDetails = User.builder()
                    .username("root")
                    .password("$2a$10$oxvr08D3W0oiesfGPZ8miuPy6kWGst6lz3.qZ29upo8yTjROWh4eC")
                    .accountExpired(false) // 账号是否已经过期
                    .accountLocked(false) // 账号是否已经锁定
                    .credentialsExpired(false) // 认证是否已经过期
                    .disabled(false) // 是否已经禁用
                    .authorities("这是临时使用的且无意义的权限值") // 权限，注意，此方法的参数值不可以为null
                    .build();
            return userDetails;
        }
        return null;
    }

}
