package utils;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Cluster;
import com.alibaba.dubbo.rpc.cluster.Directory;

/**
 * Created by allan on 16-11-23.
 */
public class CustomCluster implements Cluster {

    private DemoBean demoBean;

    public void setDemoBean(DemoBean demoBean) {
        this.demoBean = demoBean;
    }

    public <T> Invoker<T> join(Directory<T> directory) throws RpcException {
        return new CustomFailoverClusterInvoker<T>(directory, demoBean);
    }
}
