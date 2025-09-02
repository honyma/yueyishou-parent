package com.ilhaha.yueyishou.recycler.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ilhaha.yueyishou.model.entity.recycler.RecyclerInfo;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerQueryForm;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateForm;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateStatusForm;
import com.ilhaha.yueyishou.model.vo.recycler.RecyclerBaseInfoVo;

public interface IRecyclerInfoService extends IService<RecyclerInfo> {

    /**
     * 获取回收员基本信息
     *
     * @param recyclerId
     * @return
     */
    RecyclerBaseInfoVo getBaseInfo(Long recyclerId);

    /**
     * 修改回收员评分
     *
     * @param recyclerUpdateForm
     * @return
     */
    Boolean updateBaseInfo(RecyclerUpdateForm recyclerUpdateForm);


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 回收员分页查询
     *
     * @param recyclerQueryForm
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<RecyclerInfo> queryPageList(RecyclerQueryForm recyclerQueryForm, Integer pageNo, Integer pageSize);

    /**
     * 回收员状态切换
     *
     * @param recyclerUpdateStatusForm
     * @return
     */
    String switchStatus(RecyclerUpdateStatusForm recyclerUpdateStatusForm);
}
