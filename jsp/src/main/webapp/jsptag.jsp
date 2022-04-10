<%--
  Created by IntelliJ IDEA.
  User: 12902
  Date: 2022/4/8
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>你好</h1>
<%--jsp:include--%>
<jsp:forward page="/jsptag2.jsp">
    <jsp:param name="name" value="bantanger"></jsp:param>
    <jsp:param name="age" value="12"></jsp:param>
</jsp:forward>

</body>
</html>
