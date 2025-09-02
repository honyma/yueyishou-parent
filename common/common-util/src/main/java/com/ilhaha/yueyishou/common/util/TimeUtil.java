package com.ilhaha.yueyishou.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author mazehong
 * @date 2025/8/8
 */
public class TimeUtil {

    /**
     * 将 Date 转换为 LocalDate
     */
    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 将 Date 转换为 LocalDateTime
     */
    public static LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * 计算两个 Date 类型时间的分钟差
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 两个时间之间的分钟差(开始时间 > 结束时间 ： 返回负数)
     */
    public static long calculateMinutesDifference(Date startDate, Date endDate) {
        Instant startInstant = startDate.toInstant();
        Instant endInstant = endDate.toInstant();
        return ChronoUnit.MINUTES.between(startInstant, endInstant);
    }

    /**
     * 计算回收员是否超时，并返回超时分钟数
     *
     * @param appointmentTime 预约时间，格式为 yyyy-MM-dd HH:mm:ss
     * @param arriveTime      到达时间，格式为 yyyy-MM-dd HH:mm:ss
     * @return 超时的分钟数，未超时返回 0
     */
    private static int calculateTimeoutMinutes(Date appointmentTime, Date arriveTime) {
        // 获取两个时间的毫秒值
        long appointmentMillis = appointmentTime.getTime();
        long arriveMillis = arriveTime.getTime();

        // 计算时间差（毫秒级）
        long differenceInMillis = arriveMillis - appointmentMillis;

        // 如果到达时间早于或等于预约时间，则未超时，返回 0
        if (differenceInMillis <= 0) {
            return 0;
        }

        // 将毫秒差转换为分钟
        return (int) (differenceInMillis / (1000 * 60));
    }
}
