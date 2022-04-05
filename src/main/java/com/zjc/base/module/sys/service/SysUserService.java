package com.zjc.base.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhilink.pf.framework.model.LoginModel;
import com.zjc.base.module.sys.entity.SysUser;
import com.zjc.base.module.sys.entity.ValidReq;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author zjc
 * @since 2022-01-05
 */
public interface SysUserService extends IService<SysUser> {

    LoginModel<SysUser> loginByNamePwd(SysUser sysUser);

    void logout();

    boolean sendVfCode(ValidReq validReq);

    boolean changePwd(ValidReq validReq);

    boolean validEmail(ValidReq validReq);

    boolean bindEmail(ValidReq validReq);
}
