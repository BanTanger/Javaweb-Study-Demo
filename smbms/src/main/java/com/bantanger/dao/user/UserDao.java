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
    // 接口编程
    // 方法: 得到登录用户，参数：连接对象 数据库查询用户依据userCode
    // 内容: SQL查询语句，预编译使用？占位
    public User getLoginUser(Connection connection, String userCode, String userPassword) throws SQLException;
}
