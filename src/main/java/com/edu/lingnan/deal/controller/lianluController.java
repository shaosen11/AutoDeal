package com.edu.lingnan.deal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author shaosen
 * @Description //TODO
 * @Date 16:35 2020/10/30
 */
@Controller
public class lianluController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @ResponseBody
    @PostMapping("/query")
    public List query(String str) {
        List list = new ArrayList();
        for (int i = 0; i < str.length(); i++){
            if(str.charAt(i)=='['||str.charAt(i)=='<'){
                System.out.println();
                System.out.print(str.charAt(i));
            }else if(str.charAt(i)==']'||str.charAt(i)=='>'){
                System.out.print(str.charAt(i));
                System.out.println();
            }else{
                System.out.print(str.charAt(i));
            }
        }
        return null;
    }
}
