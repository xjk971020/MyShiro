<!-- jquery dataTable插件 -->
$(document).ready(function() {
    $('#user_info_table').DataTable( {
        'paging'      : true,
        'lengthChange': true,
        'searching'   : true,
        'ordering'    : false,
        'info'        : true,
        'autoWidth'   : false,
        "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "全部"]],
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项搜索结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 条信息",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "未搜索到数据",
            "sLoadingRecords": "没有数据.",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        ajax: {
            url: "../user/all",
            type: "GET"
        },

        "columns": [
            { "data": "id" },
            { "data": "userName" },
            { "data": "roleId" },
            { "data": "locked" },
        ],
        'columnDefs': [ {
            'targets': 4,
            'data': null,
            'render': function(data, type, row) {

                var param = JSON.stringify(row);
                var html = "<a href='javascript:void(0);' onclick='findRoles("+ param + ")' class='up btn btn-primary btn-xs'><i class='fa fa-pencil'></i>角色</a>";
                html += "&nbsp;<a href='javascript:void(0);' onclick='lockUser("+ param + ")'  class='down btn btn-info btn-xs'><i class='fa fa-trash'></i>状态</a>";
                html += "&nbsp;<a href='javascript:void(0);' onclick='editUser("+ param + ")'  class='down btn btn-success btn-xs'><i class='fa fa-trash'></i>编辑</a>";
                html += "&nbsp;<a href='javascript:void(0);' onclick='delUser("+ param + ")'  class='down btn btn-danger btn-xs'><i class='fa fa-trash'></i>删除</a>";
                return html;

            }
        }]
    } );
} );

//状态按钮弹出模态框
function lockUser(param) {
    $('#status-modal').modal("show");
    if (param.locked == true) {
        $('.unuse').prop("checked", true);
    } else {
        $('.use').prop("checked", true);
    }
    $("#status-modal .id").text(param.id);
    $("#status-modal .username").text(param.userName);
    $("#status-modal .locked").text(param.locked);
}
//状态模态框提交按钮
$(document).on("click","#status-sure",function() {
    $("#status-modal").modal("hide");
    var locked = 0; //0表示启用，1表示锁定
    if ($('.unuse').is(':checked')) {
        locked = 1;
    }
    $.ajax({
        url : "../user/update",
        type: "POST",
        dataType: "json",
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify({
            id: $("#status-modal .id").text(),
            userName: $("#status-modal .username").text(),
            locked: locked
        }),
        success: function (result) {
            if (result.code == 200) {
                alert(result.message);
                window.location.reload();
            } else {
                alert(JSON.stringify(result.message));
            }
        },
        error: function (result) {
            alert(JSON.stringify(result));
        }
    })
})

//添加用户
function createUser() {
    $("#create-modal").modal("show");
    $('#create-modal .modal-body .username').val('');
    $('#create-modal .modal-body .password').val('');
}

//添加用户确定按钮
$(document).on("click",".create-sure",function () {
    var username = $("#create-modal .modal-body .username").val();
    var password = $("#create-modal .modal-body .password").val();

    $.ajax({
       url:"../user/create",
       type:"POST",
       dataType: 'json',
       contentType: 'application/json;charset=utf-8',
       data:JSON.stringify({
           userName : username,
           password : password
       }),
        success: function (result) {
            if (result.code == 200) {
                alert(result.message);
                window.location.reload();
            } else {
                alert(result.message);
            }
        },
        error: function (result) {
            alert(result.message);
        }
    });
});

//弹出编辑用户模态框
function editUser(param) {
    $('#edit-modal').modal("show");
    var id = param.id;
    var username = param.userName;
    var password = param.password;
    $("#edit-modal .modal-body .id").val(id);
    $("#edit-modal .modal-body .username").val(username);
    $("#edit-modal .modal-body .password").val(password);
}

//编辑用户提交按钮
$(document).on("click",".edit-sure",function () {
    var id = $("#edit-modal .modal-body .id").val();
    var username = $("#edit-modal .modal-body .username").val();
    var password = $("#edit-modal .modal-body .password").val();
    // console.log("username:" + username + "password:" + password);
    $.ajax({
       url : "../user/update",
       type:"POST",
       dataType:"json",
       contentType:"application/json;charset=utf-8",
       data:JSON.stringify({
           id : id,
           userName : username,
           password : password
       }),
        success: function (result) {
            if (result.code == 200) {
                alert(result.message);
                window.location.reload();
            } else {
                alert(result.message);
            }
        },
        error: function (result) {
            alert(JSON.stringify(result));
        }
    });
});

//删除用户
function delUser(param) {
    if (!confirm("确定要删除【" + param.userName  + "】吗")) {
        return;
    }
    var id = param.id;
    $.ajax({
        url : "../user/delete/" + id,
        type : "GET",
        dataType:"json",
        contentType:"application/json;charset=utf-8",
        success: function (result) {
            if (result.code == 200) {
                alert(result.message);
                window.location.reload();
            } else {
                alert(result.message);
            }
        },
        error: function (result) {
            alert(JSON.stringify(result));
        }
    })
}

//根据用户id查询该用户所拥有的角色
function findRoles(param) {
    //先清空右侧文本框中的信息
    $((".title .info")).text("");
    $("#role-modal").modal("show");
    $("#role-modal .modal-body .id").text(param.id);
    makeTree(param);
}
//user绑定role更改提交按钮
function correlationUserAndRoles() {
    var zTree = $.fn.zTree.getZTreeObj("tree");//获得zTree对象
    let checkNodes = zTree.getCheckedNodes();
    var ids = new Array(); //初始化当前用户角色id的集合，后台根据parent的值来判断当前id是否是父级（角色）
    var pids = new Array(); //初始化当前用户角色的pid集合
    var parents = new Array(); //初始化当前用户角色parent值的集合
    var names = new Array(); //初始化当前用户角色名称集合

    //遍历所有已选中节点，并将相应的值赋给初始化的参数
    checkNodes.forEach(row =>{
        ids.push(row.id);
        pids.push(row.pid);
        parents.push(row.parent);
        names.push(row.name);
    });
    // console.log($("#role-modal .modal-body .id").text());
    $.ajax({
       url : "../user/correlation",
       type: "POST",
       dataType:"json",
       contentType:"application/json;charset=utf-8",
       data: JSON.stringify({
           id : $("#role-modal .modal-body .id").text(),
           ids : ids,
           pids : pids,
           parents : parents,
           names : names
       }),
        success: function (result) {
            if (result.code == 200) {
                alert(result.message);
                window.location.reload();
            } else {
                alert(result.message);
            }
        },
        error: function (result) {
            alert(JSON.stringify(result));
        }
    });
}
//构建一棵role的zTree
function makeTree(param) {
    var setting = {
        view: {
            selectedMulti: false, //设置是否能够同时选中多个节点
            showIcon: true,  //设置是否显示节点图标
            showLine: true,  //设置是否显示节点与节点之间的连线
            showTitle: true,  //设置是否显示节点的title提示信息
        },
        data: {
            simpleData: {
                enable: true, //设置是否启用简单数据格式（zTree支持标准数据格式跟简单数据格式，上面例子中是标准数据格式）
                idKey: "id",  //设置启用简单数据格式时id对应的属性名称
                pIdKey: "pid", //设置启用简单数据格式时parentId对应的属性名称,ztree根据id及pid层级关系构建树结构
                rootPId: -1, //根节点ID
            }
        },
        check:{
            enable: true   //设置是否显示checkbox复选框
        },
        callback: {
            onCheck: zTreeonCheck    //定义节点复选框选中或取消选中事件的回调函数
        },
    };
    $.ajax({
        type:"GET",
        url:"../user/roletree",
        dataType:"json",
        contentType:"application/json;charset=utf-8",
        success:function (data) {
            $.fn.zTree.init($("#tree"),setting,data);
            checkNodes(param);
        },
        error:function (data) {
            alert(JSON.stringify(data));
        }
    });
}
//定义节点复选框选中或取消选中事件的回调函数
function zTreeonCheck(event,treeId,treeNode) {
    $('.title .info').text(''); //先清空textarea
    var zTree = $.fn.zTree.getZTreeObj("tree");//获取zTree对象
    let checkedNodes = zTree.getCheckedNodes();
    // console.log(checkedNodes);
    //复选框被选中 treeNode.checked = true
    if (treeNode.checked) {
        checkedNodes.forEach(row =>{
           $(".title .info").append('id:' + row.id + ', name:' + row.name + ', pid:' + row.pid + ', parent:' + row.parent + "&#10;");
        });
    } else {
        checkedNodes.forEach(row =>{
            $(".title .info").append('id:' + row.id + ', name:' + row.name + ', pid:' + row.pid + ', parent:' + row.parent + "&#10;");
        });
    }
}
//根据用户id查找该用户所拥有的角色
function findRolesByUserId(param) {
    var userId = param.id;
    var roles = [];
    $.ajax({
       url : "../user/roles/" + userId,
       type: "GET",
       dataType : "json",
       contentType:"application/json;charset=utf-8",
       async: false,
       success:function (data) {
           // console.log("role:" + JSON.stringify(data));
           roles = data;
       }
    });
    // console.log(roles);
    return roles;
}
//勾选user已有的roles的复选框
function checkNodes(param) {
    var zTree = $.fn.zTree.getZTreeObj("tree");//获取zTree对象
    var roles = findRolesByUserId(param);
    var roleNode = []; //初始化角色（父节点）被选中的列表
    roles.forEach(row => {
        roleNode.push(row.id);
    });
    roleNode.forEach(node_role => {
        //node_role role的id,用getNodeByParam()可以精准的找到子节点
        zTree.selectNode(zTree.getNodeByParam("id", node_role), false, true); //调用selectNode()方法，能够帮我们自动展开被选中子节点所在的父节点
        zTree.checkNode(zTree.getNodeByParam("id", node_role), true, true); //设置第三个参数是false表示选中父节点不主动联动勾选其下的子节点
    });
    let checkedNodes = zTree.getCheckedNodes();
    // console.log(checkedNodes);
    //展示默认选中的数据
    checkedNodes.forEach(row => {
        $('.title .info').append('id:' + row.id + ', name:' + row.name + ', pid:' + row.pid + ', parent:' + row.parent + "&#10;");
    });
    //将当前操作用户的id作为模态框中的一个属性值
    $("#role-modal .modal-body .id").val(param.id);
}