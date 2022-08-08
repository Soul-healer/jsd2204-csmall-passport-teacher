package cn.tedu.csmall.passport.config;

import cn.tedu.csmall.passport.filter.JwtAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security的配置类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    public SecurityConfiguration() {
        log.debug("加载配置类：SecurityConfiguration");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.debug("创建密码编码器：BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 请求路径白名单
        String[] urls = {
                "/favicon.ico",
                "/doc.html",
                "/**/*.js",
                "/**/*.css",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/admins/login"
        };

        http.csrf().disable(); // 禁用防止伪造跨域攻击

        http.authorizeRequests() // 要求请求必须被授权
                .antMatchers(urls) // 匹配一些路径
                .permitAll() // 允许访问
                .anyRequest() // 除以上配置以外的请求
                .authenticated(); // 经过认证的

        http.formLogin(); // 启用登录表单，未授权的请求均会重定向到登录表单

        // 将“JWT过滤器”添加在“认证过滤器”之前
        http.addFilterBefore(jwtAuthorizationFilter,
                UsernamePasswordAuthenticationFilter.class);
    }

}