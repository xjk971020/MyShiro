<!-- jquery dataTable插件 -->
$(document).ready(function() {
    $('#role_info_table').DataTable( {
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
            url: "../role/all",
            type: "GET"
        },

        "columns": [
            { "data": "id" },
            { "data": "role" },
            { "data": "description" },
            { "data": "available" },
        ],
        'columnDefs': [ {
            'targets': 4,
            'data': null,
            'render': function(data, type, row) {

                var param = JSON.stringify(row);
                var html = "<a href='javascript:void(0);' onclick='findPermissions("+ param + ")' class='up btn btn-primary btn-xs'><i class='fa fa-pencil'></i>权限</a>";
                html += "&nbsp;<a href='javascript:void(0);' onclick='lockRole("+ param + ")'  class='down btn btn-info btn-xs'><i class='fa fa-trash'></i>状态</a>";
                html += "&nbsp;<a href='javascript:void(0);' onclick='editRole("+ param + ")'  class='down btn btn-success btn-xs'><i class='fa fa-trash'></i>编辑</a>";
                html += "&nbsp;<a href='javascript:void(0);' onclick='delRole("+ param + ")'  class='down btn btn-danger btn-xs'><i class='fa fa-trash'></i>删除</a>";
                return html;

            }
        }]
    } );
} );
//根据角色id找到该角色所拥有的权限
function findPermissions(param) {
    $(".info").text("");   //清除右边框的信息
    $("#permission-modal").modal("show");
    roleAndPermissionTree(param);   //生成一棵 角色-权限 zTree
    fullTextarea(param);   //将权限数据填充到模态框右边框中
}
//生成一棵 角色-权限 zTree
function roleAndPermissionTree(param) {
    var setting = {
        view: {
            selectedMulti: false, //设置是否能够同时选中多个节点
            showIcon: false,  //设置是否显示节点图标
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
            enable: false   //设置是否显示checkbox复选框
        }
    };
    $.ajax({
        type:"GET",
        url:"../role/permissiontree/" + param.id,
        dataType:"json",
        contentType:"application/json;charset=utf-8",
        success:function (data) {
            $.fn.zTree.init($("#tree"),setting,data);
        },
        error:function (data) {
            alert(JSON.stringify(data));
        }
    });
}
//将权限数据填充到模态框右边框中
function fullTextarea(param) {
    $.ajax({
        url : "../role/permission/" + param.id,
        type : "GET",
        dataType : "json",
        contentType : "application/json;charset=utf-8",
        success:function (data) {
            for(var i = 0; i < data.length; i++) {
                $(".title .info").append("id:" + data[i].id + "   描述: " +
                                            data[i].description + "   是否锁定: " + data[0].available + "&#10;");
            }
        }
    })
}
//状态信息模态框
function lockRole(param) {
    $("#status-modal .modal-body .id").val(param.id);
    if (param.available) {
        $(".unuse").prop("checked",true);
    } else {
        $(".use").prop("checked", true);
    }
    $("#status-modal").modal("show");
}
//状态信息更改按钮
$(document).on("click","#status-sure",function () {
    var id = $("#status-modal .modal-body .id").val();
    var available = 0;//0表示可用，1表示锁定
    if ($(".unuse").is(":checked")) {
        available = 1;
    }
    $.ajax({
        url : "../role/update",
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
//添加角色
function createRole() {
    $("#create-modal .modal-body .role").val('');
    $("#create-modal .modal-body .description").val('');

    $("#create-modal").modal("show");
    createModalTree();
}
//添加角色模态框中的zTree
function createModalTree() {
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
        }
    };
    $.ajax({
        type:"GET",
        url:"../role/roletree",
        dataType:"json",
        contentType:"application/json;charset=utf-8",
        success:function (data) {
            $.fn.zTree.init($("#tree-create"),setting,data);
            // checkNodes(param);
        },
        error:function (data) {
            alert(JSON.stringify(data));
        }
    });
}
//添加角色模态框中的添加按钮
$(document).on("click",".create-sure",function () {
    var role = $("#create-modal .modal-body .role").val();
    var description = $("#create-modal .modal-body .description").val();

    var available = 1;//0表示可用，1表示锁定
    if ($('#available').is(':checked')) {
        available = 0;
    }

    var zTree = $.fn.zTree.getZTreeObj("tree-create");//获取zTree对象
    let checkNodes = zTree.getCheckedNodes();
    var pid = 0;

    if (checkNodes.length > 0) {
        pid = checkNodes[checkNodes.length-1].id;
    }

    $.ajax({
        url:"../role/create",
        type:"POST",
        dataType:"json",
        contentType:"application/json;charset=utf-8",
        data:JSON.stringify({
           role : role,
           description : description,
           pid : pid,
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
    });
})
//编辑角色模态框
function editRole(param) {
    $("#edit-modal .modal-body .id").val(param.id);
    $("#edit-modal .modal-body .role").val(param.role);
    $("#edit-modal .modal-body .description").val(param.description);

    $("#edit-modal").modal("show");
}
//编辑角色提交按钮
$(document).on("click",".edit-sure",function () {
    var id  = $("#edit-modal .modal-body .id").val();
    var role = $("#edit-modal .modal-body .role").val();
    var description = $("#edit-modal .modal-body .description").val();

    // console.log("role = " + role +  ", description = " + description +  ", availble= " + available);

    $.ajax({
       url : "../role/update",
       type:"POST",
       dataType:"json",
       contentType:"application/json;charset=utf-8",
       data:JSON.stringify({
          id : id,
          role : role,
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
//删除角色
function delRole(param) {
    if (!confirm("确定要删除【" + param.description + "】吗?")) {
        return;
    }
    $.ajax({
       url : "../role/delete/" + param.id,
       type:"GET",
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