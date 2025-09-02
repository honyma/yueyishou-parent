package com.ilhaha.yueyishou.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IDUtil {

    // 定义前缀
    private static final String PREFIX = "RE";

    // 定义随机数生成器
    private static final Random RANDOM = new Random();

    /**
     * 生成唯一的工号
     *
     * @return 工号
     */
    public static String generateRecyclerID() {
        // 获取当前时间的时间戳
        String timestamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 生成随机数（3位）
        int randomNum = RANDOM.nextInt(900) + 100; // 100到999的随机数
        // 拼接工号
        return PREFIX + timestamp + randomNum;
    }

    public static String generateOrderNumber() {
        // 获取当前时间
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        // 生成一个随机数
        int randomNum = new Random().nextInt(100000); // 生成0到99999的随机数
        // 格式化为三位数
        String formattedRandomNum = String.format("%03d", randomNum);
        // 组合生成订单号
        return timestamp + formattedRandomNum;
    }
}
