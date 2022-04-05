package com.zjc.base.util;

import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;


/**
 * 判空工具类
 */
public class ValidateUtil {

    public static boolean isNotEmptyUtil(Object value) {
        return !isEmptyUtil(value);
    }

    public static boolean isEmptyUtil(Object value) {
        if (value == null) {
            return true;
        } else if (value instanceof String) {
            return StrUtil.isBlank((String) value);
        } else if (value instanceof Collection) {
            return ((Collection) value).size() == 0;
        } else if (value instanceof Map) {
            return ((Map) value).size() == 0;
        } else if (value instanceof CharSequence) {
            return StrUtil.isBlank((CharSequence) value);
        } else if (value instanceof Boolean) {
            return false;
        } else if (value instanceof Number) {
            return false;
        } else if (value instanceof Character) {
            return false;
        } else if (value instanceof Date) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * 对象为空
     */
    public static boolean isEmptyObj(Object o) {
        return !isNotEmptyObj(o);
    }

    /**
     * 校验对象非空
     *
     * @param: Object
     * @return: boolean
     */
    public static boolean isNotEmptyObj(Object o) {
        boolean res = false;
        if (Objects.isNull(o)) {
            return false;
        }
        try {
            Class<?> aClass = o.getClass();
            Field[] fields = aClass.getDeclaredFields();
            for (int i = 1; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(o);
                if (Objects.nonNull(value)) {
                    if (StrUtil.isNotBlank(value.toString())) {
                        res = res || true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}

