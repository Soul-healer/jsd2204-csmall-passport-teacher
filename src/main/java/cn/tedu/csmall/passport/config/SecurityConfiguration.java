package cn.tedu.csmall.passport.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.debug("创建密码编码器：BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
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
                "/v2/api-docs"
        };

        http.csrf().disable(); // 禁用防止伪造跨域攻击

        http.authorizeRequests() // 要求请求必须被授权
            .antMatchers(urls) // 匹配一些路径
            .permitAll() // 允许访问
            .anyRequest() // 除以上配置以外的请求
            .authenticated(); // 经过认证的

        http.formLogin(); // 启用登录表单，未授权的请求均会重定向到登录表单
    }

}