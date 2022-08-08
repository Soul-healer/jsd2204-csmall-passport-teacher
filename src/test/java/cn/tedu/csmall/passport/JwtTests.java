package cn.tedu.csmall.passport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtTests {

    // 密钥（盐）
    String secretKey = "nmlfdasfdsaurefuifdknjfdskjhajhef";

    // 测试生成JWT
    @Test
    public void testGenerateJwt() {
        // 准备Claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 9527);
        claims.put("name", "liulaoshi");

        // JWT的组成部分：Header（头），Payload（载荷），Signature（签名）
        String jwt = Jwts.builder()
                // Header：用于声明算法与此数据的类型，以下配置的属性名是固定的
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "jwt")
                // Payload：用于添加自定义数据，并声明有效期
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 3 * 60 * 1000))
                // Signature：用于指定算法与密钥（盐）
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        log.debug("JWT = {}", jwt);
        // eyJhbGciOiJIUzI1NiIsInR5cCI6Imp3dCJ9
        // .
        // eyJuYW1lIjoibGl1bGFvc2hpIiwiaWQiOjk1MjcsImV4cCI6MTY1OTkzMTUyMX0
        // .
        // TFyWBZ3l-y6rYbEYiVBbQjqnFNsFFR07K8lDES9TPs4

        // eyJhbGciOiJIUzI1NiIsInR5cCI6Imp3dCJ9.eyJuYW1lIjoibGl1bGFvc2hpIiwiaWQiOjk1MjcsImV4cCI6MTY1OTkzOTM0N30.7rj8Lhus1EYXUxE4Zy1wx1WFpbvxIQEmya3-A9WZP20
        // eyJhbGciOiJIUzI1NiIsInR5cCI6Imp3dCJ9.eyJuYW1lIjoibGl1bGFvc2hpIiwiaWQiOjk1MjcsImV4cCI6MTY1OTkzOTUzMH0.lwD_PzrqGXEgQs3KmMjsYzTmhsKbGhKnd1WkDkFpj5M
    }

    @Test
    public void testParseJwt() {
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6Imp3dCJ9.eyJuYW1lIjoibGl1bGFvc2hpIiwiaWQiOjk1MjcsImV4cCI6MTY1OTkzOTkyMn0.0UJ7GWaRs1SDQh6pHhCNRJntkdEfXVC0jqIbduOHhOM";
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
        Object id = claims.get("id");
        Object name = claims.get("name");
        log.debug("id={}", id);
        log.debug("name={}", name);
    }

}
