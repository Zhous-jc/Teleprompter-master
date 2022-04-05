package com.zjc.base.module.sys.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhilink.pf.framework.annotation.NoLoginRequired;
import com.zhilink.pf.framework.model.LoginModel;
import com.zhilink.pf.framework.utils.SessionUtil;
import com.zjc.base.constants.ExceptionConstants;
import com.zjc.base.constants.StringConstants;
import com.zjc.base.module.sys.entity.SysUser;
import com.zjc.base.module.sys.entity.ValidReq;
import com.zjc.base.module.sys.service.SysUserService;
import com.zjc.base.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zjc
 * @create 2022-01-05
 */
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("login")
    @ResponseBody
    @NoLoginRequired
    public SysUser login(HttpServletRequest request, @RequestBody(required = false) SysUser sysUser) {
        log.info("登录:" + JSONUtil.toJsonStr(sysUser));
        if (ValidateUtil.isNotEmptyObj(sysUser)) {
            HttpSession session = request.getSession();
            sysUser.setSessionId(session.getId());
            LoginModel<SysUser> userModel = sysUserService.loginByNamePwd(sysUser);
            if (ValidateUtil.isNotEmptyObj(userModel)) {
                SessionUtil.setAttribute(request, StringConstants.USER, userModel);
            }
            return userModel.getData();
        }
        throw ExceptionConstants.DATA_ERROR;
    }


    @RequestMapping("logout")
    @ResponseBody
    public void logout(HttpServletRequest request) {
        log.info("退出登录...");
        try {
            sysUserService.logout();
            SessionUtil.removeAttribute(request, StringConstants.USER);
        } catch (Exception e) {
            throw ExceptionConstants.LOGOUT_ERROR;
        }
    }


    @RequestMapping("sendVfCode")
    @ResponseBody
    public boolean sendVfCode(@Validated @RequestBody ValidReq validReq) {
        log.info("发送邮箱验证码:" + JSONUtil.toJsonStr(validReq.getEmail()));
        if (ValidateUtil.isNotEmptyObj(validReq)) {
            QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.lambda().eq(SysUser::getEmail, validReq.getEmail());
            int count = sysUserService.count(userQueryWrapper);
            if (validReq.getType() != 1) {
                if (count != 1) {
                    throw ExceptionConstants.EMAIL_ERROR;
                }
            } else {
                if (count == 1) {
                    throw ExceptionConstants.EMAIL_BINDED;
                }
            }
            return sysUserService.sendVfCode(validReq);
        } else {
            throw ExceptionConstants.DATA_ERROR;
        }
    }


    @RequestMapping("changePwd")
    @ResponseBody
    public boolean changePwd(@Validated @RequestBody ValidReq validReq) {
        log.info("修改密码:" + JSONUtil.toJsonStr(validReq.getEmail()));
        return sysUserService.changePwd(validReq);
    }


    @RequestMapping("bindEmail")
    @ResponseBody
    public boolean bindEmail(@Validated @RequestBody ValidReq validReq) {
        log.info("绑定邮箱:" + JSONUtil.toJsonStr(validReq.getEmail()));
        return sysUserService.bindEmail(validReq);
    }
}
