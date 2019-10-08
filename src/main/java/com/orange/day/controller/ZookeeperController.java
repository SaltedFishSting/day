package com.orange.day.controller;


import com.fasterxml.jackson.databind.ser.Serializers;
import com.orange.day.model.ResultBean;
import com.orange.day.model.ResultCode;
import com.orange.day.service.UserInfoService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.logging.log4j.util.Strings;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zookeeper")
public class ZookeeperController extends BaseController {

    @Autowired
    public CuratorFramework curatorFramework;


    @GetMapping("/getAll")
    public ResultBean saveUser() throws Exception {

        List ss=curatorFramework.getChildren().forPath("/zookeeper");
        List<ACL> acls = new ArrayList<ACL>();
        Id id=new Id("digest","orange:wang467132");
        ACL acl=new ACL(ZooDefs.Perms.ALL,id);
        acls.add(acl);
        curatorFramework.create().creatingParentsIfNeeded().withACL(acls).forPath("/orange","orange".getBytes());
        curatorFramework.getData().forPath("/orange");
        ResultBean resultBean =new ResultBean();
        resultBean.setData("/",ss);
        return resultBean;
    }


}
