package com.zjc.base.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author zjc
 * @since 2022-01-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * sessionId
     */
    @TableField(exist = false)
    private String sessionId;

    /**
     * token
     */
    @TableField(exist = false)
    private String token;

    /**
     * 昵称
     */
    private String name;

    /**
     * 性别 0默认 1男2女
     */
    private Integer sex;

    /**
     * 年龄
     */
    private String age;

    /**
     * 身份证
     */
    private String userId;

    /**
     * 用户头像
     */
    private String photo;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户类型(1-管理员，2-普通用户)
     */
    private String userType;

}
