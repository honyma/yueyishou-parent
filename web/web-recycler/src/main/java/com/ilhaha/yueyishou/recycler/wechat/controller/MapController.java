package com.ilhaha.yueyishou.recycler.wechat.controller;

import com.ilhaha.yueyishou.common.anno.WechatLoginVerification;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.form.map.CalculateLineForm;
import com.ilhaha.yueyishou.model.vo.map.DrivingLineVo;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateLocationForm;
import com.ilhaha.yueyishou.recycler.wechat.service.MapService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ilhaha
 * @Create 2024/10/13 20:43
 * @Version 1.0
 */
@RestController
@RequestMapping("/map")
public class MapController {

    @Resource
    private MapService mapService;

    /**
     * 计算回收员到回收点的线路
     *
     * @param calculateLineForm
     * @return
     */
    @WechatLoginVerification
    @PostMapping("/calculateLine")
    public Result<DrivingLineVo> calculateDrivingLine(@RequestBody CalculateLineForm calculateLineForm) {
        return mapService.calculateDrivingLine(calculateLineForm);
    }

    /**
     * 删除回收员位置信息
     *
     * @return
     */
    @WechatLoginVerification
    @PostMapping("/removeLocation")
    public Result<Boolean> removeLocation() {
        return mapService.removeRecyclerLocation();
    }

    /**
     * 上传回收员实时位置
     *
     * @param recyclerUpdateLocationForm
     * @return
     */
    @WechatLoginVerification
    @PostMapping("/updateLocation")
    public Result<Boolean> updateLocation(@RequestBody RecyclerUpdateLocationForm recyclerUpdateLocationForm) {
        return mapService.updateRecyclerLocation(recyclerUpdateLocationForm);
    }
}
