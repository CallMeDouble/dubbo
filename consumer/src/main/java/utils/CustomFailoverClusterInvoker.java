package utils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.container.page.Page;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Directory;
import com.alibaba.dubbo.rpc.cluster.LoadBalance;
import com.alibaba.dubbo.rpc.cluster.support.FailoverClusterInvoker;

import javax.annotation.Resource;

/**
 * Created by allan on 16-11-21.
 */
public class CustomFailoverClusterInvoker<T> extends FailoverClusterInvoker<T> {

    private DemoBean demoBean;

    public CustomFailoverClusterInvoker(Directory<T> directory, DemoBean demoBean) {
        super(directory);
        this.demoBean = demoBean;
    }

    public Result doInvoke(Invocation invocation, final List<Invoker<T>> invokers, LoadBalance loadbalance)
            throws RpcException {

        Result result;
        try {
            result = super.doInvoke(invocation, invokers, loadbalance);
        } catch (RpcException e) {

            System.out.println(demoBean == null);
            List<String> rmiUrl = new ArrayList<String>();
            for(Invoker<T> invoker : invokers) {
                rmiUrl.add(invoker.getUrl().toFullString());
            }

            throw e;
        }

        return result;
    }
}
