package com.bantanger.dao.user;

import com.bantanger.pojo.User;
import com.bantanger.dao.BaseDAO;
import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/11 17:27
 */
public class UserDaoImpl implements UserDao {
    /**
     * 连接数据库，通过userCode参数查询用户信息，转交到service层的UserServiceImpl里
     * @param connection
     * @param userCode
     * @return 返回用户对象
     * @throws SQLException
     */
    @Override
    public User getLoginUser(Connection connection, String userCode, String userPassword) throws SQLException {
        // 三个参数创建
        PreparedStatement pstm = null; // 预处理对象参数
        ResultSet rs = null; // SQL对象参数
        User user = null; // 用户对象参数

        // 如果连接存在就执行SQL语句查询
        if (connection != null) {
            // SQL语句编写
            String sql = "SELECT * FROM smbms_user WHERE userCode=? and userPassword=?"; // 预处理手段，防止SQL注入
            Object[] param = new Object[]{userCode, userPassword}; // 使用param对象数组来接收传入的查询用户依据参数

            // 查询,调用工具类中的util/Connect方法
            rs = BaseDAO.execute(connection, sql, param, pstm, rs);

            // 拿取数据的逻辑处理
            if (rs.next()) { // 如果rs集合就取出
                user = new User();
                user.setId(rs.getInt("id")); // 取出SQL查询结果集合中的id，getId会返回Integer类型
                user.setUserCode(rs.getString("UserCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getDate("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getDate("modifyDate"));
            }
            // 关闭数据库
            BaseDAO.closeResource(null,pstm,rs); // connection放到业务层关闭
        }
        return user;
    }
}
