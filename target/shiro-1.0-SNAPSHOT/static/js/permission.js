<!-- jquery dataTable插件 -->
$(document).ready(function() {
    $('#permission_info_table').DataTable( {
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
            url: "../permission/all",
            type: "GET"
        },

        "columns": [
            { "data": "id" },
            { "data": "permission" },
            { "data": "description" },
            { "data": "available" },
        ],
        'columnDefs': [ {
            'targets': 4,
            'data': null,
            'render': function(data, type, row) {

                var param = JSON.stringify(row);
                var html = "<a href='javascript:void(0);' onclick='correlationRoleAndPermission("+ param + ")' class='up btn btn-primary btn-xs'><i class='fa fa-pencil'></i>关联角色</a>";
                html += "&nbsp;<a href='javascript:void(0);' onclick='lockPermission("+ param + ")'  class='down btn btn-info btn-xs'><i class='fa fa-trash'></i>状态</a>";
                html += "&nbsp;<a href='javascript:void(0);' onclick='editPermission("+ param + ")'  class='down btn btn-success btn-xs'><i class='fa fa-trash'></i>编辑</a>";
                html += "&nbsp;<a href='javascript:void(0);' onclick='delPermission("+ param + ")'  class='down btn btn-danger btn-xs'><i class='fa fa-trash'></i>删除</a>";
                return html;
            }
        }]
    } );
} );
//关联角色和权限模态框
function correlationRoleAndPermission(param) {
    $("#role-modal .modal-body .id").val(param.id);
    $("#role-modal").modal("show");
    correlationRoleTree(param);//构造模态框中关于角色的zTree
}
//权限关联角色 确定按钮
function surePermission() {
    var zTree = $.fn.zTree.getZTreeObj("tree");//获得zTree对象
    let checkNodes = zTree.getCheckedNodes();
    var ids = new Array(); //初始化当前权限角色id的集合，后台根据parent的值来判断当前id是否是父级（角色）
    var pids = new Array(); //初始化当前权限角色的pid集合
    var parents = new Array(); //初始化当前权限角色parent值的集合
    // console.log($("#role-modal .modal-body .id").val());
    //遍历所有已选中节点，并将相应的值赋给初始化的参数
    checkNodes.forEach(row =>{
        ids.push(row.id);
        pids.push(row.pid);
        parents.push(row.parent);
    });
    $.ajax({
        url : "../permission/correlation" ,
        type: "POST",
        dataType:"json",
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify({
            id : $("#role-modal .modal-body .id").val(),
            ids : ids,
            pids : pids,
            parents : parents,
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
//通过权限id查找该权限已拥有的角色
function findRolesByPermissionId(param) {
    var permissionId = param.id;
    var roles = [];  //初始化已有的角色数据
    $.ajax({
        url : "../permission/roles/" + permissionId,
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
//勾选权限已有的角色节点
function checkNodes(param) {
    var zTree = $.fn.zTree.getZTreeObj("tree");//获取zTree对象
    var roles = findRolesByPermissionId(param);
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
    //展示默认选中的数据
    checkedNodes.forEach(row => {
        $('.title .info').append('id:' + row.id + ', name:' + row.name + ', pid:' + row.pid + ', parent:' + row.parent + "&#10;");
    });
    //将当前操作用户的id作为模态框中的一个属性值
    $("#role-modal .modal-body .id").val(param.id);
}
//关联角色和权限模态框中的zTree
function correlationRoleTree(param) {
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
            checkNodes(param); //勾选权限已有的角色节点
        },
        error:function (data) {
            alert(JSON.stringify(data));
        }
    });
}
//定义节点复选框选中或取消选中事件的回调函数
function zTreeonCheck(event,treeId,treeNode) {
    $(".title .info").text(""); //清空右边框中的数据
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
//添加权限 模态框
function createPermission() {
    var treeName = "#tree-create";
    createRoleTree(treeName);//生成一颗角色树
    $("#create-modal").modal("show");
}
//添加权限 提交按钮
$(document).on("click",".create-sure",function () {
    var permission = $('#create-modal .modal-body .permission').val();
    var description = $('#create-modal .modal-body .description').val();
    var available = 0;//0表示可用，1表示锁定
    if ($(".unavailable").is(":checked")) {
        available = 1;
    }
    var zTree = $.fn.zTree.getZTreeObj("tree-create");//获取zTree对象
    let checkedNodes = zTree.getCheckedNodes();
    var rid = 0; //如果没有勾选角色，默认是0
    if (checkedNodes.length > 0){
        //checkedNodes是被勾选数据的集合数组，且是被如果有父节点被选中，那么数组中这个父节点一定在其被选子节点的后面，取数组最后位置得索引即可得到最底层节点的数据
        rid = checkedNodes[checkedNodes.length - 1].id
    }
    console.log(permission);
    console.log(description);
    console.log(rid);
    console.log(available);
    //创建用户
    $.ajax({
        url: '../permission/create',
        type: 'post',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify({
            permission: permission,
            description: description,
            rid: rid,
            available: available
        }),
        success: function (result) {
            if (result.code == 200) {
                alert(result.message);
                window.location.reload();
            } else {
                alert(JSON.stringify(result));
            }
        },
        error: function (result) {
            alert(JSON.stringify(result));
        }
    })

})
//生成一颗 角色 zTree
function createRoleTree(treeName) {
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
    };
    $.ajax({
        type:"GET",
        url:"../user/roletree",
        dataType:"json",
        contentType:"application/json;charset=utf-8",
        success:function (data) {
            $.fn.zTree.init($(treeName),setting,data);
        },
        error:function (data) {
            alert(JSON.stringify(data));
        }
    });
}
//权限状态 模态框
function lockPermission(param) {
    $("#status-modal .modal-body .id").val(param.id);
    if (param.available == true) {
        $(".unuse").prop("checked",true);
    } else {
        $(".use").prop("checked",true);
    }
    $("#status-modal").modal("show");
}
//权限状态 提交按钮
$(document).on("click",".status-sure",function () {
    var available = 0;//0表示可用,1表示不可用
    if ($(".unuse").is(":checked")) {
        available = 1;
    }
    var id = $("#status-modal .modal-body .id").val();

    $.ajax({
        url : "../permission/update",
        type:"POST",
        dataType:"json",
        contentType:"application/json;charset=utf-8",
        data:JSON.stringify({
            id : id,
            available : available
        }),
        success: function (result) {
            if (result.code == 200) {
                alert(result.message);
                window.location.reload();
            } else {
                alert(JSON.stringify(result));
            }
        },
        error: function (result) {
            alert(JSON.stringify(result));
        }
    });
})
//编辑权限模态框
function editPermission(param) {
    $("#edit-modal .modal-body .id").val(param.id);
    $("#edit-modal .modal-body .permission").val(param.permission);
    $("#edit-modal .modal-body .description").val(param.description);
    $("#edit-modal").modal("show");
}
//编辑权限 提交按钮
$(document).on("click",".edit-sure",function () {
    var id = $("#edit-modal .modal-body .id").val();
    var permission = $("#edit-modal .modal-body .permission").val();
    var description = $("#edit-modal .modal-body .description").val();

    $.ajax({
        url : "../permission/update",
        type:"POST",
        dataType:"json",
        contentType:"application/json;charset=utf-8",
        data:JSON.stringify({
            id : id,
            permission : permission,
            description : description
        }),
        success: function (result) {
            if (result.code == 200) {
                alert(result.message);
                window.location.reload();
            } else {
                alert(JSON.stringify(result));
            }
        },
        error: function (result) {
            alert(JSON.stringify(result));
        }
    });
})
//删除权限
function delPermission(param) {
    if (!confirm("确定要删除权限【" + param.description + "】吗?")) {
        return;
    }
    $.ajax({
        url : "../permission/delete/" + param.id,
        type:"DELETE",
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
    });
}