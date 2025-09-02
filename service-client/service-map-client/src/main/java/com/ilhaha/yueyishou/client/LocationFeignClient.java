package com.ilhaha.yueyishou.client;

import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateLocationForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author ilhaha
 * @Create 2024/10/11 19:51
 * @Version 1.0
 */
@FeignClient("service-server")
public interface LocationFeignClient {

    /**
     * 删除回收员位置信息
     *
     * @param recyclerId
     * @return
     */
    @PostMapping("/map/location/removeRecyclerLocation/{recyclerId}")
    Result<Boolean> removeDriverLocation(@PathVariable("recyclerId") Long recyclerId);

    /**
     * 上传回收员实时位置
     *
     * @param recyclerUpdateLocationForm
     * @return
     */
    @PostMapping("/map/location/updateRecyclerLocation")
    Result<Boolean> updateRecyclerLocation(@RequestBody RecyclerUpdateLocationForm recyclerUpdateLocationForm);


}
