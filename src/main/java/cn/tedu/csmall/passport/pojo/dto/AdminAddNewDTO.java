package cn.tedu.csmall.passport.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AdminAddNewDTO implements Serializable {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    @NotNull(message = "必须提交用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotNull(message = "必须提交密码")
    private String password;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 头像URL
     */
    @ApiModelProperty("头像URL")
    private String avatar;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String phone;

    /**
     * 电子邮箱
     */
    @ApiModelProperty("电子邮箱")
    private String email;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 是否启用，1=启用，0=未启用
     */
    @ApiModelProperty("是否启用，1=启用，0=未启用")
    private Integer enable;

}