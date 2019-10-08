package com.orange.day.dao;


import com.orange.day.model.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserInfoDao {


    @Select("SELECT uuid,user_name,user_phone,user_password FROM user_info WHERE uuid=#{id}")
    public UserInfo getUserById(long id);


    @Select("SELECT uuid,user_name,user_phone,user_password FROM user_info WHERE user_phone=#{phone}")
    public UserInfo getUserByPhone(String phone);


    @Insert("INSERT INTO user_info (uuid,user_name,user_phone,user_password) VALUES (#{uuid},#{userName},#{userPhone},#{userPassword})")
    public int saveUser(UserInfo user);

}
