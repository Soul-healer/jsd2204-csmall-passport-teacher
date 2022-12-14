package cn.tedu.csmall.passport.service.impl;

import cn.tedu.csmall.passport.ex.ServiceException;
import cn.tedu.csmall.passport.mapper.AdminMapper;
import cn.tedu.csmall.passport.mapper.AdminRoleMapper;
import cn.tedu.csmall.passport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmall.passport.pojo.dto.AdminLoginDTO;
import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.entity.AdminRole;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.passport.pojo.vo.AdminStandardVO;
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

import java.time.LocalDateTime;
import java.util.*;

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
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Value("${csmall.jwt.secret-key}")
    private String secretKey;
    @Value("${csmall.jwt.duration-in-minute}")
    private Long durationInMinute;

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
        log.debug("准备生成JWT，有效时长：{}分钟，密钥：{}", durationInMinute, secretKey);
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
                .setExpiration(new Date(System.currentTimeMillis() + durationInMinute * 60 * 1000))
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
            String message = "添加管理员失败，服务器忙，请稍后再次尝试！[错误代码：1]";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }

        // 向管理员与角色关联的表中插入数据
        log.debug("准备向管理员与角色关联的表中插入数据");
        Long adminId = admin.getId();
        Long[] roleIds = adminAddNewDTO.getRoleIds();
        LocalDateTime now = LocalDateTime.now();

        AdminRole[] adminRoleList = new AdminRole[roleIds.length];
        for (int i = 0; i < roleIds.length; i++) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleIds[i]);
            adminRole.setGmtCreate(now);
            adminRole.setGmtModified(now);
            adminRoleList[i] = adminRole;
        }
        rows = adminRoleMapper.insertBatch(adminRoleList);
        // 判断受影响的行数是否小于1（可能插入多条数据，所以，大于或等于1的值均视为正确）
        if (rows < 1) {
            // 是：日志，抛出ServiceException
            String message = "添加管理员失败，服务器忙，请稍后再次尝试！[错误代码：2]";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    @Override
    public void deleteById(Long id) {
        log.debug("开始处理【根据id删除管理员】的业务：id={}", id);
        // 调用adminMapper根据参数id执行查询
        AdminStandardVO queryResult = adminMapper.getStandardById(id);
        // 判断查询结果是否为null
        if (queryResult == null) {
            // 抛出ServiceException，业务状态码：40400
            String message = "删除管理员失败！尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 调用adminMapper根据参数id删除管理员的数据，并获取返回值
        int rows = adminMapper.deleteById(id);
        // 判断返回值是否不为1
        if (rows != 1) {
            // 抛出ServiceException，业务状态码：DELETE对应的常量
            String message = "删除管理员失败！服务器忙，请稍后再次尝试！[错误代码：1]";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }

        // 调用adminRoleMapper根据参数id删除关联数据，并获取返回值
        rows = adminRoleMapper.deleteByAdminId(id);
        // 判断返回值是否小于1
        if (rows < 1) {
            // 抛出ServiceException，业务状态码：DELETE对应的常量
            String message = "删除管理员失败！服务器忙，请稍后再次尝试！[错误代码：2]";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    @Override
    public void setEnable(Long id) {
        log.debug("开始处理【启用管理员账号】的业务：id={}", id);
        // 根据id查询管理员数据
        AdminStandardVO queryResult = adminMapper.getStandardById(id);
        // 判断查询结果是否为null
        if (queryResult == null) {
            // 是：ServiceException：NOT_FOUND
            String message = "启用管理员账号失败！尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 判断查询结果中的enable是否为1
        if (queryResult.getEnable() == 1) {
            // 是：ServiceException：CONFLICT
            String message = "启用管理员账号失败！当前账号已经启用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // Admin admin = new Admin(); admin.setId(id); admin.setEnable(1);
        Admin admin = new Admin();
        admin.setId(id);
        admin.setEnable(1);
        // 执行更新，获取返回值
        int rows = adminMapper.update(admin);
        // 判断返回值是否不为1
        if (rows != 1) {
            // 是：ServiceException：UPDATE
            String message = "启用管理员账号失败！服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    @Override
    public void setDisable(Long id) {
        log.debug("开始处理【禁用管理员账号】的业务：id={}", id);
        // 根据id查询管理员数据
        AdminStandardVO queryResult = adminMapper.getStandardById(id);
        // 判断查询结果是否为null
        if (queryResult == null) {
            // 是：ServiceException：NOT_FOUND
            String message = "禁用管理员账号失败！尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 判断查询结果中的enable是否为1
        if (queryResult.getEnable() == 0) {
            // 是：ServiceException：CONFLICT
            String message = "禁用管理员账号失败！当前账号已经禁用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // Admin admin = new Admin(); admin.setId(id); admin.setEnable(1);
        Admin admin = new Admin();
        admin.setId(id);
        admin.setEnable(0);
        // 执行更新，获取返回值
        int rows = adminMapper.update(admin);
        // 判断返回值是否不为1
        if (rows != 1) {
            // 是：ServiceException：UPDATE
            String message = "禁用管理员账号失败！服务器忙，请稍后再次尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    @Override
    public List<AdminListItemVO> list() {
        // 日志
        log.debug("开始处理【查询管理员列表】的业务");
        return adminMapper.list();
    }

}
