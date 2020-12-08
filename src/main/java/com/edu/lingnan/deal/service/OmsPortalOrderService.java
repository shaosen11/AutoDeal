package com.edu.lingnan.deal.service;

import org.springframework.transaction.annotation.Transactional;

public interface OmsPortalOrderService {

    /**
     * 根据提交信息生成订单
     */
    @Transactional
    void generateOrder();

    /**
     * 取消单个超时订单
     */
    @Transactional
    void cancelOrder(Long orderId);
}
