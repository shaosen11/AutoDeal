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
        List<lianlu> frontList = frontList(str);
        List<lianlu> markList = markList(frontList);
        Map<String, List> map = new HashMap<>();
        map.put("frontList", frontList);
        map.put("markList", markList);
        return map;
    }

    @ResponseBody
    @PostMapping("/insert")
    public Integer insert(String str) {
        str = str.trim();
        Integer index = 0;
        lianlu lianlu = new lianlu();
        for (int i = 0; i < str.length(); i++) {
            if (i == str.length() - 1 || str.charAt(i + 1) == '<') {
                String lianluStr = str.substring(index, i);
                lianlu frontListToLianlu = frontListToLianlu(lianluStr);
                if (insertFlag(frontListToLianlu)) {
                    if (lianluService.getByName(frontListToLianlu.getName()) == null) {
                        lianluService.insert(frontListToLianlu);
                    }
                }
                index = i;
            }
        }
        return 1;
    }

    /**
     * 判断是否需要处理端口
     *
     * @param str
     * @return
     */
    public lianlu duankou(String str) {
        int lianluIndex = str.indexOf("<");
        int duankouIndex = str.indexOf("[");
        if (duankouIndex < lianluIndex) {
            return new lianlu().setName(str.substring(str.indexOf("["), str.indexOf("]") + 1)).setMark(StringContent.duankouxuyaochuli);
        }
        return null;
    }

    /**
     * 整理链路
     *
     * @param str
     * @return
     */
    public List frontList(String str) {
        List<lianlu> frontList = new ArrayList<>();
        Integer index = 0;
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '[' || str.charAt(i) == '<') {
                line.setLength(0);
                index = i;
            } else if (str.charAt(i) == ']' || str.charAt(i) == '>') {
                for (int j = index; j <= i; j++) {
                    line.append(str.charAt(j));
                }
                frontList.add(new lianlu().setName(line.toString()));
            }
        }
        return frontList;
    }

    /**
     * 自动标记
     * @param frontList
     * @return
     */
    public List markList(List<lianlu> frontList) {
        List<lianlu> markList = new ArrayList();
        lianlu duankou = duankou(frontList.toString().substring(1,frontList.toString().length()-1));
        if (duankou != null) {
            markList.add(duankou);
        }
        for (lianlu lianlu : frontList) {
            if (lianlu.getName().startsWith("<")) {
                if (lianlu.getName().lastIndexOf("/") != -1) {
                    lianlu lianluServiceByName = lianluService.getByName(lianlu.getName().substring(1, lianlu.getName().lastIndexOf(">")));
                    if (lianluServiceByName==null){
                        lianluServiceByName = lianluService.getByName(lianlu.getName().substring(1, lianlu.getName().lastIndexOf("/")));
                    }
                    if (lianluServiceByName != null) {
                        markList.add(lianlu.setMark(lianluServiceByName.getMark()));
                    } else {
                        markList.add(lianlu.setName(lianlu.getName()).setMark(StringContent.qingchaxun));
                    }
                } else {
                    markList.add(lianlu.setMark(StringContent.lianlubuqueding));
                }
            }
        }
        return markList;
    }


    /**
     * 把string转换为lianlu
     * @param lianluStr
     * @return
     */
    public lianlu frontListToLianlu(String lianluStr){
        String name;
        String mark = lianluStr.substring(lianluStr.lastIndexOf(">") + 1, lianluStr.length());
        if (mark.equals(StringContent.sulvbucunzai)){
            name = lianluStr.substring(lianluStr.indexOf("<") + 1, lianluStr.lastIndexOf(">"));
        }else {
            name = lianluStr.substring(lianluStr.indexOf("<") + 1, lianluStr.lastIndexOf("/"));
        }
        return new lianlu(name, mark);
    }


    /**
     * 判断是否需要插入
     * @param lianlu
     * @return
     */
    public boolean insertFlag(lianlu lianlu){
        if(lianlu.getMark().equals(StringContent.lianlubucunzai)||lianlu.getMark().equals(StringContent.sulvbucunzai)){
            return true;
        }
        return false;
    }
}
