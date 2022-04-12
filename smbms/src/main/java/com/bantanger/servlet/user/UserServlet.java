package com.bantanger.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.bantanger.pojo.User;
import com.bantanger.service.user.UserService;
import com.bantanger.service.user.UserServiceImpl;
import com.bantanger.util.Constants;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/12 16:13
 */
public class UserServlet extends HttpServlet {
    // Servlet端代码复用
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method"); // /jsp/pwdmodify.jsp
        if (method != null && method.equals("savePwd")) { // 根据前端标签属性编写后端业务逻辑
            this.updatePwd(req, resp);
        } else if(method != null && method.equals("pwdmodify")){
            this.pwdModify(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    // 修改密码
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) {
        /*
            永远不要相信前端传来的数据，后端进行二次校验，避免数据有误，保证数据安全
         */
        // 从Session拿到ID，先不着急转型，先为原型类型Object，提高效率
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);

        // 从请求中拿到新密码
        String newpassword = req.getParameter("newpassword");

        // 设置布尔值查看是否修改成功
        boolean flag = false;

        // 后端数据二次校验，JDBC工具类检测数据是否为空
        if (o != null && !StringUtils.isNullOrEmpty(newpassword)) {
            // 调用业务层对象方法
            UserService userService = new UserServiceImpl();
            // 确定已经成功拿到用户数据，强制类型转换成User类，调用User类方法获得用户id
            flag = userService.updatePwd(((User) o).getId(), newpassword);

            // 逻辑处理，flag = true时显示密码修改成功，并且通过移除Session，让用户使用新密码重新登录
            if (flag) {
                req.setAttribute("message", "密码修改成功，请使用新密码重新登录");
                req.getSession().removeAttribute(Constants.USER_SESSION);
            } else {
                req.setAttribute("message", "密码修改失败");
            }
        } else {
            req.setAttribute("message", "两次密码输入不一致，或者新密码设置有问题");
        }

        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 验证旧密码
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp) {
        // 从Session中拿到用户旧密码数据
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");

        // 创建Map存放结果集
        Map<String, String> resultMap = new HashMap<>();

        // 检查Session是否过期
        if (o == null) { // Session失效，过期
            // 逻辑处理，如果过期，通知前端
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldpassword)) { // 如果输入的旧密码为空
            resultMap.put("result", "false");
        } else {
            String userPassword = ((User) o).getUserPassword(); // Session中的用户密码
            if (oldpassword.equals(userPassword)) {
                resultMap.put("result", "true");
            } else {
                resultMap.put("result", "false");
            }
        }

        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            // JSONArray 阿里巴巴的工具类，转换格式
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
