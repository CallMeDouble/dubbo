package utils;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Directory;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.alibaba.dubbo.rpc.cluster.support.FailoverClusterInvoker;

/**
 * Created by allan on 16-11-21.
 */
public class CustomFailoverCluster extends FailoverCluster {

    public <T> Invoker<T> join(Directory<T> directory) throws RpcException {
       // return new CustomFailoverClusterInvoker<T>(directory);
        return null;
    }
}
