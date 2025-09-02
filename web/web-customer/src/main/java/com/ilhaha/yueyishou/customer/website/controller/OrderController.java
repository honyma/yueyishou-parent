package com.ilhaha.yueyishou.customer.website.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.anno.WebsiteLoginVerification;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.customer.website.service.OrderService;
import com.ilhaha.yueyishou.model.form.order.OrderMgrQueryForm;
import com.ilhaha.yueyishou.model.vo.order.OrderMgrQueryVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ilhaha
 * @Create 2024/9/28 20:28
 * @Version 1.0
 */
@RestController(value = "cWebsiteOrderController")
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 订单列表查询
     *
     * @return
     */
    @WebsiteLoginVerification
    @PostMapping("/list")
    public Result<Page<OrderMgrQueryVo>> queryPageList(@RequestBody OrderMgrQueryForm orderMgrQueryForm,
                                                       @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                                       @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        return orderService.queryPageList(orderMgrQueryForm, pageNo, pageSize);
    }
}
