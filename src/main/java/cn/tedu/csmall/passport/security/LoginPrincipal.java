package cn.tedu.csmall.passport.security;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于保存到Security上下文中的、当前登录的管理员信息（不包含权限信息）
 */
@Data
public class LoginPrincipal implements Serializable {

    /**
     * 当事人id
     */
    private Long id;
    /**
     * 当事人用户名
     */
    private String username;

}
