package com.ynzyq.yudbadmin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author niunafei
 * @function
 * @email niunafei0315@163.com
 * @date 2020/6/5  2:19 PM
 */
public class PhoneFormatCheckUtils {
    /**
     * ^ 匹配输入字符串开始的位置
     * \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
     * $ 匹配输入字符串结尾的位置
     */
    private static final Pattern HK_PATTERN = Pattern.compile("^(5|6|8|9)\\d{7}$");
    private static final Pattern CHINA_PATTERN = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");
    private static final Pattern NUM_PATTERN = Pattern.compile("[0-9]+");

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 145,147,149
     * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
     * 166
     * 17+3,5,6,7,8
     * 18+任意数
     * 198,199
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        Matcher m = CHINA_PATTERN.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {

        Matcher m = HK_PATTERN.matcher(str);
        return m.matches();
    }

    /**
     * 判断是否是正整数的方法
     */
    public static boolean isNumeric(String string) {
        return NUM_PATTERN.matcher(string).matches();
    }


    /**
     * 身份证号基本判断
     *
     * @param IDNumber
     * @return
     */
    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]d{5}(18|19|20)d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)d{3}[0-9Xx]$)|" +
                "(^[1-9]d{5}d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)d{3}$)";
        //假设18位身份证号码:41000119910101123X 410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个 4
        //d{5} 五位数字 10001（前六位省市县地区）
        //(18|19|20) 19（现阶段可能取值范围18xx-20xx年）
        //d{2} 91（年份）
        //((0[1-9])|(10|11|12)) 01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //d{3} 三位数字 123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123 410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个 4
        //d{5} 五位数字 10001（前六位省市县地区）
        //d{2} 91（年份）
        //((0[1-9])|(10|11|12)) 01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //d{3} 三位数字 123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾


        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常:" + IDNumber);
                    return false;
                }
            }

        }
        return matches;
    }
}