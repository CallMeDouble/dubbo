package com.demo.factory;

import com.demo.service.ClientProxyFactoryBean;
import demo.dubbo.service.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by dragon
 */
@Configuration
@RestController
@SpringBootApplication
@RequestMapping("/test")
public class BootApplication {
    @Bean
    public HelloService clientFactoryBean() throws Exception {
        ClientProxyFactoryBean<HelloService> clientFactoryBean = new ClientProxyFactoryBean<HelloService>();
        clientFactoryBean.setZkConn("127.0.0.1:2181");
        clientFactoryBean.setServiceName("hello");
        clientFactoryBean.setServiceInterface(HelloService.class);
        return clientFactoryBean.getObject();
    }
    @Resource
    private HelloService helloWorld;

    @RequestMapping("/hello")
    public String hello(String say) {
        return helloWorld.sayHello(say);
    }
    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, "--server.port=8081");
    }
}
