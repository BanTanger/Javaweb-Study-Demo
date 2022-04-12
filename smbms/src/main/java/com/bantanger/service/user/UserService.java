package com.bantanger.service.user;

import com.bantanger.pojo.User;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/11 19:33
 */
public interface UserService {

    // 用户登录
    public User login(String userCode, String password);

    // 根据用户ID修改密码
    public boolean updatePwd(int id, String password);
}
