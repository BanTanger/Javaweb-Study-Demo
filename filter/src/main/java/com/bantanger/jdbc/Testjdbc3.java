package com.bantanger.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/10 14:55
 */
public class Testjdbc3 {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSl=true";
        String username = "root";
        String password = "123456";

        Connection connection = null;

        try {
            // 加载驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 连接数据库
            connection = DriverManager.getConnection(url, username, password);

            // 通知数据库开启事务,关闭自动提交就是开启事务
            connection.setAutoCommit(false);

            String sql = "update account set money = money - 100 where name = 'A'";
            connection.prepareStatement(sql).executeUpdate();

            // 制造错误
            // int i = 1 / 0;

            String sql2 = "update account set money = money + 100 where name = 'B'";
            connection.prepareStatement(sql2).executeUpdate();

            connection.commit(); // 以上两句SQL都执行成功后就提交事务
            System.out.println("success");
        } catch (Exception e) {
            try {
                connection.rollback(); // 如果出现异常就让事务回滚
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
