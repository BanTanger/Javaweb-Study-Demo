<%@ page import="com.bantanger.pojo.People" %><%--
  Created by IntelliJ IDEA.
  User: 12902
  Date: 2022/4/9
  Time: 21:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    People people = new People();
    people.setId(114514);
    people.setName("半糖");
    people.setAge(45);
    people.setAddress("翻斗公园二号三巷");
%>

<%--<jsp:useBean id="people" class="com.bantanger.pojo.People" scope="page"></jsp:useBean>--%>
姓名：<jsp:getProperty name="people" property="name"/>
编号：<jsp:getProperty name="people" property="id"/>
年龄：<jsp:getProperty name="people" property="age"/>
地址：<jsp:getProperty name="people" property="address"/>

</body>
</html>
