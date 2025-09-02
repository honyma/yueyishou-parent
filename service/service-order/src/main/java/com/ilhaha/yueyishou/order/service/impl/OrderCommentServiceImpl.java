package com.ilhaha.yueyishou.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilhaha.yueyishou.model.entity.order.OrderComment;
import com.ilhaha.yueyishou.model.form.order.OrderCommentForm;
import com.ilhaha.yueyishou.model.form.order.OrderReviewForm;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateForm;
import com.ilhaha.yueyishou.model.vo.order.OrderCommentVo;
import com.ilhaha.yueyishou.order.mapper.OrderCommentMapper;
import com.ilhaha.yueyishou.order.service.IOrderCommentService;
import com.ilhaha.yueyishou.recycler.client.RecyclerInfoFeignClient;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderCommentServiceImpl extends ServiceImpl<OrderCommentMapper, OrderComment> implements IOrderCommentService {

    @Resource
    private RecyclerInfoFeignClient recyclerInfoFeignClient;

    /**
     * 顾客评论
     *
     * @return
     */
    @Override
    public Boolean review(OrderReviewForm orderReviewForm) {
        OrderComment orderComment = new OrderComment();
        BeanUtils.copyProperties(orderReviewForm, orderComment);
        boolean flag = this.save(orderComment);
        // 评论添加成功，重新计算回收员评分
        if (flag) {
            // 获取该回收员所有的评论信息
            LambdaQueryWrapper<OrderComment> orderCommentLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderCommentLambdaQueryWrapper.eq(OrderComment::getRecyclerId, orderReviewForm.getRecyclerId());
            List<OrderComment> orderCommentListDB = this.list(orderCommentLambdaQueryWrapper);
            // 计算评分平均值
            double averageRating = orderCommentListDB.stream()
                    .mapToInt(OrderComment::getRate)
                    .average()
                    .orElse(0.0);

            // 更新回收员的评分信息
            RecyclerUpdateForm recyclerUpdateForm = new RecyclerUpdateForm();
            recyclerUpdateForm.setRate(BigDecimal.valueOf(averageRating));
            recyclerUpdateForm.setRecyclerId(orderReviewForm.getRecyclerId());
            recyclerInfoFeignClient.updateBaseInfo(recyclerUpdateForm);
        }
        return flag;
    }

    /**
     * 查询订单评论详情
     *
     * @param orderNo
     * @return
     */
    @Override
    public OrderCommentVo getCommentDetail(String orderNo) {
        OrderCommentVo orderCommentVo = new OrderCommentVo();
        OrderComment orderCommentDB = this.getOne(new LambdaQueryWrapper<OrderComment>().eq(OrderComment::getOrderNo, orderNo));
        if (!ObjectUtils.isEmpty(orderCommentDB)) {
            BeanUtils.copyProperties(orderCommentDB, orderCommentVo);
            orderCommentVo.setReviewTime(orderCommentDB.getCreateTime());
        }
        return orderCommentVo;
    }


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 查询评论信息列表
     *
     * @param orderCommentForm
     * @return
     */
    @Override
    public Page<OrderComment> queryPageList(OrderCommentForm orderCommentForm, Integer pageNo, Integer pageSize) {
        LambdaQueryWrapper<OrderComment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(!ObjectUtils.isEmpty(orderCommentForm.getCustomerId()), OrderComment::getCustomerId, orderCommentForm.getCustomerId());
        lambdaQueryWrapper.eq(!ObjectUtils.isEmpty(orderCommentForm.getRecyclerId()), OrderComment::getRecyclerId, orderCommentForm.getRecyclerId());
        lambdaQueryWrapper.eq(!StringUtils.isEmpty(orderCommentForm.getOrderNo()), OrderComment::getOrderNo, orderCommentForm.getOrderNo());
        lambdaQueryWrapper.orderByDesc(OrderComment::getCreateTime);
        //全量查询
        if (pageNo == null || pageSize == null) {
            List<OrderComment> orderCommentList = this.list(lambdaQueryWrapper);
            Page<OrderComment> page = new Page<>(1, orderCommentList.size(), orderCommentList.size());
            return page.setRecords(orderCommentList);
        }
        //分页查询
        Page<OrderComment> page = new Page<>(pageNo, pageSize);

        return this.page(page, lambdaQueryWrapper);
    }
}
