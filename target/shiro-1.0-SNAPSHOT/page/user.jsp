<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/2/16
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>用户列表</title>
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/lib/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/bootstrap.min.js"></script>
    <!-- DataTables -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/datatables.net-bs/css/dataTables.bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/lib/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <!-- 引入zTree -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/css/demo.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/css/metroStyle/metroStyle.css">
    <script src="${pageContext.request.contextPath}/static/lib/jquery.ztree.core.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/jquery.ztree.excheck.min.js"></script>
    <!-- 引入user.js文件 -->
    <script src="${pageContext.request.contextPath}/static/js/user.js"></script>
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
    </style>
</head>

<body>
    <!-- 导航条 -->
    <div class="navbar visible-lg visible-md" role="navigation" id="menu-nav">
        <div class="container">
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li>
                        <shiro:guest>
                            <a class="navfont">欢迎游客访问</a>
                        </shiro:guest>
                        <shiro:user>
                        <a class="navfont"> 欢迎【<shiro:principal/>】登录</a></li>
                    <li><a href="../index.jsp" class="navfont">首页</a></li>
                    <li class="active"><a href="user.jsp" class="navfont">用户列表</a></li>
                    <li><a href="role.jsp" class="navfont">角色列表</a></li>
                    <li><a href="permission.jsp" class="navfont">权限列表</a></li>
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
    <br/>
    <shiro:user >
        <!-- 添加用户按钮 -->
        <div class="col-md-1 col-md-offset-1">
            <input type="button" value="添加用户" class="btn btn-primary" name="create-user-btn" onclick="createUser()">
        </div>
        <br/><br/>
        <!-- user数据表格 -->
        <div class="col-md-10 col-md-offset-1">
            <table class="table table-striped table-hover" id="user_info_table">
                <thead>
                <tr class="info">
                    <th>ID</th>
                    <th>用户名</th>
                    <th>角色列表</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>
        <!-- 状态 模态框 -->
        <div class="modal fade" id="status-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">状态信息</h4>
                    </div>
                    <div class="modal-body">
                        <input hidden="hidden" class="id"  value=""/>
                        <input hidden="hidden" class="username" value=""/>
                        <input hidden="hidden" class="locked" value=""/>
                        <div class="radio">
                            <label>
                                <input type="radio" name="statusOption" class="use">启用
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="statusOption" class="unuse">锁定
                            </label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary status-sure"id="status-sure">提交更改</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 添加用户 模态框 -->
        <div class="modal fade" id="create-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">添加用户</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">用户名</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control username" name="userName">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">密码</label>
                                <div class="col-sm-5">
                                    <input type="password" class="form-control password" name="password">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary create-sure">添加</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 编辑用户 模态框 -->
        <div class="modal fade" id="edit-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">编辑用户</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <input class="id" name="id" value="" hidden="hidden">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">用户名</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control username" name="userName" id="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">密码</label>
                                <div class="col-sm-5">
                                    <input type="password" class="form-control password" name="password">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary edit-sure">提交更改</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 用户角色 模态框 -->
        <div class="modal fade" id="role-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">角色信息</h4>
                    </div>
                    <div class="modal-body">
                        <!-- 用来存放当前操作用户的id值 -->
                        <input hidden="hidden" class="id" value=""/>
                        <!-- 角色-用户的Tree树 -左侧 -->
                        <div class="zTreeDemoBackground" style="float: left;">
                            <ul id="tree" class="ztree"></ul>
                        </div>
                        <!-- 选中节点信息展示 -右侧 -->
                        <div style="float: right;width: 55%;margin-top: -2%;">
                            <li class="title">
                                <h2 style="margin-left: 25%;">&rarr; 选中节点信息展示 &larr;</h2>
                                <textarea class="info" style="margin: 0px;width: 320px;height: 257px;word-wrap: normal;"></textarea>
                            </li>
                        </div>
                    </div>
                    <div class="modal-footer" style="clear: both;">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="correlationUserAndRoles()">提交更改</button>
                    </div>
                </div>
            </div>
        </div>
    </shiro:user>
</body>
</html>
