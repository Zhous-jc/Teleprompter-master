package com.zjc.base.module.sys.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author: zjc
 * @create: 2022-01-06
 */
@Data
public class ValidReq {

    /**
     * 邮箱号
     */
    @Email
    private String email;

    /**
     * 手机号
     */
    @Length(max = 11, message = "手机号码最大长度为11位")
    @Pattern(regexp = "^\\s{0}$|^1(3|4|5|7|8)\\d{9}$", message = "手机号栏位格式错误,请检查")
    private String mobile;

    /**
     * 验证码
     */
    private String vfcode;

    /**
     * 业务类型 1-绑定 2-登录 3-改密
     */
    @NotNull
    private int type;

    /**
     * 新密码
     */
    private String newPwd;
}
