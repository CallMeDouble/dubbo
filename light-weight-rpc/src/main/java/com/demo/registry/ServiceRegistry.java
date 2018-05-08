package com.demo.registry;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.demo.constant.Constant;

/**
 * Created by dragon
 *
 * 使用 ZooKeeper 客户端可轻松实现服务注册功能
 */
@Configuration
public class ServiceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistry.class);

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Value("${service.address}")
    private String serviceAddress;

    public ServiceRegistry(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    private ZooKeeper connServer() throws Exception {
        ZooKeeper zooKeeper = null;
        zooKeeper = new ZooKeeper(serviceAddress, Constant.ZK_SESSION_TIMEOUT, (e) -> {
            if(e.getState() == Watcher.Event.KeeperState.SyncConnected){
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        return zooKeeper;
    }

    /**
     * 首先需要使用 ZooKeeper 客户端命令行创建/registry永久节点，用于存放所有的服务临时节点。
     * @param zooKeeper
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void createNode(ZooKeeper zooKeeper, String data) throws KeeperException, InterruptedException {
        byte[] dataBytes = data.getBytes();
        String path = zooKeeper
                .create(Constant.ZK_DATA_PATH, dataBytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        LOGGER.debug("create zookeeper node ({} => {})", path, data);
    }
}
