package com.bantanger.service.role;

import com.bantanger.dao.BaseDAO;
import com.bantanger.dao.role.RoleDao;
import com.bantanger.dao.role.RoleDaoImpl;
import com.bantanger.pojo.Role;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/13 21:03
 */
public class RoleServiceImpl implements RoleService {

    // 引入DAO层
    private RoleDao roleDao;

    // 接口化编程，创建接口对象，接口对象变量通过构造器接收实现类对象
    public RoleServiceImpl() {
        roleDao = new RoleDaoImpl();
    }

    @Override
    public List<Role> getRoleList() {
        Connection connection = null;
        List<Role> roleList = null;

        try {
            connection = BaseDAO.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDAO.closeResource(connection, null, null);
        }
        return roleList;
    }

    @Test
    public void test(){
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        for (Role role : roleList) {
            System.out.print(role.getRoleName() + " ");
        }
    }
}
