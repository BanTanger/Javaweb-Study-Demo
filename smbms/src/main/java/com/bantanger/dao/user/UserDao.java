package com.bantanger.dao.user;

import com.bantanger.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/11 16:17
 */
public interface UserDao {

    // 得到要登陆的用户
    public User getLoginUser(Connection connection, String userCode, String userPassword) throws SQLException;

    // 修改用户密码
    public int updatePwd(Connection connection, int id, String password) throws SQLException;
}
