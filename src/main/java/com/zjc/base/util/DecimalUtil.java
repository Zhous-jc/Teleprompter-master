package com.zjc.base.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @ClassName DecimalUtil
 * @Desc 小数处理工具类
 **/
public class DecimalUtil {

    /**
     * @Description: roundByScale
     * @Param: [需处理的小数, 保留位数]
     * @return: java.math.BigDecimal
     * @Date: 2019/11/14
     */
    public static BigDecimal roundByScale(double v, int scale) {
        if (scale < 0) {
            return BigDecimal.ZERO;
        }
        if (scale == 0) {
            String format = new DecimalFormat("0").format(v);
            return new BigDecimal(format);
        }
        String formatStr = "0.";
        for (int i = 0; i < scale; i++) {
            formatStr = formatStr + "0";
        }
        String format = new DecimalFormat(formatStr).format(v);
        return new BigDecimal(format);
    }

    /**
     * @Description: roundByScale
     * @Param: [需处理的小数, 保留位数]
     * @return: java.math.BigDecimal
     * @Date: 2019/11/14
     */
    public static String strRoundByScale(double v, int scale) {
        if (scale < 0) {
            return "";
        }
        if (scale == 0) {
            return new DecimalFormat("0").format(v);
        }
        String formatStr = "0.";
        for (int i = 0; i < scale; i++) {
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }

    /**
     * @Description: roundByScale
     * @Param: [需处理的小数, 保留位数]
     * @return: java.math.BigDecimal
     * @Date: 2019/11/14
     */
    public static BigDecimal roundByScale(BigDecimal decimal, int scale) {
        if (scale < 0) {
            return BigDecimal.ZERO;
        }
        Double v = null == decimal ? 0.00 : decimal.doubleValue();
        if (scale == 0) {
            String format = new DecimalFormat("0").format(v);
            return new BigDecimal(format);
        }
        String formatStr = "0.";
        for (int i = 0; i < scale; i++) {
            formatStr = formatStr + "0";
        }
        String format = new DecimalFormat(formatStr).format(v);
        return new BigDecimal(format);
    }

    /**
     * @Description: roundByScale
     * @Param: [需处理的小数, 保留位数]
     * @return: java.math.BigDecimal
     * @Date: 2019/11/14
     */
    public static String strRoundByScale(BigDecimal decimal, int scale) {
        if (scale < 0) {
            return "";
        }
        Double v = null == decimal ? 0.00 : decimal.doubleValue();
        if (scale == 0) {
            return new DecimalFormat("0").format(v);
        }
        String formatStr = "0.";
        for (int i = 0; i < scale; i++) {
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }

    /**
     * 整数去0
     */
    public static String deleteZero(String str) {
        if (null != str) {
            if (str.indexOf(".") > 0) {
                str = str.replaceAll("0+?$", "");//去掉多余的0
                str = str.replaceAll("[.]$", "");//如最后一位是.则去掉
                return str;
            }
        }
        return str;
    }

}
