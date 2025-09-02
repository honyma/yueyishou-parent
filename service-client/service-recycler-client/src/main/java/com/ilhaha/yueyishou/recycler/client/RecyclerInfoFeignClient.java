package com.ilhaha.yueyishou.recycler.client;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.entity.recycler.RecyclerInfo;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateForm;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateStatusForm;
import com.ilhaha.yueyishou.model.vo.recycler.RecyclerBaseInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ilhaha
 * @Create 2024/9/2 22:46
 * @Version 1.0
 */
@FeignClient("service-server")
public interface RecyclerInfoFeignClient {
    /**
     * 获取回收员基本信息
     *
     * @param recyclerId
     * @return
     */
    @GetMapping("/recyclerInfo/base/info/{recyclerId}")
    Result<RecyclerBaseInfoVo> getBaseInfo(@PathVariable("recyclerId") Long recyclerId);

    /**
     * 修改回收员评分
     * @param recyclerUpdateForm
     * @return
     */
    @PostMapping("/recyclerInfo/update/rate")
    Result<Boolean> updateBaseInfo(@RequestBody RecyclerUpdateForm recyclerUpdateForm);


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 回收员分页列表查询
     *
     * @param recyclerInfo
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/recyclerInfo/list")
    Result<Page<RecyclerInfo>> queryPageList(@RequestBody RecyclerInfo recyclerInfo,
                                             @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                             @RequestParam(name = "pageSize", required = false) Integer pageSize);

    /**
     * 添加回收员信息
     *
     * @param recyclerInfo
     * @return
     */
    @PostMapping(value = "/recyclerInfo/add")
    Result<RecyclerInfo> add(@RequestBody RecyclerInfo recyclerInfo);

    /**
     * 编辑
     *
     * @param recyclerInfo
     * @return
     */
    @PostMapping("/recyclerInfo/edit")
    Result<String> edit(@RequestBody RecyclerInfo recyclerInfo);

    /**
     * 回收员状态切换
     *
     * @param recyclerUpdateStatusForm
     * @return
     */
    @PostMapping("/recyclerInfo/switch/status")
    Result<String> switchStatus(@RequestBody RecyclerUpdateStatusForm recyclerUpdateStatusForm);
}
