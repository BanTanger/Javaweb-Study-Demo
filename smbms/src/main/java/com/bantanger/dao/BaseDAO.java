package com.bantanger.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/10 16:00
 */
public class BaseDAO {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    // 静态代码块处理配置信息，在类加载时期就能生成。
    static {
        // 得到配置类对象
        Properties properties = new Properties();
        // 通过反射得到配置类，并且将其转换成输入流
        InputStream is = BaseDAO.class.getClassLoader().getResourceAsStream("db.properties");

        try {
            // 尝试加载配置
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获取所写配置类的信息
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    // 获取数据库的连接
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // 通过反射得到数据库的配置
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    // 编写查询公共类
    public static ResultSet execute(Connection connection, String sql, Object[] params, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        // 得到SQL预处理对象，防止SQL注入情况
        preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            // setObject,占位符从1开始，数组下标从零开始，所以需要让i + 1
            preparedStatement.setObject(i + 1, params[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    // 编写增删改公共类
    public static int execute(Connection connection, String sql, Object[] params, PreparedStatement preparedStatement) throws SQLException {
        // 得到SQL预处理对象，防止SQL注入情况
        preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            // setObject,占位符从1开始，但数组下标从零开始，所以需要让i + 1
            preparedStatement.setObject(i + 1, params[i]);
        }

        int executeUpdate = preparedStatement.executeUpdate();
        return executeUpdate; // 返回修改的行数
    }

    // 关闭连接，释放资源
    public static boolean closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        boolean flag = true;
        // 遵循先进后出的关闭原则
        if (resultSet != null) {
            try {
                resultSet.close();
                // 如果resultSet还存在，启动GC回收
                resultSet = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false; // 如果关闭失败，错误
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                // 如果resultSet还存在，启动GC回收
                preparedStatement = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false; // 如果关闭失败，错误
            }
        }
        if (connection != null) {
            try {
                connection.close();
                // 如果resultSet还存在，启动GC回收
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false; // 如果关闭失败，错误
            }
        }
        return flag;
    }
}
