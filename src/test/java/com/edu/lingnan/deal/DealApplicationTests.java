package com.edu.lingnan.deal;

import com.edu.lingnan.deal.service.lianluService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DealApplicationTests {

    @Autowired
    private lianluService lianluService;

    @Test
    public void contextLoads() {
        String str1 = "广东省, M宽带,\"\", 1个月";
        addDoubleQuotationMarks(str1);
        String str2 = "广东省";
        addDoubleQuotationMarks(str2);
        String str3 = "\"\"广东省";
        addDoubleQuotationMarks(str3);
    }

    private String addDoubleQuotationMarks(String str) {
        System.out.println(str);
        StringBuffer newStr = new StringBuffer(str);
        //处理"
        if (newStr.indexOf("\"") != -1) {
            for (int i = 0; i < newStr.length(); i++) {
                if (newStr.charAt(i) == '"') {
                    newStr.insert(i, '"');
                    i++;
                }
            }
        }
        //处理,
        if (newStr.indexOf(",") != -1) {
            newStr.insert(0, '"');
            newStr.insert(newStr.length(), '"');
        }
        str = newStr.toString();
        System.out.println(str);
        return str;
    }

}
