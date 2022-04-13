package com.bantanger.dao.user;

import com.bantanger.pojo.Role;
import com.bantanger.pojo.User;
import com.bantanger.dao.BaseDAO;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/11 17:27
 */
public class UserDaoImpl implements UserDao {

    /**
     * DAO层查询数据库验证用户合法性逻辑处理
     * 连接数据库，通过userCode参数查询用户信息，转交到service层的UserServiceImpl里
     *
     * @param connection   连接数据库的连接对象
     * @param userCode     用户名，查询数据库的依据
     * @param userPassword 密码，检验用户名和密码是否一致
     * @return 返回用户对象
     * @throws SQLException
     */
    @Override
    public User getLoginUser(Connection connection, String userCode, String userPassword) throws SQLException {
        // 接口编程
        // 方法: 得到登录用户，参数：连接对象 数据库查询用户依据userCode
        // 内容: SQL查询语句，预编译使用？占位

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
            BaseDAO.closeResource(null, pstm, rs); // connection放到业务层关闭
        }
        return user;
    }


    /**
     * DAO层通过数据库修改当前用户密码逻辑处理
     *
     * @param connection 连接数据库的连接对象
     * @param id         操作用户处于数据库的位置id
     * @param password   密码，用于更改
     * @return 返回要修改的数据位于数据库的哪一行，
     * 如果行数小于等于0就代表不存在这个数据
     * @throws SQLException
     */
    @Override
    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        // 执行对象（预处理对象）
        PreparedStatement pstm = null;

        // 逻辑处理，如果当前连接不为null才执行（衔接业务层，避免报错）
        int execute = 0; // 作用域层级往上提，不然在if里面的execute没法返回查询结果
        if (connection != null) {
            // 编写sql，对象数组存放传参数据（用于传参给BaseDAO对象方法）
            String sql = "update smbms_user set userPassword=? where id=?";
            Object[] params = {password, id}; // 这里顺序要跟占位符保持一致，否则逻辑出错
            execute = BaseDAO.execute(connection, sql, params, pstm);

            // 关闭数据库，但不断开连接
            BaseDAO.closeResource(null, pstm, null);
        }

        // 将查询的结果返回（作用域向上提一级，避免报错）
        return execute;
    }


    /**
     * DAO层联表查询数据库返回数据信息
     * 包括每种角色有多少人，总共有多少个人，某一个姓氏有多少人
     *
     * @param connection 连接数据库的对象，由业务层创建
     * @param username   用户名
     * @param userRole   用户角色
     * @return 返回人员数量
     * @throws SQLException
     */
    @Override
    public int getUserCount(Connection connection, String username, int userRole) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            ArrayList<Object> list = new ArrayList<>(); // 使用ArrayList存放数组
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole = r.id"); // 联表查询的sql语句

            if (!StringUtils.isNullOrEmpty(username)) {
                sql.append(" and u.username like ?");
                list.add("%" + username + "%"); // index:0
            }

            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole); // index:1
            }

            // 将list转换成原型数组
            Object[] params = list.toArray();

            System.out.println("UserDaoImpl-->getUserCount" + sql.toString());

            rs = BaseDAO.execute(connection, sql.toString(), params, pstm, rs);

            if (rs.next()) {
                count = rs.getInt("count");// 从结果集里获取最终的数量
            }
            BaseDAO.closeResource(null, pstm, rs);
        }
        return count;
    }


    @Override
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws Exception {
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            //在数据库中，分页显示 limit startIndex，pageSize；总数
            //当前页  (当前页-1)*页面大小
            //0,5	1,0	 01234
            //5,5	5,0	 56789
            //10,5	10,0 10~
            sql.append(" order by creationDate DESC limit ?,?"); // 分页查询的SQL语句
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());

            rs = BaseDAO.execute(connection, sql.toString(), params, pstm, rs);
            while (rs.next()) {
                User _user = new User();
                _user.setId(rs.getInt("id"));
                _user.setUserCode(rs.getString("userCode"));
                _user.setUserName(rs.getString("userName"));
                _user.setGender(rs.getInt("gender"));
                _user.setBirthday(rs.getDate("birthday"));
                _user.setPhone(rs.getString("phone"));
                _user.setUserRole(rs.getInt("userRole"));
                _user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(_user);
            }
            BaseDAO.closeResource(null, pstm, rs);
        }
        return userList;
    }
}
