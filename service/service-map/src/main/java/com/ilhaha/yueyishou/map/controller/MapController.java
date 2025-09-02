package com.ilhaha.yueyishou.map.controller;

import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.map.service.MapService;
import com.ilhaha.yueyishou.model.form.map.CalculateLineForm;
import com.ilhaha.yueyishou.model.vo.map.DrivingLineVo;
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
    @PostMapping("/calculateLine")
    public Result<DrivingLineVo> calculateDrivingLine(@RequestBody CalculateLineForm calculateLineForm) {
        return Result.ok(mapService.calculateDrivingLine(calculateLineForm));
    }
}
