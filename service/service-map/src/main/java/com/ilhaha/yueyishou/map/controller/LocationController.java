package com.ilhaha.yueyishou.map.controller;

import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.map.service.LocationService;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateLocationForm;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ilhaha
 * @Create 2024/10/11 19:46
 * @Version 1.0
 */
@RestController
@RequestMapping("/map/location")
public class LocationController {

    @Resource
    private LocationService locationService;

    /**
     * 删除回收员位置信息
     *
     * @param recyclerId
     * @return
     */
    @PostMapping("/removeRecyclerLocation/{recyclerId}")
    public Result<Boolean> removeDriverLocation(@PathVariable("recyclerId") Long recyclerId) {
        return Result.ok(locationService.removeRecyclerLocation(recyclerId));
    }

    /**
     * 上传回收员实时位置
     *
     * @param recyclerUpdateLocationForm
     * @return
     */
    @PostMapping("/updateRecyclerLocation")
    public Result<Boolean> updateRecyclerLocation(@RequestBody RecyclerUpdateLocationForm recyclerUpdateLocationForm) {
        return Result.ok(locationService.updateRecyclerLocation(recyclerUpdateLocationForm));
    }

}
