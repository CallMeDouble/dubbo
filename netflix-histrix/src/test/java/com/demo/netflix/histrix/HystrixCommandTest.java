package com.demo.netflix.histrix;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Author: shuanglong.zhu@ele.me
 * @Date: 2019/1/28 下午5:45
 */
@ActiveProfiles(profiles = {"dev"})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes = {HystrixApplication.class})
public class HystrixCommandTest {

    @Test
    public void testSynchronous() {
//        assertEquals("Hello World!", new HystrixCommandHelloworld("World").execute());
//        assertEquals("Hello Bob!", new HystrixCommandHelloworld("Bob").execute());
    }
}
