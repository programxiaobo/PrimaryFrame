package com.example.hbprolibrary.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 作者：
 * 邮箱：
 */
public class StringUtil {

    /**
     * 判断字符串 是否为空
     *
     * @param text
     * @return
     */
    public static boolean isEmpty(String text) {
        if ("null".equalsIgnoreCase(text) || "".equals(text) || TextUtils.isEmpty(text)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 卡号 4 位 添加 空格
     *
     * @param cardNumber
     * @return
     */
    public static String addBlankToCard(String cardNumber) {
        return cardNumber.replaceAll(".{4}(?!$)", "$0  ");
    }


    /**
     * 判断输入的手机号码是否合法
     * @param telNumber
     * @return
     */
    public static boolean isMobileNumber(String telNumber){
        Pattern p = Pattern.compile("^((13[0-9])|(14[5,7])|(17[6-8])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(telNumber);
        return m.matches();
    }

    /**
     * 判断输入的身份证是否合法
     * @param number
     * @return
     */
    public static boolean isSFZNumber(String number){
        Pattern p = Pattern.compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$" +
                "|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    /**
     * 验证银行卡卡号
     *
     * @param bankCard
     * @return
     */
    public static boolean isCheckBankCard(String bankCard) {
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }


    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeBankCard
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0 || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

}
