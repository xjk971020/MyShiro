<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/2/13
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>error</title>
</head>
<body>
<div style="width:500px;border:1px solid lightgray;margin:200px auto;padding:80px">
    系统出现了异常，异常原因是：
    ${exception}
    <br><br>
    出现异常的地址是：
    ${url}
</div>
</body>
</html>
