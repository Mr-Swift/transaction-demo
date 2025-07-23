package com.apple.dev;

import com.apple.dev.service.ChangeBalance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;

@SpringBootTest
class DevApplicationTests {


    @Resource
    private ChangeBalance changeBalance;

    @Test
    void contextLoads() {

    }

    @Test
    void test1() {
        changeBalance.change1();
    }

    @Test
    void test2() {
        changeBalance.change2();
    }
}
