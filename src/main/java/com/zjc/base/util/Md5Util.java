package com.zjc.base.util;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;


/**
 * @author zjc
 * @description MD5加密工具类
 */
@Service
public class Md5Util {

    public static String getMD5String(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return StrUtil.EMPTY;
        }
    }
}
