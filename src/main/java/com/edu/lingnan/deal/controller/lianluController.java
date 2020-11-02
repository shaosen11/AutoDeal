package com.edu.lingnan.deal.controller;

import com.edu.lingnan.deal.pojo.lianlu;
import com.edu.lingnan.deal.service.lianluService;
import com.edu.lingnan.deal.util.StringContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author shaosen
 * @Description //TODO
 * @Date 16:35 2020/10/30
 */
@Controller
public class lianluController {

    @Autowired
    private lianluService lianluService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @ResponseBody
    @PostMapping("/query")
    public Map query(String str) {
        List<lianlu> frontList = new ArrayList<>();
        Integer index=0;
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < str.length(); i++){
            if(str.charAt(i)=='['||str.charAt(i)=='<'){
                line.setLength(0);
                index = i;
            }else if(str.charAt(i)==']'||str.charAt(i)=='>'){
                for(int j=index;j<=i;j++){
                    line.append(str.charAt(j));
                }
                frontList.add(new lianlu().setName(line.toString()));
            }
        }
        List<lianlu> markList = new ArrayList();
        for (lianlu lianlu : frontList) {
            if(lianlu.getName().startsWith("<")){
                if (lianlu.getName().lastIndexOf("/")!=-1){
                    if(lianluService.getByName(lianlu.getName().substring(1, lianlu.getName().lastIndexOf("/")))!=null){
                        markList.add(lianlu.setName(lianlu.getName() + StringContent.lianlubucunzai));
                    }else{
                        markList.add(lianlu.setName(lianlu.getName() + StringContent.qingchaxun));
                    }
                }else{
                    markList.add(lianlu.setName(lianlu.getName() + StringContent.lianlubuqueding));
                }
            }
        }
        Map<String, List> map = new HashMap<>();
        map.put("frontList", frontList);
        map.put("markList", markList);
        return map;
    }

    @ResponseBody
    @PostMapping("/insert")
    public Integer insert(String str) {
        str  = str.trim();
        Integer index=0;
        lianlu lianlu = new lianlu();
        for (int i = 0; i < str.length(); i++){
            if(i==str.length()-1||str.charAt(i+1)=='<'){
                String lianluStr = str.substring(index, i);
                String name;
                if (lianluStr.lastIndexOf("/")!=-1){
                    name = lianluStr.substring(lianluStr.indexOf("<")+1, lianluStr.lastIndexOf("/"));
                }else{
                    name = lianluStr.substring(lianluStr.indexOf("<")+1, lianluStr.lastIndexOf(">"));
                }
                String mark = lianluStr.substring(lianluStr.lastIndexOf(">")+1, lianluStr.length());
                if(lianluService.getByName(name)==null){
                    lianlu.setName(name).setMark(mark);
                    lianluService.insert(lianlu);
                }
                index = i;
            }

        }

        return 1;
    }

}
