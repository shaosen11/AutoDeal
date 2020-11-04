package com.edu.lingnan.deal.controller;

import com.edu.lingnan.deal.pojo.lianlu;
import com.edu.lingnan.deal.service.lianluService;
import com.edu.lingnan.deal.util.StringContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

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
        for (int i = 0; i < str.length(); i++) {
            if (i == str.length() - 1) {
                String lianluStr = str.substring(index, i + 1);
                lianlu frontListToLianlu = frontListToLianlu(lianluStr);
                insertDB(frontListToLianlu);
                index = i;
            } else if (str.charAt(i + 1) == '<') {
                String lianluStr = str.substring(index, i);
                lianlu frontListToLianlu = frontListToLianlu(lianluStr);
                insertDB(frontListToLianlu);
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
        if (duankouIndex != -1 && lianluIndex != -1) {
            if (duankouIndex < lianluIndex) {
                return new lianlu().setName(str.substring(str.indexOf("["), str.indexOf("]") + 1)).setMark(StringContent.duankouxuyaochuli);
            }
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
        Stack<Integer> depth = new Stack();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '[') {
                line.setLength(0);
                index = i;
                depth.push(index);
            } else if (str.charAt(i) == ']') {
                if (depth.size() == 1) {
                    for (int j = depth.pop(); j <= i; j++) {
                        line.append(str.charAt(j));
                    }
                    if (line.toString().contains("支路")) {
                        List<lianlu> lianlus = frontProtectList(line.toString().substring(1, line.toString().length() - 1));
                        frontList.addAll(lianlus);
                    } else {
                        frontList.add(new lianlu().setName(line.toString()));
                    }

                } else if (depth.size() > 1) {
                    depth.pop();
                }
            }
            if (str.charAt(i) == '<') {
                line.setLength(0);
                index = i;
            } else if (str.charAt(i) == '>') {
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
     *
     * @param frontList
     * @return
     */
    public List markList(List<lianlu> frontList) {
        List<lianlu> markList = new ArrayList();
        lianlu duankou = duankou(frontList.toString().substring(1, frontList.toString().length() - 1));
        if (duankou != null) {
            markList.add(duankou);
        }
        for (lianlu lianlu : frontList) {
            if (lianlu.getName().startsWith("<")) {
                if (lianlu.getName().lastIndexOf("/") != -1) {
                    lianlu lianluServiceByName = lianluService.getByName(lianlu.getName().substring(1, lianlu.getName().lastIndexOf(">")));
                    if (lianluServiceByName == null) {
                        lianluServiceByName = lianluService.getByName(lianlu.getName().substring(1, lianlu.getName().lastIndexOf("/")));
                    }
                    if (lianluServiceByName != null) {
                        markList.add(lianlu.setMark(lianluServiceByName.getMark()));
                    } else {
                        lianlu.setName(lianlu.getName());
                        if (lianlu.getMark() == null) {
                            lianlu.setMark(StringContent.qingchaxun);
                        }
                        markList.add(lianlu);
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
     *
     * @param lianluStr
     * @return
     */
    public lianlu frontListToLianlu(String lianluStr) {
        String name = new String();
        String mark = lianluStr.substring(lianluStr.lastIndexOf(">") + 1);
        if (mark.equals(StringContent.sulvbucunzai)) {
            name = lianluStr.substring(lianluStr.indexOf("<") + 1, lianluStr.lastIndexOf(">"));
        } else if (mark.equals(StringContent.lianlubucunzai)) {
            name = lianluStr.substring(lianluStr.indexOf("<") + 1, lianluStr.lastIndexOf("/"));
        }
        return new lianlu(name, mark);
    }


    /**
     * 判断是否需要插入
     *
     * @param lianlu
     * @return
     */
    public boolean insertFlag(lianlu lianlu) {
        if (lianlu.getMark().equals(StringContent.lianlubucunzai) || lianlu.getMark().equals(StringContent.sulvbucunzai)) {
            return true;
        }
        return false;
    }

    public void insertDB(lianlu lianlu){
        if (insertFlag(lianlu)) {
            if (lianluService.getByName(lianlu.getName()) == null) {
                lianluService.insert(lianlu);
            }else {
                lianluService.update(lianlu);
            }
        }
    }

    public List<lianlu> frontProtectList(String str) {
        System.out.println(str);
        String str1 = str.substring(str.lastIndexOf("支路1") + 4, str.lastIndexOf("；"));
        String str2 = str.substring(str.lastIndexOf("支路2") + 4);
        Integer index = 0;
        StringBuilder line = new StringBuilder();
        List<lianlu> frontList = new ArrayList<>();
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) == '<') {
                line.setLength(0);
                index = i;
            } else if (str1.charAt(i) == '>') {
                for (int j = index; j <= i; j++) {
                    line.append(str1.charAt(j));
                }
                frontList.add(new lianlu().setName(line.toString()));
            }
        }
        for (int i = 0; i < str2.length(); i++) {
            if (str2.charAt(i) == '<') {
                line.setLength(0);
                index = i;
            } else if (str2.charAt(i) == '>') {
                for (int j = index; j <= i; j++) {
                    line.append(str2.charAt(j));
                }
                frontList.add(new lianlu().setName(line.toString()).setMark(StringContent.baohulianlu));
            }
        }
        return frontList;
    }
}
