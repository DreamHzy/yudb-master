package com.yunzyq.yudbapp.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 平台code生成标识
 */
public class PlatformCodeUtil {

    /**
     * 根据传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param sformat yyyyMMddhhmmss
     * @return
     */
    public static String getDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getRandomNum(int num) {
        String numStr = "";
        for (int i = 0; i < num; i++) {
            numStr += (int) (10 * (Math.random()));
        }
        return numStr;
    }

    /**
     * 生成id
     *
     * @return
     */
    public static String getGeneratID(String str) {
        String sformat = "MMddhhmmssSSS";
        int num = 5;
        String idStr = getDate(sformat) + getRandomNum(num);
        Long id = Long.valueOf(idStr);
        return str + id;
    }

    public static void main(String[] args) {
    }


    /**
     * 平台商品编号 优惠券YH 抽奖位置LD
     *
     * @return
     */
    public static String gs(String msg) {
        return msg + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 28);
    }

    /**
     * 订单号生成 系统订单
     *
     * @param str
     * @return
     */
    public static String orderNo(String str) {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String seconds = new SimpleDateFormat("HHmmss").format(new Date());
        return str + date + "0010" + getTwo() + "00" + seconds + getTwo();
    }

    /**
     * 订单号生成 系统订单
     *
     * @param str
     * @return
     */
    public static String newOrderNo(String str) {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String seconds = new SimpleDateFormat("HHmmss").format(new Date());
        return str + date + getTwo() + seconds + getTwo();
    }

    public static String getTwo() {
        Random rad = new Random();

        String result = rad.nextInt(100) + "";

        if (result.length() == 1) {
            result = "0" + result;
        }
        return result;
    }


    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};


    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    /**
     * 获取加密用的key
     *
     * @return
     */
    public static String getPsdCode() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 6; i++) {
            result += random.nextInt(10);
        }
        return result;
    }

    /**
     * 手续费计算
     */
    public static BigDecimal rate(BigDecimal money, BigDecimal rate) {
        BigDecimal reault = money.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
        return reault;
    }
}