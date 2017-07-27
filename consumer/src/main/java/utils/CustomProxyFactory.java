package utils;

import com.alibaba.dubbo.common.bytecode.Proxy;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler;
import com.alibaba.dubbo.rpc.proxy.javassist.JavassistProxyFactory;

/**
 * Created by allan on 16-11-21.
 */
public class CustomProxyFactory extends JavassistProxyFactory {
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {
        return (T) Proxy.getProxy(interfaces).newInstance(new CustomInvokerInvocationHandler(invoker));
    }

}
