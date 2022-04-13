package com.bantanger.service.user;

import com.bantanger.pojo.User;

import java.sql.Connection;
import java.util.List;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/11 19:33
 */
public interface UserService {

    // 用户登录
    public User login(String userCode, String password);

    // 修改密码
    public boolean updatePwd(int id, String password);

    // 查询用户
    public int getUserCount(String username, int userRole);

    //根据条件查询用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);

}
