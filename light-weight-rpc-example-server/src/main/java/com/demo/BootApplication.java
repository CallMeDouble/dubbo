package com.demo;

import com.demo.service.ServerFactoryBean;
import demo.dubbo.service.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.UnknownHostException;

@SpringBootApplication
public class BootApplication {

	@Bean
	public HelloService hello() {
		return new HelloServiceImpl();
	}

	@Bean
	public ServerFactoryBean serverFactoryBean(){
		ServerFactoryBean serverFactoryBean = new ServerFactoryBean();
		serverFactoryBean.setPort(8080);
		serverFactoryBean.setServiceImpl(HelloServiceImpl.class);//会被调用它的方法
		serverFactoryBean.setServiceName("helloService");
		serverFactoryBean.setZkConn("127.0.0.1:2181");

		new Thread(() -> {
			try {
				serverFactoryBean.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "RpcServer").start();
		return serverFactoryBean;
	}


	public static void main(String[] args) throws UnknownHostException {
		SpringApplication.run(BootApplication.class, "--server.port=8082");
	}
}
