package com.bantanger.filter;

import com.bantanger.pojo.User;
import com.bantanger.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/12 14:32
 */
public class SysFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 需要强制类型转换，因为ServletRequest,HttpServlet不能使用父类HttpServletRequest方法
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 过滤器，从Session拿到用户
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);

        // 逻辑处理，被移除用户或者用户数据为空不能登陆，并重定向到错误页面
        if (user == null) {
            response.sendRedirect("/error.jsp");
        } else {
            // 如果用户存在chain链不工作拦截，如果用户为空才进行拦截
            filterChain.doFilter(servletRequest, servletResponse); // 防止程序堵塞
        }
    }

    @Override
    public void destroy() {

    }
}
