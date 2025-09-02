package com.ilhaha.yueyishou.server;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.form.order.OrderMgrQueryForm;
import com.ilhaha.yueyishou.model.vo.order.OrderMgrQueryVo;
import com.ilhaha.yueyishou.recycler.wechat.service.OrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author mazehong
 * @since 2022/5/6
 */
@SpringBootTest(classes = WebServerApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@ActiveProfiles({"dev"})
public class BaseTest {

    @Resource
    private OrderService orderService;

    @Test
    public void testQueryVaRepayResult() {
        OrderMgrQueryForm orderMgrQueryForm = new OrderMgrQueryForm();
        Result<Page<OrderMgrQueryVo>> result = orderService.queryPageList(orderMgrQueryForm, null, null);
        log.info("输出结果：{}", JSONObject.toJSONString(result));
    }
}
