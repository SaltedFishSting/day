package com.orange.day.controller;


import com.orange.day.model.ResultBean;
import com.orange.day.model.ResultCode;
import com.orange.day.model.UserInfo;
import com.orange.day.service.UserInfoService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/save")
    public ResultBean saveUser(@RequestBody Map map) {
        String userName;
        String userPhone;
        String userpassword;
        userName = map.get("userName").toString();
        userPhone = map.get("userPhone").toString();
        userpassword = map.get("userPassword").toString();

        if (Strings.isEmpty(userName) || Strings.isEmpty(userPhone) || Strings.isEmpty(userpassword)) {
            ResultBean resultBean =new ResultBean();
            resultBean.setCode(ResultCode.PARAMENT_ILLEGAL.code);
            resultBean.setDesc(ResultCode.PARAMENT_ILLEGAL.desc);
        }
        userInfoService.saveUser(userName, userPhone, userpassword);
        ResultBean resultBean =new ResultBean();
        resultBean.setCode(ResultCode.SUCCESS.code);
        resultBean.setDesc(ResultCode.SUCCESS.desc);
        return resultBean;
    }

    @GetMapping("/getByPhone/{phone}")
    public ResultBean saveUser(@PathVariable String phone) {
        ResultBean resultBean =new ResultBean();
        resultBean.setCode(ResultCode.SUCCESS.code);
        resultBean.setDesc(ResultCode.SUCCESS.desc);
        resultBean.setData("user", userInfoService.getUserByPhone(phone));
        return resultBean;
    }

}
