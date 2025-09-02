package com.ilhaha.yueyishou.model.vo.map;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author ilhaha
 * @Create 2024/10/13 21:31
 * @Version 1.0
 */
@Data
public class DrivingLineVo {

    /**
     * 回收员与回收点两点之间的距离，单位：千米
     */
    private BigDecimal distance;

    /**
     * 回收员去回收点所需时间，单位：分钟
     */
    private BigDecimal duration;

    /**
     * 路线坐标点串
     */
    private JSONArray polyline;
    /**
     * 回收点地点经度
     */
    private BigDecimal endPointLongitude;

    /**
     * 回收点点纬度
     */
    private BigDecimal endPointLatitude;

    /**
     * 回收员地点经度
     */
    private BigDecimal recyclerPointLongitude;
    /**
     * 回收员地点纬度
     */
    private BigDecimal recyclerPointLatitude;
}
