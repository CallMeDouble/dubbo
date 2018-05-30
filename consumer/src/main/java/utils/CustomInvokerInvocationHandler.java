package utils;

import java.lang.reflect.Method;
import java.util.List;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.integration.RegistryDirectory;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.alibaba.dubbo.rpc.cluster.Directory;
import com.alibaba.dubbo.rpc.cluster.support.wrapper.MockClusterInvoker;
import com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler;

/**
 * Created by allan on 16-11-21.
 */
public class CustomInvokerInvocationHandler extends InvokerInvocationHandler {

    private final Invoker<?> invoker;

    public CustomInvokerInvocationHandler(Invoker<?> handler) {
        super(handler);
        this.invoker = handler;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result;
        try {
            result = super.invoke(proxy, method, args);
        } catch (RpcException e) {

            e.printStackTrace();
            if (e.isTimeout()) {
                System.out.println("timeout..............");
            }
            result = null;
        }

        return result;
    }

}
