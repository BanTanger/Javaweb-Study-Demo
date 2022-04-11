package com.bantanger.service.user;

import com.bantanger.dao.user.UserDao;
import com.bantanger.dao.user.UserDaoImpl;
import com.bantanger.pojo.User;
import com.bantanger.dao.BaseDAO;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/11 19:35
 */
public class UserServiceImpl implements UserService {

    // 业务层都会调用DAO层，所以需要引入DAO层
    private UserDao userDao; // 创建DAO接口对象

    public UserServiceImpl() {
        userDao = new UserDaoImpl(); // 接口对象接收实例化对象
    }

    @Override
    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;

        try {
            connection = BaseDAO.getConnection(); // 获得数据库连接对象
            // 通过业务层调用DAO层具体的数据库查询操作
            user = userDao.getLoginUser(connection, userCode, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDAO.closeResource(connection, null, null);
        }
        return user;
    }

    @Test
    public void test() {
        UserServiceImpl userService = new UserServiceImpl();
        User admin = userService.login("admin", "123456");
        System.out.println("用户admin的password是: " + admin.getUserPassword());
    }
}
