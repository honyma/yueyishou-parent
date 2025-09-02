package com.ilhaha.yueyishou.client;

import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.form.map.CalculateLineForm;
import com.ilhaha.yueyishou.model.vo.map.DrivingLineVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author ilhaha
 * @Create 2024/10/13 21:47
 * @Version 1.0
 */
@FeignClient("service-server")
public interface MapFeignClient {

    /**
     * 计算回收员到回收点的线路
     *
     * @param calculateLineForm
     * @return
     */
    @PostMapping("/map/calculateLine")
    Result<DrivingLineVo> calculateDrivingLine(@RequestBody CalculateLineForm calculateLineForm);
}
