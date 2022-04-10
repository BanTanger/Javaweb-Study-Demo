package com.bantanger.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/10 12:19
 */
public class Testjdbc2 {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSl=true";
        String username = "root";
        String password = "123456";

        // 加载驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 连接数据库
        Connection connection = DriverManager.getConnection(url, username, password);

        // 编写SQL
        String sql = "insert into users(id, name, password, email, birthday) value (?,?,?,?,?)";

        // 预编译
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, 5); // 给第一个占位符？ 的值为1
        preparedStatement.setString(2, "半糖"); // 给第二个占位符？ 的值为半糖
        preparedStatement.setString(3, "123456"); // 给第三个占位符？ 的值为123456
        preparedStatement.setString(4, "bt@qq.com"); // 给第四个占位符？ 的值为邮箱号码
        preparedStatement.setDate(5, new Date(new java.util.Date().getTime())); // 给第五个占位符？ 的值为当前时间

        // 执行SQL
        int i = preparedStatement.executeUpdate();
        if(i > 0) {
            System.out.println("插入成功");
        }

        // 关闭资源，释放连接
        preparedStatement.close();
        connection.close();
    }
}
