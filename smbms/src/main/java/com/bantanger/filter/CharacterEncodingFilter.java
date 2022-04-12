package com.bantanger.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author bantanger 半糖
 * @version 1.0
 * @Date 2022/4/11 15:19
 */
public class CharacterEncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        // 设置字符编码
        servletRequest.setCharacterEncoding("utf8");
        servletResponse.setCharacterEncoding("utf8");
        servletResponse.setContentType("text/html;charset=utf-8");

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
