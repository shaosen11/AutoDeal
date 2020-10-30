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
        System.out.println(lianluService.getByName("HKG/CT-SHI/CT VC4S044/2M22"));
    }

}
