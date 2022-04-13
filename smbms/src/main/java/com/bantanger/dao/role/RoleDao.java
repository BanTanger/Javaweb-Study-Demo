package com.bantanger.dao.role;

import com.bantanger.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/13 20:57
 */
public interface RoleDao {

    // 职业查询
    public List<Role> getRoleList(Connection connection) throws SQLException;
}
