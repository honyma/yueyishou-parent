package com.ilhaha.yueyishou.recycler.wechat.service;

import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.form.map.CalculateLineForm;
import com.ilhaha.yueyishou.model.vo.map.DrivingLineVo;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateLocationForm;

/**
 * @Author ilhaha
 * @Create 2024/10/13 21:46
 * @Version 1.0
 */
public interface MapService {

    /**
     * 计算回收员到回收点的线路
     *
     * @param calculateLineForm
     * @return
     */
    Result<DrivingLineVo> calculateDrivingLine(CalculateLineForm calculateLineForm);

    /**
     * 上传回收员实时位置
     *
     * @param recyclerUpdateLocationForm
     * @return
     */
    Result<Boolean> updateRecyclerLocation(RecyclerUpdateLocationForm recyclerUpdateLocationForm);

    /**
     * 删除回收员位置信息
     *
     * @return
     */
    Result<Boolean> removeRecyclerLocation();
}
