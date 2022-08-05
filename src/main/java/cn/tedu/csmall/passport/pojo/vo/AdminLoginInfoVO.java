package cn.tedu.csmall.passport.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminLoginInfoVO implements Serializable {

    /**
     * 数据id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（密文）
     */
    private String password;

    /**
     * 是否启用，1=启用，0=未启用
     */
    private Integer enable;

}
