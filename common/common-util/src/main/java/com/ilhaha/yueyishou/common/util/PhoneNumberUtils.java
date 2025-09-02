package com.ilhaha.yueyishou.common.util;

import java.util.Random;

/**
 * @Author ilhaha
 * @Create 2024/8/18 10:54
 * @Version 1.0
 */
public class PhoneNumberUtils {
    private static final String[] PREFIXES = {
            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", // 可能的运营商前缀
            "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
            "50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
            "60", "61", "62", "63", "64", "65", "66", "67", "68", "69",
            "70", "71", "72", "73", "74", "75", "76", "77", "78", "79",
            "80", "81", "82", "83", "84", "85", "86", "87", "88", "89",
            "90", "91", "92", "93", "94", "95", "96", "97", "98", "99"
    };

    private static final Random RANDOM = new Random();

    /**
     * 生成一个随机的中国大陆手机号
     *
     * @return 随机生成的手机号
     */
    public static String generateRandomPhoneNumber() {
        // 手机号码前缀，以 1 开头，加上一个随机前缀
        String prefix = PREFIXES[RANDOM.nextInt(PREFIXES.length)];
        // 确保总长度为 11 位
        StringBuilder phoneNumber = new StringBuilder("1").append(prefix);

        // 随机生成剩余 9 位数字
        for (int i = 0; i < 9; i++) {
            phoneNumber.append(RANDOM.nextInt(10));
        }

        // 手机号总长度应为 11 位
        if (phoneNumber.length() > 11) {
            return phoneNumber.substring(0, 11);
        } else {
            return phoneNumber.toString();
        }
    }

}
