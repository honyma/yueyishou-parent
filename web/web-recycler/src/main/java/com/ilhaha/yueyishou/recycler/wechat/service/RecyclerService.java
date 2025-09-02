package com.ilhaha.yueyishou.recycler.wechat.service;

import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.vo.recycler.RecyclerBaseInfoVo;

/**
 * @Author ilhaha
 * @Create 2024/9/8 17:20
 * @Version 1.0
 */
public interface RecyclerService {

    /**
     * 获取回收员基本信息
     *
     * @return
     */
    Result<RecyclerBaseInfoVo> getRecyclerBaseInfo();
}
