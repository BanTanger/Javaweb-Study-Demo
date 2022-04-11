package com.bantanger.servlet.user;

import com.bantanger.pojo.User;
import com.bantanger.service.user.UserService;
import com.bantanger.service.user.UserServiceImpl;
import com.bantanger.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/11 20:25
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet--start--");

        // 获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        // 和数据库中的密码做对比，调用业务层
        UserService service = new UserServiceImpl();
        User user = service.login(userCode, userPassword);

        // 逻辑处理: 查有此人，可以登陆，将用户信息放到Session中，跳转内部主页（重定向）
        //          查无此人，转发回去，并且发送error错误信息{用户名或密码不正确}
        if(user != null) {
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            resp.sendRedirect("jsp/frame.jsp");
        } else {
            // 前端有业务逻辑来捕获error错误，后台直接设置错误内容就行
            req.setAttribute("error","用户名或者密码不正确"); // 不要调用Session
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
