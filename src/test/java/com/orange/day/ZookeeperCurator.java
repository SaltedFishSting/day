package com.orange.day;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ZookeeperCurator {

    /**
     * Zookeeper info
     */
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


        TreeCache treeCache = new TreeCache(client, ZK_PATH);
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) {
                ChildData data = treeCacheEvent.getData();
                if (data == null || data.getData() == null) {
                    System.out.println("No data in event[" + treeCacheEvent + "]");
                } else {
                    System.out.println("Receive event: "
                            + "type=[" + treeCacheEvent.getType() + "]"
                            + ", path=[" + data.getPath() + "]"
                            + ", data=[" + new String(data.getData()) + "]"
                            + ", stat=[" + data.getStat() + "]");
                }

            }
        });
        print("conn ready start");
        client.start();
        print("conn start");
        print("treeCache ready start");
        treeCache.start();
        print("treeCache start");
        Thread.sleep(1000);
        String data1 = "hello";
        if (client.checkExists().forPath(ZK_PATH) == null) {
            print(1);
            client.create().forPath(ZK_PATH, data1.getBytes());
        }else {
            print(1);
            client.delete().deletingChildrenIfNeeded().forPath(ZK_PATH);
            client.create().forPath(ZK_PATH, data1.getBytes());
        }
        print(2);
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
        client.setData().forPath("/zktest/11", "11111".getBytes());
        Thread.sleep(1000);
        client.setData().forPath("/zktest/12", "22222".getBytes());
        Thread.sleep(1000);
        client.setData().forPath(ZK_PATH, "ZK_PATH".getBytes());
        Thread.sleep(1000);
//        Collection<CuratorTransactionResult> results=null;
//        try {
//            results = client.inTransaction().setData().forPath("/zktest/11", "oneone".getBytes()).and().delete().forPath(ZK_PATH).and().commit();
//        }catch (Exception e){
//            print(e.getMessage());
//        }
//        if(results!=null) {
//            for (CuratorTransactionResult result : results) {
//                System.out.println("执行结果是： " + result.getForPath() + "--" + result.getType());
//            }
//        }
//        print("事务");
//        print(client.getData().forPath("/zktest/11"));
//        print(client.getChildren().forPath(ZK_PATH));
//        client.delete().forPath("/zktest/11");
//        client.delete().forPath("/zktest/12");
//
//
//
//
//
//        Thread.sleep(1000);
//        InterProcessMutex lock1=new InterProcessMutex(client,ZK_PATH);
//        InterProcessMutex lock2=new InterProcessMutex(client,ZK_PATH);
//
//        Thread t1 = new Thread(() -> {
//            try {
//                print(client.getData().forPath(ZK_PATH));
//                lock1.acquire(100, TimeUnit.SECONDS);
//                print(client.getData().forPath(ZK_PATH));
//                System.out.println("t1 start");
//                Thread.sleep(5000);
//                System.out.println("t1 end");
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }finally {
//                try {
//                    lock1.release();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }, "t1");
//        Thread t2 = new Thread(() -> {
//            try {
//                print(client.getData().forPath(ZK_PATH));
//                lock2.acquire(100, TimeUnit.SECONDS);
//                print(client.getData().forPath(ZK_PATH));
//                System.out.println("t2 start");
//                Thread.sleep(4000);
//                System.out.println("t2 end");
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }finally {
//                try {
//                    lock2.release();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "t2");
//
//        t1.start();
//        t2.start();
        System.out.println("*******************");
        List<ACL> acls = new ArrayList<ACL>();
        Id id=new Id("digest","orange:wang467132");
        ACL acl=new ACL(ZooDefs.Perms.ALL,id);
        acls.add(acl);
        client.create().withACL(acls).forPath("/orange","orangeData".getBytes());
        print(client.getData().forPath("/orange"));
        System.out.println("*******************");
        Thread.sleep(10000);
        client.delete().forPath(ZK_PATH);
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
