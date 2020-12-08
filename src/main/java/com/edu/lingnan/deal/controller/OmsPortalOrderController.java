package com.edu.lingnan.deal.controller;

import com.edu.lingnan.deal.service.OmsPortalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/12/7 下午10:58
 */
@Controller
public class OmsPortalOrderController {

    @Autowired
    private OmsPortalOrderService portalOrderService;

    @GetMapping("/generateOrder")
    @ResponseBody
    public String generateOrder() {
        portalOrderService.generateOrder();
        return "请求成功";
    }




}
