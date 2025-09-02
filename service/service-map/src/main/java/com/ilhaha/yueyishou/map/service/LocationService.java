package com.ilhaha.yueyishou.map.service;

import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateLocationForm;

/**
 * @Author ilhaha
 * @Create 2024/10/11 19:48
 * @Version 1.0
 */
public interface LocationService {

    /**
     * 上传回收员实时位置
     *
     * @param recyclerUpdateLocationForm
     * @return
     */
    Boolean updateRecyclerLocation(RecyclerUpdateLocationForm recyclerUpdateLocationForm);

    /**
     * 删除回收员位置信息
     *
     * @param recyclerId
     * @return
     */
    Boolean removeRecyclerLocation(Long recyclerId);
}
