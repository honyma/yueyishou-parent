package com.ilhaha.yueyishou.order.service.impl;

import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilhaha.yueyishou.common.constant.CategoryConstant;
import com.ilhaha.yueyishou.common.constant.CustomerConstant;
import com.ilhaha.yueyishou.common.constant.RecyclerConstant;
import com.ilhaha.yueyishou.common.constant.RedisConstant;
import com.ilhaha.yueyishou.common.execption.YueYiShouException;
import com.ilhaha.yueyishou.common.result.ResultCodeEnum;
import com.ilhaha.yueyishou.common.util.IDUtil;
import com.ilhaha.yueyishou.common.util.LocationUtil;
import com.ilhaha.yueyishou.customer.client.CustomerAccountFeignClient;
import com.ilhaha.yueyishou.model.entity.order.OrderDetail;
import com.ilhaha.yueyishou.model.entity.order.OrderInfo;
import com.ilhaha.yueyishou.model.enums.BonusDetailType;
import com.ilhaha.yueyishou.model.enums.OrderStatus;
import com.ilhaha.yueyishou.model.enums.UnitName;
import com.ilhaha.yueyishou.model.form.customer.CustomerSettlementForm;
import com.ilhaha.yueyishou.model.form.order.*;
import com.ilhaha.yueyishou.model.vo.order.OrderMatchingVo;
import com.ilhaha.yueyishou.model.vo.order.OrderDetailsVo;
import com.ilhaha.yueyishou.model.vo.order.OrderMgrQueryVo;
import com.ilhaha.yueyishou.model.vo.order.OrderMyVo;
import com.ilhaha.yueyishou.order.mapper.OrderInfoMapper;
import com.ilhaha.yueyishou.order.service.IOrderDetailService;
import com.ilhaha.yueyishou.order.service.IOrderInfoService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private CustomerAccountFeignClient customerAccountFeignClient;

    @Resource
    @Lazy
    private IOrderDetailService orderDetailService;

    /**
     * 回收员到达回收点开始服务订单
     *
     * @param orderNo
     * @return
     */
    @Override
    public Boolean arrive(String orderNo) {
        // 查询订单信息
        LambdaQueryWrapper<OrderInfo> orderInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderInfoLambdaQueryWrapper.eq(OrderInfo::getOrderNo, orderNo);
        OrderInfo orderInfoDB = this.getOne(orderInfoLambdaQueryWrapper);
        if (orderInfoDB == null) {
            throw new YueYiShouException(ResultCodeEnum.ARGUMENT_VALID_ERROR);
        }
        //判断不能超过100米就点击已到达
        Double distance = getDistanceLocation(orderInfoDB);
        if (distance != null) {
            BigDecimal compareDistance = BigDecimal.valueOf(distance).setScale(1, RoundingMode.CEILING);
            if (compareDistance.compareTo(RecyclerConstant.MIN_CUSTOMER_DISTANCE) > 0) {
                throw new YueYiShouException(ResultCodeEnum.NOT_REACH_SERVICE_AREA);
            }
        }
        LambdaUpdateWrapper<OrderInfo> orderInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        orderInfoLambdaUpdateWrapper.set(OrderInfo::getStatus, OrderStatus.SERVICE_PROCESSING)
                .set(OrderInfo::getArriveTime, new Date())
                .set(OrderInfo::getUpdateTime, new Date())
                .eq(OrderInfo::getOrderNo, orderNo);
        return this.update(orderInfoLambdaUpdateWrapper);
    }

    /**
     * 回收员获取符合接单的订单
     *
     * @param orderMatchingForm
     * @return
     */
    @Override
    public List<OrderMatchingVo> retrieveMatchingOrders(OrderMatchingForm orderMatchingForm) {
        ArrayList<OrderMatchingVo> result = new ArrayList<>();
        // 查询redis所有的待接单的订单信息
        Set keys = redisTemplate.keys(RedisConstant.SELECT_WAITING_ORDER);

        // 使用 multiGet 批量获取这些键对应的 OrderInfo 对象
        List<OrderInfo> waitOrderInfoList = redisTemplate.opsForValue().multiGet(keys);

        // 过滤掉是自己的订单，自己不能接自己的订单
        List<OrderInfo> filteredOrders = waitOrderInfoList.stream()
                .filter(order -> !orderMatchingForm.getCustomerId().equals(order.getCustomerId()))
                .collect(Collectors.toList());

        // 过滤出未过预约时间的订单
        List<OrderInfo> validOrders = filterValidOrdersByAppointmentTime(filteredOrders);

        // 过滤出符合接单里程的订单并将距离保存到 MatchingOrderVo 的 apart 字段
        filterOrdersByDistance(validOrders, getRecyclerLocationFromRedis(orderMatchingForm.getRecyclerId()), orderMatchingForm.getAcceptDistance(), result);

        // 返回结果
        return result;
    }

    /**
     * 过滤出未过预约时间的订单
     *
     * @param orders 原始订单列表
     * @return 未过预约时间的订单列表
     */
    private List<OrderInfo> filterValidOrdersByAppointmentTime(List<OrderInfo> orders) {
        Date now = new Date(); // 获取当前时间
        return orders.stream()
                .filter(order -> order.getAppointmentTime().after(now))  // 过滤掉预约时间已过的订单
                .collect(Collectors.toList());  // 返回过滤后的订单列表
    }


    /**
     * 从 Redis 获取回收员的实时位置
     *
     * @param recyclerId 回收员ID
     * @return 回收员的地理位置 (Point)
     */
    private Point getRecyclerLocationFromRedis(Long recyclerId) {
        List<Point> recyclerLocations = redisTemplate.opsForGeo()
                .position(RedisConstant.RECYCLER_GEO_LOCATION, recyclerId.toString());

        if (recyclerLocations != null && !recyclerLocations.isEmpty()) {
            Point location = recyclerLocations.get(0);  // 获取第一个位置点
            if (ObjectUtils.isEmpty(location)) {
                return null;
            }
            return new Point(location.getX(), location.getY());  // 返回经纬度
        }

        return null;  // 如果没有位置数据，则返回空
    }

    /**
     * 过滤出符合接单里程的订单，并将结果添加到 result 中
     *
     * @param orders           订单列表
     * @param recyclerLocation 回收员当前的地理位置
     * @param acceptDistance   回收员能接受的最大里程
     * @param result           用于保存最终的 MatchingOrderVo 结果
     */
    private void filterOrdersByDistance(List<OrderInfo> orders, Point recyclerLocation, BigDecimal acceptDistance, List<OrderMatchingVo> result) {
        if (ObjectUtils.isEmpty(recyclerLocation)) {
            return;
        }
        orders.stream()
                .filter(order -> {
                    // 计算订单的客户位置与回收员位置之间的距离
                    Point customerLocation = new Point(order.getCustomerPointLongitude().doubleValue(), order.getCustomerPointLatitude().doubleValue());
                    double distance = LocationUtil.calculateDistance(recyclerLocation, customerLocation);  // 使用 Haversine 公式计算距离

                    // 过滤掉超过回收员可接受距离的订单
                    if (distance <= acceptDistance.doubleValue()) {
                        // 创建 MatchingOrderVo 并将距离信息保存到 apart 字段
                        OrderMatchingVo vo = new OrderMatchingVo();
                        BeanUtils.copyProperties(order, vo);  // 拷贝订单的其他信息
                        if (!ObjectUtils.isEmpty(order.getActualPhotos())) {
                            vo.setActualPhoto(order.getActualPhotos().split(",")[0]);
                        }
                        // 将距离向上取整并保存到 apart 字段
                        vo.setApart(BigDecimal.valueOf(distance).setScale(1, RoundingMode.CEILING));
                        result.add(vo);  // 添加到 result 列表
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
    }

    /**
     * 回收员回收完后对订单进行结算
     *
     * @param orderSettlementForm
     * @return
     */
    @Override
    public Boolean settlement(OrderSettlementForm orderSettlementForm) {
        //校验结算金额
        List<OrderSettlementForm.SettlementDetailForm> settlementDetailFormList = orderSettlementForm.getSettlementDetailFormList();
        BigDecimal totalActualAmount = BigDecimal.ZERO;
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (OrderSettlementForm.SettlementDetailForm settlementDetail : settlementDetailFormList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(settlementDetail.getOrderDetailId());
            orderDetail.setUnitPrice(settlementDetail.getUnitPrice());
            orderDetail.setUpdateTime(new Date());
            if (settlementDetail.getRecycleWeigh() != null) {
                BigDecimal recycleAmount = settlementDetail.getUnitPrice().multiply(settlementDetail.getRecycleWeigh());
                totalActualAmount = totalActualAmount.add(recycleAmount);
                orderDetail.setRecycleAmount(recycleAmount);

                orderDetail.setRecycleWeigh(settlementDetail.getRecycleWeigh());
            } else {
                totalActualAmount = totalActualAmount.add(settlementDetail.getUnitPrice());
                orderDetail.setRecycleAmount(settlementDetail.getUnitPrice());
            }
            orderDetailList.add(orderDetail);
        }
        if (totalActualAmount.compareTo(orderSettlementForm.getAmount()) != 0) {
            throw new YueYiShouException(ResultCodeEnum.ARGUMENT_VALID_ERROR);
        }

        // 查询订单信息
        LambdaQueryWrapper<OrderInfo> orderInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderInfoLambdaQueryWrapper.eq(OrderInfo::getOrderNo, orderSettlementForm.getOrderNo());
        OrderInfo orderInfoDB = this.getOne(orderInfoLambdaQueryWrapper);
        if (orderInfoDB == null) {
            throw new YueYiShouException(ResultCodeEnum.ARGUMENT_VALID_ERROR);
        }

        // 增加顾客账户余额、增加奖励金明细
        BigDecimal settlementAmount = orderSettlementForm.getAmount();
        CustomerSettlementForm customerSettlementForm = new CustomerSettlementForm();
        customerSettlementForm.setCustomerId(orderInfoDB.getCustomerId());
        customerSettlementForm.setAssociatedNo(orderInfoDB.getOrderNo());
        customerSettlementForm.setAmount(settlementAmount);
        customerSettlementForm.setType(BonusDetailType.ITEMS_RECYCLE_INCOME.getType());
        customerAccountFeignClient.settlement(customerSettlementForm);

        // 更新订单信息
        LambdaUpdateWrapper<OrderInfo> orderInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        orderInfoLambdaUpdateWrapper.eq(OrderInfo::getOrderNo, orderSettlementForm.getOrderNo())
                .set(OrderInfo::getActualAmount, settlementAmount)
                .set(OrderInfo::getBonusAmount, settlementAmount.divide(CustomerConstant.BONUS_CALCULATE_DIVIDE, 2, RoundingMode.HALF_UP))
                .set(OrderInfo::getStatus, OrderStatus.SERVICE_COMPLETE)
                .set(OrderInfo::getSettleTime, new Date())
                .set(OrderInfo::getUpdateTime, new Date());
        this.update(orderInfoLambdaUpdateWrapper);

        // 更新订单明细
        return orderDetailService.updateBatchById(orderDetailList);
    }

    /**
     * 顾客下单
     *
     * @param orderPlaceForm
     * @return
     */
    @Override
    @Transactional
    public Boolean placeOrder(OrderPlaceForm orderPlaceForm) {
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderPlaceForm, orderInfo);
        orderInfo.setOrderNo(IDUtil.generateOrderNumber());
        orderInfo.setStatus(OrderStatus.WAITING_ACCEPT.getStatus());
        boolean flag = this.save(orderInfo);

        List<OrderPlaceForm.OrderDetailForm> orderDetailFormList = orderPlaceForm.getOrderDetailFormList();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailFormList.parallelStream().forEach(item -> {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(item, orderDetail);
            orderDetail.setUnitName(UnitName.getEnumByValue(item.getUnitName()).getName());
            orderDetail.setOrderNo(orderInfo.getOrderNo());
            orderDetail.setCustomerId(orderPlaceForm.getCustomerId());
            orderDetailList.add(orderDetail);
        });

        orderDetailService.saveBatch(orderDetailList);

        // 将新订单存储到redis中
        if (flag) {
            redisTemplate.opsForValue().set(RedisConstant.WAITING_ORDER + orderInfo.getId(), orderInfo);
        }
        return flag;
    }

    /**
     * 顾客取消订单
     *
     * @param orderCancelForm
     * @return
     */
    @Override
    public Boolean cancelOrder(OrderCancelForm orderCancelForm) {
        // 查询订单信息
        LambdaQueryWrapper<OrderInfo> orderInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderInfoLambdaQueryWrapper.eq(OrderInfo::getOrderNo, orderCancelForm.getOrderNo());
        OrderInfo orderInfoDB = this.getOne(orderInfoLambdaQueryWrapper);
        if (orderInfoDB == null) {
            throw new YueYiShouException(ResultCodeEnum.ARGUMENT_VALID_ERROR);
        }
        LambdaUpdateWrapper<OrderInfo> orderInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        orderInfoLambdaUpdateWrapper.set(OrderInfo::getStatus, OrderStatus.ORDER_CANCELED)
                .set(OrderInfo::getCancelMessage, orderCancelForm.getCancelReason())
                .set(OrderInfo::getCancelTime, new Date())
                .set(OrderInfo::getUpdateTime, new Date())
                .eq(OrderInfo::getOrderNo, orderCancelForm.getOrderNo());

        // 删除redis中的订单
        redisTemplate.delete(RedisConstant.WAITING_ORDER + orderInfoDB.getId());
        return this.update(orderInfoLambdaUpdateWrapper);
    }

    /**
     * 顾客根据订单号获取订单详情
     *
     * @param orderNo
     * @return
     */
    @Override
    public OrderDetailsVo getOrderDetail(String orderNo) {
        // 查询订单信息
        LambdaQueryWrapper<OrderInfo> orderInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderInfoLambdaQueryWrapper.eq(OrderInfo::getOrderNo, orderNo);
        OrderInfo orderInfoDB = this.getOne(orderInfoLambdaQueryWrapper);
        if (orderInfoDB == null) {
            throw new YueYiShouException(ResultCodeEnum.ARGUMENT_VALID_ERROR);
        }
        OrderDetailsVo orderDetailsVo = new OrderDetailsVo();
        BeanUtils.copyProperties(orderInfoDB, orderDetailsVo);
        orderDetailsVo.setStatusDesc(OrderStatus.getEnumByStatus(orderInfoDB.getStatus()).getComment());
        if (!ObjectUtils.isEmpty(orderInfoDB.getActualPhotos())) {
            String[] actualPhotoArr = orderInfoDB.getActualPhotos().split(",");
            orderDetailsVo.setActualPhoto(actualPhotoArr[0]);
            orderDetailsVo.setActualPhotoList(Arrays.stream(actualPhotoArr).toList());
        }

        Double distance = getDistanceLocation(orderInfoDB);
        if (distance != null) {
            // 将计算出的距离存储到字段
            orderDetailsVo.setDistance(BigDecimal.valueOf(distance).setScale(1, RoundingMode.CEILING));
        }

        // 查询订单明细
        List<OrderDetailsVo.OrderDetail> orderDetailList = Lists.newArrayList();
        LambdaQueryWrapper<OrderDetail> orderDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderDetailLambdaQueryWrapper.eq(OrderDetail::getOrderNo, orderInfoDB.getOrderNo());
        List<OrderDetail> orderDetailDBList = orderDetailService.list(orderDetailLambdaQueryWrapper);
        orderDetailDBList.forEach(orderDetail -> {
            OrderDetailsVo.OrderDetail orderDetailVal = new OrderDetailsVo.OrderDetail();
            BeanUtils.copyProperties(orderDetail, orderDetailVal);
            orderDetailList.add(orderDetailVal);
        });
        orderDetailsVo.setOrderDetailList(orderDetailList);

        return orderDetailsVo;
    }

    /**
     * 计算回收员和用户之间的距离
     *
     * @param orderInfoDB 订单信息
     * @return Double
     */
    private Double getDistanceLocation(OrderInfo orderInfoDB) {
        // 查询redis查看该订单距离回收员多远
        // 获取回收员的位置
        Point recyclerLocation = getRecyclerLocationFromRedis(orderInfoDB.getRecyclerId());
        if (recyclerLocation != null && orderInfoDB.getCustomerPointLongitude() != null && orderInfoDB.getCustomerPointLatitude() != null) {
            // 订单中的客户位置
            Point customerLocation = new Point(orderInfoDB.getCustomerPointLongitude().doubleValue(),
                    orderInfoDB.getCustomerPointLatitude().doubleValue());

            // 使用 Haversine 公式计算距离（单位：公里）
            return LocationUtil.calculateDistance(recyclerLocation, customerLocation);
        }

        return null;
    }

    /**
     * 获取顾客我的页面初始信息
     *
     * @param customerId
     * @return
     */
    @Override
    public OrderMyVo getMyCenterInfo(Long customerId) {
        OrderMyVo orderMyVo = new OrderMyVo();
        LambdaQueryWrapper<OrderInfo> orderInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderInfoLambdaQueryWrapper.eq(OrderInfo::getCustomerId, customerId)
                .eq(OrderInfo::getStatus, OrderStatus.SERVICE_COMPLETE.getStatus());
        List<OrderInfo> list = this.list(orderInfoLambdaQueryWrapper);
        if (!ObjectUtils.isEmpty(list)) {
            orderMyVo.setRecycleCount((long) list.size());
            LambdaQueryWrapper<OrderDetail> orderDetailLambdaQueryWrapper;
            for (OrderInfo orderInfo : list) {
                orderDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
                orderDetailLambdaQueryWrapper.eq(OrderDetail::getOrderNo, orderInfo.getOrderNo());
                List<OrderDetail> orderDetailList = orderDetailService.list(orderDetailLambdaQueryWrapper);
                orderDetailList.forEach(orderDetail -> {
                    if (orderDetail.getRecycleWeigh() == null) {
                        orderDetail.setRecycleWeigh(CategoryConstant.NON_WEIGHING_ITEMS_CARBON_EMISSIONS);
                    }
                    orderMyVo.setCarbonEmissions(orderDetail.getRecycleWeigh().add(orderMyVo.getCarbonEmissions()));
                });
            }
        }
        return orderMyVo;
    }


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 订单分页列表查询
     *
     * @param orderMgrQueryForm
     * @return
     */
    @Override
    public Page<OrderMgrQueryVo> queryPageList(OrderMgrQueryForm orderMgrQueryForm, Integer pageNo, Integer pageSize) {
        LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(!ObjectUtils.isEmpty(orderMgrQueryForm.getCustomerId()), OrderInfo::getCustomerId, orderMgrQueryForm.getCustomerId());
        lambdaQueryWrapper.eq(!ObjectUtils.isEmpty(orderMgrQueryForm.getRecyclerId()), OrderInfo::getRecyclerId, orderMgrQueryForm.getRecyclerId());
        lambdaQueryWrapper.eq(!StringUtils.isEmpty(orderMgrQueryForm.getPhoneNumber()), OrderInfo::getContactPhone, orderMgrQueryForm.getPhoneNumber());
        lambdaQueryWrapper.eq(!StringUtils.isEmpty(orderMgrQueryForm.getRecyclerName()), OrderInfo::getRecyclerName, orderMgrQueryForm.getRecyclerName());
        lambdaQueryWrapper.eq(!ObjectUtils.isEmpty(orderMgrQueryForm.getAppointmentTime()), OrderInfo::getAppointmentTime, orderMgrQueryForm.getAppointmentTime());
        lambdaQueryWrapper.eq(!ObjectUtils.isEmpty(orderMgrQueryForm.getStatus()), OrderInfo::getStatus, orderMgrQueryForm.getStatus());
        lambdaQueryWrapper.orderByDesc(OrderInfo::getCreateTime);

        List<OrderMgrQueryVo> orderMgrQueryVoList;
        List<OrderInfo> orderInfoList;
        Page<OrderMgrQueryVo> returnPage;
        if (pageNo == null || pageSize == null) {
            //全量查询
            orderInfoList = this.list(lambdaQueryWrapper);
            returnPage = new Page<>(1, orderInfoList.size(), orderInfoList.size());
        } else {
            //分页查询
            Page<OrderInfo> orderInfoPage = this.page(new Page<>(pageNo, pageSize), lambdaQueryWrapper);
            orderInfoList = orderInfoPage.getRecords();
            returnPage = new Page<>(orderInfoPage.getCurrent(), orderInfoPage.getSize(), orderInfoPage.getTotal());
        }
        orderMgrQueryVoList = orderInfoList.parallelStream().map(orderInfo -> {
            OrderMgrQueryVo orderMgrQueryVo = new OrderMgrQueryVo();
            BeanUtils.copyProperties(orderInfo, orderMgrQueryVo);
            orderMgrQueryVo.setStatusDesc(OrderStatus.getEnumByStatus(orderInfo.getStatus()).getComment());

            return orderMgrQueryVo;
        }).toList();
        return returnPage.setRecords(orderMgrQueryVoList);
    }
}
