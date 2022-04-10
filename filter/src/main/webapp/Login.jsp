<%--
  Created by IntelliJ IDEA.
  User: 12902
  Date: 2022/4/9
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>登录</h1>
<%--写表单提交到后端处理--%>
<form action="/servlet/login" method="post">
    <input type="text" name="username">
    <input type="submit">
</form>
</body>
</html>
