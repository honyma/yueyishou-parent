package com.ilhaha.yueyishou.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ilhaha.yueyishou.common.execption.YueYiShouException;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.common.result.ResultCodeEnum;
import com.ilhaha.yueyishou.model.entity.order.OrderComment;
import com.ilhaha.yueyishou.model.form.order.OrderCommentForm;
import com.ilhaha.yueyishou.model.form.order.OrderReviewForm;
import com.ilhaha.yueyishou.model.vo.order.OrderCommentVo;
import com.ilhaha.yueyishou.order.service.IOrderCommentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderComment")
@Slf4j
public class OrderCommentController {

    @Resource
    private IOrderCommentService orderCommentService;

    /**
     * 顾客评论
     *
     * @return
     */
    @PostMapping("/review")
    public Result<Boolean> review(@RequestBody OrderReviewForm orderReviewForm) {
        return Result.ok(orderCommentService.review(orderReviewForm));
    }

    /**
     * 查询订单评价信息
     *
     * @param orderNo
     * @return
     */
    @GetMapping("/details/{orderNo}")
    public Result<OrderCommentVo> getCommentDetail(@PathVariable("orderNo") String orderNo) {
        OrderCommentVo orderCommentVo = orderCommentService.getCommentDetail(orderNo);
        if (orderCommentVo == null) {
            throw new YueYiShouException(ResultCodeEnum.DATA_ERROR);
        }
        return Result.ok(orderCommentVo);
    }


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 分页列表查询
     *
     * @param orderCommentForm
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<OrderComment>> queryPageList(@RequestBody OrderCommentForm orderCommentForm,
                                                     @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                                     @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        return Result.ok(orderCommentService.queryPageList(orderCommentForm, pageNo, pageSize));
    }
}
