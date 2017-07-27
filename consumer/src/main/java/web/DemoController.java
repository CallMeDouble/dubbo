package web;

import demo.dubbo.service.AnotherService;
import demo.dubbo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by allan on 16-11-1.
 */
@Controller
public class DemoController {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private DemoService demoService;

    @Autowired
    private AnotherService anotherService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {

        String name = demoService.sayHello(sdf.format(new Date(System.currentTimeMillis())));
        return name;
    }

    @RequestMapping("/hi")
    @ResponseBody
    public String sayHi() {

        String name = anotherService.sayHi(sdf.format(new Date(System.currentTimeMillis())));
        return name;
    }
}
