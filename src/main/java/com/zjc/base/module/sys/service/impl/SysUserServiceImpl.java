package com.zjc.base.module.sys.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhilink.pf.framework.model.LoginModel;
import com.zhilink.pf.framework.thread.ThreadLocal4LoginModel;
import com.zhilink.pf.framework.utils.RedisUtil;
import com.zjc.base.constants.ExceptionConstants;
import com.zjc.base.constants.LongConstants;
import com.zjc.base.constants.StringConstants;
import com.zjc.base.module.sys.dao.SysUserMapper;
import com.zjc.base.module.sys.entity.SysUser;
import com.zjc.base.module.sys.entity.ValidReq;
import com.zjc.base.module.sys.service.SysUserService;
import com.zjc.base.util.EmailUtil;
import com.zjc.base.util.Md5Util;
import com.zjc.base.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zjc
 * @since 2022-01-05
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 用户缓存
     */
    private static final String USER_CACHE = "userCache_";

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private EmailUtil emailUtil;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     */
    @Override
    public LoginModel<SysUser> loginByNamePwd(SysUser sysUser) {
        if (ValidateUtil.isNotEmptyObj(sysUser)) {
            SysUser user = validateUser(sysUser.getLoginName(), sysUser.getPassword());
            LoginModel<SysUser> loginModel = new LoginModel();
            loginModel.setToken(sysUser.getSessionId());
            loginModel.setData(user);
            try {
                String id = USER_CACHE + sysUser.getSessionId();
                ThreadLocal4LoginModel.setLoginModel(loginModel);
                redisUtil.remove(id);
                redisUtil.set(id, user, LongConstants.ONE_DAY);
                user.setToken(sysUser.getSessionId());
                return loginModel;
            } catch (Exception e) {
                throw ExceptionConstants.DATA_ERROR;
            }
        }
        return null;
    }


    /**
     * 登出
     */
    @Override
    public void logout() {
        try {
            LoginModel loginModel = ThreadLocal4LoginModel.getLoginModel();
            if (ValidateUtil.isNotEmptyObj(loginModel)) {
                String token = loginModel.getToken();
                redisUtil.remove(USER_CACHE + token);
            }
        } catch (Exception e) {
            throw ExceptionConstants.DATA_ERROR;
        }
    }


    /**
     * 发送邮箱验证码
     * @return
     */
    @Override
    public boolean sendVfCode(ValidReq validReq) {
        if (StrUtil.isNotBlank(validReq.getEmail())) {
            String id = validReq.getType() + StringConstants.UNDER_LINE + validReq.getEmail();
            String code;
            if (Objects.nonNull(redisUtil.get(id))) {
                code = (String) redisUtil.get(id);
            } else {
                code = RandomUtil.randomNumbers(6);
            }
            emailUtil.sendEmail(validReq.getEmail(), code, validReq.getType());
            redisUtil.set(id, code, LongConstants.TEN_MIN);
            return true;
        }
        return false;
    }


    /**
     * 修改密码
     */
    @Override
    public boolean changePwd(ValidReq validReq) {
        if (ValidateUtil.isNotEmptyObj(validReq)) {
            boolean res = this.validEmail(validReq);
            if (res && StrUtil.isNotBlank(validReq.getNewPwd())) {
                SysUser usr;
                QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.lambda().eq(SysUser::getEmail, validReq.getEmail());
                if (sysUserService.count(userQueryWrapper) == 1) {
                    usr = sysUserService.getOne(userQueryWrapper);

                    usr.setPassword(validReq.getNewPwd());
                    return sysUserService.updateById(usr);
                } else {
                    throw ExceptionConstants.EMAIL_ERROR;
                }
            }
        }
        return false;
    }


    /**
     * 验证 验证码
     */
    @Override
    public boolean validEmail(ValidReq validReq) {
        if (ValidateUtil.isNotEmptyObj(validReq)) {
            if (StrUtil.isNotBlank(validReq.getEmail()) && StrUtil.isNotBlank(String.valueOf(validReq.getType()))) {
                String id = validReq.getType() + StringConstants.UNDER_LINE + validReq.getEmail();
                String code = (String) redisUtil.get(id);
                if (StrUtil.equals(validReq.getVfcode(), code)) {
                    redisUtil.remove(id);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 绑定邮箱
     */
    @Override
    public boolean bindEmail(ValidReq validReq) {
        if (ValidateUtil.isNotEmptyObj(validReq) && StrUtil.isNotBlank(validReq.getEmail()) && validReq.getType() == 1) {
            if (this.validEmail(validReq)) {
                LoginModel loginModel = ThreadLocal4LoginModel.getLoginModel();
                log.info("当前用户:" + JSONUtil.toJsonStr(loginModel));
                if (loginModel.getData() instanceof SysUser) {
                    SysUser user = (SysUser) loginModel.getData();
                    user.setEmail(validReq.getEmail());
                    sysUserService.updateById(user);
                    String id = USER_CACHE + user.getSessionId();
                    redisUtil.set(id, user, LongConstants.ONE_DAY);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 用户名/密码 校验
     */
    private SysUser validateUser(String userName, String password) {
        if (StrUtil.isNotBlank(userName) && StrUtil.isNotBlank(password)) {
            QueryWrapper<SysUser> validate = new QueryWrapper<>();
            validate.lambda().eq(SysUser::getLoginName, userName);
            validate.lambda().eq(SysUser::getPassword, Md5Util.getMD5String(password));
            int userCount = this.count(validate);
            if (userCount == 1) {
                return this.getOne(validate);
            } else if (userCount > 1) {
                throw ExceptionConstants.DATA_ERROR;
            } else {
                throw ExceptionConstants.LOGIN_ERROR;
            }
        } else {
            throw ExceptionConstants.LOGIN_EMPTY;
        }
    }
}
