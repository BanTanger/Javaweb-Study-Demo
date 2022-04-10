package com.bantanger.jdbc;

import java.sql.*;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/10 11:49
 */
public class Testjdbc {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSl=true";
        String username = "root";
        String password = "123456";

        // 1.通过反射加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 2.连接数据库,connection代表数据库
        Connection connection = DriverManager.getConnection(url, username, password);

        // 3.向数据库发送SQL对象Statement : CRUD
        Statement statement = connection.createStatement();

        // 4.编写SQL
        String sql = "select * from users";

        // 5.执行查询SQL，返回一个ResultSet ：结果集
        ResultSet rs = statement.executeQuery(sql);

        // ResultSet的数据结构有点像链表，因此使用.next()方法拿到所有的值
        while(rs.next()){
            System.out.println("id=" + rs.getObject("id"));
            System.out.println("name=" + rs.getObject("name"));
            System.out.println("password=" + rs.getObject("password"));
            System.out.println("email=" + rs.getObject("email"));
            System.out.println("birthday=" + rs.getObject("birthday"));
        }

        // 6.关闭连接，释放资源 ， 先开后关
        rs.close();
        statement.close();
        connection.close();
    }
}
