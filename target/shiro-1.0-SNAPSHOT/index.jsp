<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/2/13
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>首页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/bootstrap.min.css">
    <style type="text/css">
        /*重写导航栏颜色*/
        .navbar{
            margin-bottom: 0px;
            border-radius: 5px;
            background: #0e9aff;
        }
        .navfont{
            color: #ffffff;
        }
        #content{
            align: center;
            margin-left: 23%;
            margin-right: 17%;
            margin-top: 10%;
        }
        .jumbotron{
            height: 500px;
        }
    </style>
</head>
<body>
<!-- 导航条 -->
<div class="navbar  visible-lg visible-md" role="navigation" id="menu-nav">
    <div class="container">
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li>
                    <shiro:guest>
                        <a class="navfont">欢迎游客访问</a>
                    </shiro:guest>
                    <shiro:user>
                        <a class="navfont"> 欢迎【<shiro:principal/>】登录</a></li>
                        <li class="active"><a href="index.jsp" class="navfont">首页</a></li>
                        <li><a href="page/user.jsp" class="navfont">用户列表</a></li>
                        <li><a href="page/role.jsp" class="navfont">角色列表</a></li>
                        <li><a href="page/permission.jsp" class="navfont">权限列表</a></li>
            </ul>
            </shiro:user>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <shiro:guest>
                       <a href="${pageContext.request.contextPath}/login.jsp" class="navfont">点击登录</a>
                    </shiro:guest>
                    <shiro:user>
                       <a href="${pageContext.request.contextPath}/logout" class="navfont">点击退出</a>
                    </shiro:user>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="jumbotron">
    <div id="content">
        <h2>spring + spirngMVC + Mybatis + shiro 整合案例</h2>
        <br/>
        <!-- 连接到GitHub -->
        <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a></p>
    </div>
</div>

</body>
<script type="text/javascript" src="static/lib/jquery-3.3.1.min.js"/>
<script type="text/javascript" src="static/lib/bootstrap.min.js"/>
</html>
