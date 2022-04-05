package com.zjc.base.util;

import com.zjc.base.constants.StringConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

/**
 * @author zjc
 * @create 2022-1-6
 * @desc 邮件发送工具类
 */
@Service
@Slf4j
public class EmailUtil {

    private static final String emailName = "数据中心";
    private static final String bind = "邮箱验证";

    @Resource
    private JavaMailSenderImpl mailSender;

    /**
     * @param emailAddress
     * @param value
     * @param type         1-绑定 2-登录 3-改密 4-注册
     * @return
     */
    public boolean sendEmail(String emailAddress, String value, int type) {
        String content = null;
        switch (type) {
            case 1:
                content = "点击链接进行绑定:";
                break;
            case 2:
                content = "登录验证码:";
                break;
            case 3:
                content = "修改密码验证码:";
                break;
            case 4:
                content = "注册验证码:";
                break;
            default:
                break;
        }
        return send(emailAddress, bind, content + value);
    }

    private boolean send(String address, String subject, String content) {
        try {
            MimeMessage mess = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mess, true);
            //发件人
            helper.setFrom(emailName + StringConstants.LEFT_BRACE + mailSender.getUsername() + StringConstants.RIGHT_BRACE);
            //收件人
            helper.setTo(address);
            //邮件标题
            helper.setSubject(subject);
            //邮件内容
            helper.setText(content, true);

            mailSender.send(mess);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
