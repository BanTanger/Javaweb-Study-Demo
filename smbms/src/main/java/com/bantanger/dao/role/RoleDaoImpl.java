package com.bantanger.dao.role;

import com.bantanger.dao.BaseDAO;
import com.bantanger.pojo.Role;
import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/13 20:58
 */
public class RoleDaoImpl implements RoleDao {

    @Override
    public List<Role> getRoleList(Connection connection) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Role> list = new ArrayList<>();

        if (connection != null) {
            String sql = "select * from smbms_role";
            Object[] params = {};
            rs = BaseDAO.execute(connection, sql, params, pstm, rs);

            while (rs.next()) {
                Role _role = new Role();
                _role.setRoleName(rs.getString("roleName"));
                _role.setRoleCode(rs.getString("roleCode"));
                _role.setId(rs.getInt("id"));
                list.add(_role);
            }
            BaseDAO.closeResource(null, pstm, rs);
        }
        return list;
    }
}
