package com.edu.lingnan.deal.component;

import com.edu.lingnan.deal.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/12/7 下午10:55
 */
@Component
@RabbitListener(queues = "mall.order.cancel")
public class CancelOrderReceiver {
    private static Logger LOGGER = LoggerFactory.getLogger(CancelOrderReceiver.class);

    @Autowired
    private OmsPortalOrderService portalOrderService;

    @RabbitHandler
    public void handle(Long orderId){
        LOGGER.info("receive delay message orderId:{}",orderId);
        System.out.println("消费者收到数据");
        portalOrderService.cancelOrder(orderId);
    }
}
