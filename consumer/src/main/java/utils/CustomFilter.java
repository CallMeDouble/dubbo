package utils;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.filter.TimeoutFilter;

import java.util.Arrays;

/**
 * Created by allan on 16-11-21.
 */
public class CustomFilter extends TimeoutFilter {
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result;
        try {
            result = super.invoke(invoker, invocation);
        } catch (RpcException e) {
            result = null;
        }

        return result;
    }


}
