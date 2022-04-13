package com.bantanger.dao.user;

import com.bantanger.pojo.Role;
import com.bantanger.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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

    // 查询用户总数
    public int getUserCount(Connection connection,String username,int userRole) throws SQLException;

    // 通过条件查询-userList
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize)throws Exception;
}
