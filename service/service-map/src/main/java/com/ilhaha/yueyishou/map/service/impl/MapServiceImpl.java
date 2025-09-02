package com.ilhaha.yueyishou.map.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ilhaha.yueyishou.common.constant.RedisConstant;
import com.ilhaha.yueyishou.common.execption.YueYiShouException;
import com.ilhaha.yueyishou.common.result.ResultCodeEnum;
import com.ilhaha.yueyishou.map.service.MapService;
import com.ilhaha.yueyishou.model.form.map.CalculateLineForm;
import com.ilhaha.yueyishou.model.vo.map.DrivingLineVo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/10/13 21:27
 * @Version 1.0
 */
@Service
public class MapServiceImpl implements MapService {

    @Value("${tencent.map.key}")
    private String key;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 计算回收员到回收点的线路
     *
     * @param calculateLineForm
     * @return
     */
    @Override
    public DrivingLineVo calculateDrivingLine(CalculateLineForm calculateLineForm) {
        // 获取回收员的地理位置
        Point recyclerLocationFromRedis = getRecyclerLocationFromRedis(calculateLineForm.getRecyclerId());
        // 回收员停止接单
        if (ObjectUtils.isEmpty(recyclerLocationFromRedis)) {
            return null;
        }
        // 定义腾讯地图访问地址
        String url = "https://apis.map.qq.com/ws/direction/v1/driving/?from={from}&to={to}&key={key}";
        // 定义请求参数
        HashMap<String, String> map = new HashMap<>();

        map.put("from", recyclerLocationFromRedis.getY() + "," + recyclerLocationFromRedis.getX());
        map.put("to", calculateLineForm.getEndPointLatitude() + "," + calculateLineForm.getEndPointLongitude());
        map.put("key", key);
        // 远程调用
        JSONObject result = restTemplate.getForObject(url, JSONObject.class, map);
        System.out.println(result);
        // 处理返回结果
        Integer status = result.getInteger("status");
        if (status != 0) {
            throw new YueYiShouException(ResultCodeEnum.MAP_FAIL);
        }
        JSONObject route = result.getJSONObject("result").getJSONArray("routes").getJSONObject(0);
        DrivingLineVo drivingLineVo = new DrivingLineVo();
        drivingLineVo.setDistance(route.getBigDecimal("distance").divide(new BigDecimal(1000)).setScale(2, RoundingMode.HALF_UP));
        drivingLineVo.setDuration(route.getBigDecimal("duration"));
        drivingLineVo.setPolyline(route.getJSONArray("polyline"));

        // 订单位置
        drivingLineVo.setEndPointLatitude(calculateLineForm.getEndPointLatitude());
        drivingLineVo.setEndPointLongitude(calculateLineForm.getEndPointLongitude());
        // 回收员位置
        drivingLineVo.setRecyclerPointLatitude(BigDecimal.valueOf(recyclerLocationFromRedis.getY()));
        drivingLineVo.setRecyclerPointLongitude(BigDecimal.valueOf(recyclerLocationFromRedis.getX()));
        return drivingLineVo;
    }


    /**
     * 从 Redis 获取回收员的实时位置
     *
     * @param recyclerId 回收员ID
     * @return 回收员的地理位置 (Point)
     */
    private Point getRecyclerLocationFromRedis(Long recyclerId) {
        List<Point> recyclerLocations = redisTemplate.opsForGeo()
                .position(RedisConstant.RECYCLER_GEO_LOCATION, recyclerId.toString());
        if (recyclerLocations != null && !recyclerLocations.isEmpty()) {
            Point location = recyclerLocations.get(0);  // 获取第一个位置点
            if (ObjectUtils.isEmpty(location)) {
                return null;
            }
            return new Point(location.getX(), location.getY());  // 返回经纬度
        }
        return null;  // 如果没有位置数据，则返回空
    }
}
