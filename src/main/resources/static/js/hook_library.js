$(function () {
    //1.初始化Table
    var oTable = new hookLibraryTable();
    oTable.Init();

});
var hookLibraryTable = function () {
    var ti = new Object();
    //初始化Table
    ti.Init = function () {
        $('#hl_departments').bootstrapTable({
            url: '/hook_library/query',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            dataType: 'json',
            toolbar: '#hl_toolbar',                //工具按钮用哪个容器
            theadClasses: '.thead-light',
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            showPaginationSwitch: false,        //是否显示分页数
            sortable: false,                     //是否启用排序
            sortName: "title",                     //是否启用排序
            sortOrder: "desc",                   //排序方式
            queryParams: ti.queryParams,//传递参数（*）
            queryParamsType: '',                //如果要在oTableInit.queryParams方法获取pageNumber和pageSize的值，需要将此值设置为空字符串（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            singleSelect: true,                 //是否单选模式
            height: $(window).height() - 200,   //table总高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "alias",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            paginationPreText: "上一页",
            paginationNextText: "下一页",
            onClickRow: function (row, $element) {
                console.log("click:" + row["id"])
            },
            columns: [
                {
                    checkbox: true,
                    align: 'center',
                    valign: 'middle',
                },{
                    field: 'id',
                    title: 'id'
                },
                {
                    field: 'alias',
                    title: '别名'
                },
                {
                    field: 'className',
                    title: 'className'
                },
                {
                    field: 'method',
                    title: 'method'
                },
                {
                    field: 'desc',
                    title: 'desc'
                },
                {
                    field: 'notes',
                    title: '注释'
                }]
        });
    };
    return ti;
};

function refresh_hl() {
    $('#hl_departments').bootstrapTable('refresh', {
        query: {
            pageNumber: 1
        }
    });
}
function getIdSelections() {
    return $.map($("#hl_departments").bootstrapTable('getSelections'), function (row) {
        return row.id
    })
}
function getSelections() {
    return $.map($("#hl_departments").bootstrapTable('getSelections'), function (row) {
        return row
    })
}
function getAliasSelections() {
    return $.map($("#hl_departments").bootstrapTable('getSelections'), function (row) {
        return row.alias
    })
}

function update_hl() {
    $.ajax({
        url: '/hook_library/update',
        type: 'GET',
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            setTimeout(function () {
                refresh();
            }, 200);
        },
        error: function (jqXHR, textStatus, errorThrown) {
        }
    });
}
function del_hl() {
    let id = getIdSelections()[0];
    if (id==null){
        alert("未选择hook点");
        return
    }
    $.ajax({
        url: '/hook_library/del?id='+id,
        type: 'GET',
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            setTimeout(function () {
                refresh_hl();
            }, 200);
        },
        error: function (jqXHR, textStatus, errorThrown) {
        }
    });
}

$(document).ready(function () {
    $("#hl_add").click(function () {
        $("#hl_className").val("");
        $("#hl_method").val("");
        $("#hl_desc").val("");
        $("#hl_alias").val("");
        $("#add_hl_button").unbind("click");
        $("#add_hl_button").text("添加");
        $('#add_hl_button').click(function () {
            let url = '/hook_library/add';
            $.ajax({
                //async:false,非异步，modal窗口失效；
                async: true,
                url: url,
                type: 'POST',
                data: JSON.stringify({
                    alias: $('#hl_alias').val(),
                    className: $('#hl_className').val(),
                    desc: $('#hl_desc').val(),
                    method: $('#hl_method').val(),
                    notes:$('#hl_notes').val()
                }),
                dataType: 'html',
                contentType: 'application/json',
            }).done(function (data) {
                refresh_hl();
                setTimeout(function () {
                    $('#add_hl_dialog').modal('hide');
                }, 200);
            })

        });
        $("#add_hl_dialog").modal();
    });

    //编辑hook点弹框
    $("#hl_edit").click(function () {
        var alias = getAliasSelections()[0];
        if (alias.length === 0) {
            alert("未选择hook");
            return
        }
        let selections = getSelections()[0];
        $("#add_hl_button").unbind("click");
        $("#add_hl_button").text("更新");
        $("#add_hl_button").click(function () {
            let url = '/hook_library/update';
            $.ajax({
                //async:false,非异步，modal窗口失效；
                async: true,
                url: url,
                type: 'POST',
                data: JSON.stringify({
                    id:getIdSelections()[0],
                    className: $('#hl_className').val(),
                    desc: $('#hl_desc').val(),
                    method: $('#hl_method').val(),
                    alias: $('#hl_alias').val(),
                    "notes": $('#hl_notes').val(),
                }),
                dataType: 'html',
                contentType: 'application/json',
            }).done(function (data) {
                refresh_hl();
                setTimeout(function () {
                    $('#add_hl_dialog').modal('hide');
                }, 200);
            })
        });
        $("#hl_alias").val(selections.alias);
        $("#hl_className").val(selections.className);
        $("#hl_method").val(selections.method);
        $("#hl_desc").val(selections.desc);
        $("#hl_notes").val(selections.notes);
        $("#add_hl_dialog").modal();
    });
})
