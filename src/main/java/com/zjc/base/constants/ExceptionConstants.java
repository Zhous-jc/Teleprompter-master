package com.zjc.base.constants;

import com.zhilink.pf.framework.exception.BizException;

/**
 * @author zjc
 * @create 2022-01-05
 */
public class ExceptionConstants {

    public static final BizException DATA_ERROR = new BizException("000000","数据异常,请联系管理员");
    /**
     * 登录
     */
    public static final BizException LOGIN_ERROR = new BizException("000011","用户名或密码错误");
    public static final BizException LOGIN_EMPTY = new BizException("000012","用户名/密码 不可为空");
    public static final BizException EMAIL_ERROR = new BizException("000013","未找到此邮箱对应的账号");
    public static final BizException EMAIL_BINDED = new BizException("000014","此邮箱已被其他账号绑定");
    /**
     * 登出
     */
    public static final BizException LOGOUT_ERROR = new BizException("000021","用户状态异常");
}
