package cn.tedu.csmall.passport.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    public JwtAuthorizationFilter() {
        log.debug("创建过滤器：JwtAuthorizationFilter");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.debug("执行JwtAuthorizationFilter");
        // 从请求头中获取JWT
        String jwt = request.getHeader("Authorization");
        log.debug("从请求头中获取JWT：{}", jwt);

        // 判断JWT数据是否不存在
        if (!StringUtils.hasText(jwt) || jwt.length() < 80) {
            log.debug("获取到的JWT是无效的，直接放行，交由后续的组件继续处理！");
            // 过滤器链继续执行，相当于：放行
            filterChain.doFilter(request, response);
            // 返回，终止当前方法本次执行
            return;
        }

        // 尝试解析JWT
        String secretKey = "nmlfdasfdsaurefuifdknjfdskjhajhef";
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
        Object username = claims.get("username");
        log.debug("从JWT中解析得到username：{}", username);

        // 准备Authentication对象，后续会将此对象封装到Security的上下文中
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("临时使用的权限"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username, null, authorities);

        // 将用户信息封装到Security的上下文中
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        // 过滤器链继续执行，相当于：放行
        filterChain.doFilter(request, response);
    }

}
