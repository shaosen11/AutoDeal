package com.edu.lingnan.deal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/12/4 下午9:41
 */
@RestController
public class testController {

    @GetMapping("/test1")
    public String test1(){
        return "test1";
    }

    @GetMapping("/test2")
    public String test2(){
        return "test2";
    }
}


