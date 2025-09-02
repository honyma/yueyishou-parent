package com.ilhaha.yueyishou.recycler.wechat.service.impl;

import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.common.util.AuthContextHolder;
import com.ilhaha.yueyishou.model.vo.recycler.RecyclerBaseInfoVo;
import com.ilhaha.yueyishou.recycler.client.RecyclerInfoFeignClient;
import com.ilhaha.yueyishou.recycler.wechat.service.RecyclerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author ilhaha
 * @Create 2024/9/8 17:20
 * @Version 1.0
 */
@Service
public class RecyclerServiceImpl implements RecyclerService {

    @Resource
    private RecyclerInfoFeignClient recyclerInfoFeignClient;

    /**
     * 获取回收员基本信息
     *
     * @return
     */
    @Override
    public Result<RecyclerBaseInfoVo> getRecyclerBaseInfo() {
        return recyclerInfoFeignClient.getBaseInfo(AuthContextHolder.getRecyclerId());
    }
}
