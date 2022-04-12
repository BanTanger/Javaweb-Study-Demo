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

    // 接口对象接收实例化对象
    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    /**
     * 业务层用户登录逻辑处理，
     * 通过JDBC传入的userCode和password用来查询数据库是否有对应的数据
     * 创建和断开数据库连接
     *
     * @param userCode 用户名
     * @param password 用户密码
     * @return 返回DAO层在数据库里查询到的用户信息
     */
    @Override
    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;

        try {
            // 创建数据库连接
            connection = BaseDAO.getConnection();

            // 通过业务层调用DAO层具体的数据库查询操作
            user = userDao.getLoginUser(connection, userCode, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 断开数据库连接
            BaseDAO.closeResource(connection, null, null);
        }
        return user;
    }

    /**
     * 业务层用户密码修改逻辑处理
     *
     * @param id 用户数据在数据库里的id，这里通过DAO层处理SQL查询的时候提前设置给了user
     * @param password 修改的密码
     * @return 返回是否修改成功
     */
    @Override
    public boolean updatePwd(int id, String password) {
        Connection connection = null; // 创建连接
        boolean flag = false;
        // 修改密码
        try {
            connection = BaseDAO.getConnection(); // 得到连接

            // 调用DAO层的对象方法进行查询，
            // 如果查询到的数据位置大于零，
            // 代表存在这行数据，并将flag设置成ture
            if (userDao.updatePwd(connection, id, password) > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDAO.closeResource(connection, null, null);
        }
        return flag;
    }

    @Test
    public void test() {
        UserServiceImpl userService = new UserServiceImpl();
        User admin = userService.login("admin", "123456");
        System.out.println("用户admin的password是: " + admin.getUserPassword());
    }
}
