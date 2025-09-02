package com.ilhaha.yueyishou.recycler.wechat.controller;

import com.ilhaha.yueyishou.common.anno.WechatLoginVerification;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.vo.recycler.RecyclerBaseInfoVo;
import com.ilhaha.yueyishou.recycler.wechat.service.RecyclerService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ilhaha
 * @Create 2024/9/8 17:18
 * @Version 1.0
 */
@RestController
@RequestMapping("/recycler")
public class RecyclerController {

    @Resource
    private RecyclerService recyclerService;

    /**
     * 获取回收员基本信息
     *
     * @param
     * @return
     */
    @WechatLoginVerification
    @GetMapping("/base/info")
    public Result<RecyclerBaseInfoVo> getBaseInfo() {
        return recyclerService.getRecyclerBaseInfo();
    }
}
