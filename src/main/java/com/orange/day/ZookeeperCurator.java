package com.orange.day;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.RetryNTimes;

public class ZookeeperCurator {

    /** Zookeeper info */
    private static final String ZK_ADDRESS = "39.106.119.206:2181";
    private static final String ZK_PATH = "/zktest";

    public static void main(String[] args) throws Exception {
        // 1.Connect to zk
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                ZK_ADDRESS,
                60000,
                20000,
                new RetryNTimes(10, 500)
        );
        client.start();
        PathChildrenCache watcher = new PathChildrenCache(
                client,
                ZK_PATH,
                true    // if cache data
        );
        watcher.getListenable().addListener((client1, event) -> {
            ChildData data = event.getData();
            if (data == null) {
                System.out.println("No data in event[" + event + "]");
            } else {
                System.out.println("Receive event: "
                        + "type=[" + event.getType() + "]"
                        + ", path=[" + data.getPath() + "]"
                        + ", data=[" + new String(data.getData()) + "]"
                        + ", stat=[" + data.getStat() + "]");
            }
        });
        watcher.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        String data1 = "hello";
        if(client.checkExists().forPath(ZK_PATH)==null){
            client.create().creatingParentsIfNeeded().forPath(ZK_PATH, data1.getBytes());
        }
        print(client.getChildren().forPath("/"));
        print(client.getData().forPath(ZK_PATH));
        String data2 = "world";
        client.setData().forPath(ZK_PATH, data2.getBytes());
        print(client.getData().forPath(ZK_PATH));

        print(client.getChildren().forPath("/"));
        client.create().forPath("/zktest/11", data1.getBytes());
        Thread.sleep(1000);
        client.create().forPath("/zktest/12", data1.getBytes());
        Thread.sleep(1000);
        client.delete().forPath("/zktest/11");
        client.delete().forPath("/zktest/12");
        client.delete().forPath(ZK_PATH);
        Thread.sleep(5000);
        client.close();
    }
    private static void print(String... cmds) {
        StringBuilder text = new StringBuilder("$ ");
        for (String cmd : cmds) {
            text.append(cmd).append(" ");
        }
        System.out.println(text.toString());
    }
    private static void print(Object result) {
        System.out.println(
                result instanceof byte[]
                        ? new String((byte[]) result)
                        : result);
    }

}
