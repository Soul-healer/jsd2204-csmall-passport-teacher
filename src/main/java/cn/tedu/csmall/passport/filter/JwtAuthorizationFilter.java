package cn.tedu.csmall.passport.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        // 过滤器链继续执行，相当于：放行
        filterChain.doFilter(request, response);
    }

}
