package com.ilhaha.yueyishou.recycler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilhaha.yueyishou.model.entity.recycler.RecyclerInfo;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerQueryForm;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateForm;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateStatusForm;
import com.ilhaha.yueyishou.model.vo.recycler.RecyclerBaseInfoVo;
import com.ilhaha.yueyishou.recycler.mapper.RecyclerInfoMapper;
import com.ilhaha.yueyishou.recycler.service.IRecyclerInfoService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class RecyclerInfoServiceImpl extends ServiceImpl<RecyclerInfoMapper, RecyclerInfo> implements IRecyclerInfoService {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 获取回收员基本信息
     *
     * @param recyclerId
     * @return
     */
    @Override
    public RecyclerBaseInfoVo getBaseInfo(Long recyclerId) {
        //查询回收员信息
        RecyclerBaseInfoVo recyclerBaseInfoVo = new RecyclerBaseInfoVo();
        RecyclerInfo recyclerInfoDB = this.getById(recyclerId);
        BeanUtils.copyProperties(recyclerInfoDB, recyclerBaseInfoVo);

        return recyclerBaseInfoVo;
    }

    /**
     * 修改回收员评分
     *
     * @param recyclerUpdateForm
     * @return
     */
    @Override
    public Boolean updateBaseInfo(RecyclerUpdateForm recyclerUpdateForm) {
        RecyclerInfo recyclerInfo = new RecyclerInfo();
        recyclerInfo.setId(recyclerUpdateForm.getRecyclerId());
        recyclerInfo.setScore(recyclerUpdateForm.getRate());
        return this.updateById(recyclerInfo);
    }


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 回收员分页查询
     *
     * @param recyclerQueryForm
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<RecyclerInfo> queryPageList(RecyclerQueryForm recyclerQueryForm, Integer pageNo, Integer pageSize) {
        LambdaQueryWrapper<RecyclerInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(!ObjectUtils.isEmpty(recyclerQueryForm.getStatus()), RecyclerInfo::getStatus, recyclerQueryForm.getStatus());
        lambdaQueryWrapper.like(StringUtils.hasText(recyclerQueryForm.getRecyclerNo()), RecyclerInfo::getRecyclerNo, recyclerQueryForm.getRecyclerNo());
        lambdaQueryWrapper.like(StringUtils.hasText(recyclerQueryForm.getName()), RecyclerInfo::getName, recyclerQueryForm.getName());
        lambdaQueryWrapper.like(StringUtils.hasText(recyclerQueryForm.getPhone()), RecyclerInfo::getPhone, recyclerQueryForm.getPhone());
        lambdaQueryWrapper.like(StringUtils.hasText(recyclerQueryForm.getIdcardNo()), RecyclerInfo::getIdcardNo, recyclerQueryForm.getIdcardNo());
        lambdaQueryWrapper.orderByDesc(RecyclerInfo::getCreateTime);
        //全量查询
        if (pageNo == null || pageSize == null) {
            List<RecyclerInfo> recyclerInfoList = this.list(lambdaQueryWrapper);
            Page<RecyclerInfo> page = new Page<>(1, recyclerInfoList.size(), recyclerInfoList.size());
            return page.setRecords(recyclerInfoList);
        }
        //分页查询
        Page<RecyclerInfo> page = new Page<>(pageNo, pageSize);

        return this.page(page, lambdaQueryWrapper);
    }

    /**
     * 回收员状态切换
     *
     * @param recyclerUpdateStatusForm
     * @return
     */
    @Override
    public String switchStatus(RecyclerUpdateStatusForm recyclerUpdateStatusForm) {
        LambdaUpdateWrapper<RecyclerInfo> recyclerInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        recyclerInfoLambdaUpdateWrapper.set(RecyclerInfo::getStatus, recyclerUpdateStatusForm.getStatus())
                .in(RecyclerInfo::getId, recyclerUpdateStatusForm.getRecyclerIds());
        this.update(recyclerInfoLambdaUpdateWrapper);
        return "修改成功";
    }
}
