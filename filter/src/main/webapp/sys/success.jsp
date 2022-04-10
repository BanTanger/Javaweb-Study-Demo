<%--
  Created by IntelliJ IDEA.
  User: 12902
  Date: 2022/4/9
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Object user_session = request.getSession().getAttribute("USER_SESSION");
    if(user_session == null){
        response.sendRedirect("/Login.jsp");
    }
%>

<h1>登录成功，尊贵的用户</h1>
<p><a href="/servlet/logout">注销</a></p>
</body>
</html>
