package com.ly.a316.ly_meetingroommanagement.login.services;

/*
Date:2018/12/28
Time:15:14
auther:xwd
*/
public interface UserService {
    //验证用户
    public void loginValidate(String username,String password,String loginType);

}
