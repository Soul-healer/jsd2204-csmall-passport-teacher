package cn.tedu.csmall.passport.service.impl;

import cn.tedu.csmall.passport.ex.ServiceException;
import cn.tedu.csmall.passport.mapper.AdminMapper;
import cn.tedu.csmall.passport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmall.passport.pojo.dto.AdminLoginDTO;
import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.security.AdminDetails;
import cn.tedu.csmall.passport.service.IAdminService;
import cn.tedu.csmall.passport.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理管理员业务的实现类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Value("${csmall.jwt.secret-key}")
    private String secretKey;

    @Override
    public String login(AdminLoginDTO adminLoginDTO) {
        // 日志
        log.debug("开始处理【管理员登录】的业务，参数：{}", adminLoginDTO);
        // 调用AuthenticationManager执行认证
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                adminLoginDTO.getUsername(), adminLoginDTO.getPassword());
        Authentication authenticateResult = authenticationManager.authenticate(authentication);
        log.debug("认证通过，返回的结果：{}", authenticateResult);
        log.debug("认证结果中的Principal的类型：{}",
                authenticateResult.getPrincipal().getClass().getName());

        // 处理认证结果
        AdminDetails loginUser = (AdminDetails) authenticateResult.getPrincipal();
        log.debug("认证结果中的管理员id：{}", loginUser.getId());
        log.debug("认证结果中的用户名：{}", loginUser.getUsername());
        Collection<GrantedAuthority> authorities = loginUser.getAuthorities();
        log.debug("认证结果中的权限列表：{}", authorities);
        // 【重要】将权限列表转换成JSON格式，用于存储到JWT中
        String authorityListString = JSON.toJSONString(authorities);

        // 生成JWT
        log.debug("准备生成JWT，secretKey：{}", secretKey);
        // 准备Claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginUser.getId());
        claims.put("username", loginUser.getUsername());
        claims.put("authorities", authorityListString);
        log.debug("生成JWT，向JWT中存入id：{}", loginUser.getId());
        log.debug("生成JWT，向JWT中存入username：{}", loginUser.getUsername());
        log.debug("生成JWT，向JWT中存入authorities：{}", authorityListString);
        // JWT的组成部分：Header（头），Payload（载荷），Signature（签名）
        String jwt = Jwts.builder()
                // Header：用于声明算法与此数据的类型，以下配置的属性名是固定的
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "jwt")
                // Payload：用于添加自定义数据，并声明有效期
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000))
                // Signature：用于指定算法与密钥（盐）
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        log.debug("生成的JWT：{}", jwt);
        return jwt;
    }

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
        // 对密码进行加密处理
        String rawPassword = admin.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        admin.setPassword(encodedPassword);
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
