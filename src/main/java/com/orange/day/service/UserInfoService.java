package com.orange.day.service;


import com.orange.day.dao.UserInfoDao;
import com.orange.day.model.UserInfo;
import com.orange.day.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


@Service
public class UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    public int saveUser(String userName, String userPhone, String userPassword) {

        userPassword = DigestUtils.md5DigestAsHex(userPassword.getBytes());
        return userInfoDao.saveUser(new UserInfo(snowflakeIdWorker.getId(), userName, userPhone, userPassword));

    }

    public UserInfo getUserById(long id) {
        return userInfoDao.getUserById(id);
    }

    public UserInfo getUserByPhone(String phone) {
        return userInfoDao.getUserByPhone(phone);
    }

}
