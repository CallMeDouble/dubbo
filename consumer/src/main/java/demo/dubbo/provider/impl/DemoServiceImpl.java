package demo.dubbo.provider.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;

import demo.dubbo.service.DemoService;

/**
 * Created by allan on 16-11-1.
 */
public class DemoServiceImpl implements DemoService {

    public String sayHello(String name) {
        String hostname = null;
        String ip = null;
        try {
            InetAddress ia = InetAddress.getLocalHost();
            hostname = ia.getHostName();
            ip = ia.getHostAddress();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(100000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Current Time is :  " + name + " hostname: " + hostname + " ip: " + ip;
    }
}
