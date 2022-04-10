<%--
  Created by IntelliJ IDEA.
  User: 12902
  Date: 2022/4/8
  Time: 22:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--引入JSTL核心标签库--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h4>if测试</h4>
<hr>
<form action="coreif.jsp" method="get">
    <%--
    EL表达式获取表单的数据
    ${param.参数名}
    --%>
    <input type="text" name="username" value="${param.username}">
    <%--这里通过EL表达式获取表单本身的name，不可以直接填入username，而是需要通过param.name属性才可以--%>
    <input type="submit" value="登录">
</form>
<c:if test="${param.username=='admin'}" var="isAdmin">
    <c:out value="管理员欢迎您" />
</c:if>

<c:out value="${isAdmin}" />

</body>
</html>
