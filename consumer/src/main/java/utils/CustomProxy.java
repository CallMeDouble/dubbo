package utils;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler;

import java.lang.reflect.Method;

/**
 * Created by allan on 16-11-21.
 */
public class CustomProxy extends InvokerInvocationHandler {

    public CustomProxy(Invoker<?> handler) {
        super(handler);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return super.invoke(proxy, method, args);
    }


}
