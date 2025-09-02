package com.ilhaha.yueyishou.recycler.wechat.service.impl;

import com.ilhaha.yueyishou.client.LocationFeignClient;
import com.ilhaha.yueyishou.client.MapFeignClient;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.common.util.AuthContextHolder;
import com.ilhaha.yueyishou.model.form.map.CalculateLineForm;
import com.ilhaha.yueyishou.model.vo.map.DrivingLineVo;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateLocationForm;
import com.ilhaha.yueyishou.recycler.wechat.service.MapService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author ilhaha
 * @Create 2024/10/13 21:46
 * @Version 1.0
 */
@Service
public class MapServiceImpl implements MapService {

    @Resource
    private MapFeignClient mapFeignClient;

    @Resource
    private LocationFeignClient locationFeignClient;

    /**
     * 计算回收员到回收点的线路
     *
     * @param calculateLineForm
     * @return
     */
    @Override
    public Result<DrivingLineVo> calculateDrivingLine(CalculateLineForm calculateLineForm) {
        return mapFeignClient.calculateDrivingLine(calculateLineForm);
    }

    /**
     * 上传回收员实时位置
     *
     * @param recyclerUpdateLocationForm
     * @return
     */
    @Override
    public Result<Boolean> updateRecyclerLocation(RecyclerUpdateLocationForm recyclerUpdateLocationForm) {
        recyclerUpdateLocationForm.setRecyclerId(AuthContextHolder.getRecyclerId());
        return locationFeignClient.updateRecyclerLocation(recyclerUpdateLocationForm);
    }

    /**
     * 删除回收员位置信息
     *
     * @return
     */
    @Override
    public Result<Boolean> removeRecyclerLocation() {
        return locationFeignClient.removeDriverLocation(AuthContextHolder.getRecyclerId());
    }
}
