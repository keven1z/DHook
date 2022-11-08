$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

});

// home页面列表 start
var TableInit = function () {
    var oTableInit = {};
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_departments').bootstrapTable({
            url: '/hook/find',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            dataType: 'json',
            toolbar: '#toolbar',                //工具按钮用哪个容器
            theadClasses: '.thead-light',
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            showPaginationSwitch: false,        //是否显示分页数
            sortable: false,                     //是否启用排序
            sortName: "title",                     //是否启用排序
            sortOrder: "desc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
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
            height: $(window).height() - 220,   //table总高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            paginationPreText: "上一页",
            paginationNextText: "下一页",
            onClickRow: function (row, $element) {
                // console.log("click:" + row["id"])
            },
            columns: [
                {
                    checkbox: true,
                    align: 'center',
                    valign: 'middle'
                }, {
                    field: 'id',
                    title: 'id'
                }, {
                    field: 'className',
                    title: '类名'
                }, {
                    field: 'method',
                    title: '方法'
                }, {
                    field: 'desc',
                    title: '描述符'
                }, {
                    title: '功能',
                    // align: 'center',
                    // valign: 'middle',
                    formatter: function (value, row, index) {
                        return "<a class=\"btn btn-primary\" href='/hookDetail?hookId=" + row.id + "' >详情</a>";
                    }
                }]
        });
    };
    oTableInit.queryParams = function (params) {
        // alert(JSON.stringify(params));
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            id: $("#small").text(),
            // statu: $("#txt_search_statu").val()
        };
        return temp;
    };
    return oTableInit;
};

function refresh_hook() {
    $('#tb_departments').bootstrapTable('refresh', {
        query: {
            pageNumber: 1
        }
    });
}

function get_hook_id() {
    return $.map($("#tb_departments").bootstrapTable('getSelections'), function (row) {
        return row.id
    })
}

function del_hook() {
    let id = get_hook_id();
    if (id == null) {
        alert("未选择删除的hook点");
        return;
    }
    $.ajax({
        url: "/hook/del?hookId=" + id,
        type: "get",
        success: function (data) {
            refresh_hook();
        },
        error: function () {
        }
    });
}

function update_hook(hook_id) {
    let url = '/hook/update';
    let methodFields = getMethodFields(hook_id);
    $.ajax({
        //async:false,非异步，modal窗口失效；
        async: true,
        url: url,
        type: 'POST',
        data: JSON.stringify({
            agentId: $("small").text(),
            id: hook_id,
            className: $('#className').val(),
            desc: $('#desc').val(),
            method: $('#method').val(),
            returnValue: $('#returnValue').val(),
            parameters:$('#parameter_pos').val()+","+$('#parameter_value').val(),
            "methodEntities": methodFields.methods,
            "fieldEntities": methodFields.fields

        }),
        dataType: 'html',
        contentType: 'application/json',
    }).done(function (data) {
        refresh_hook();
        setTimeout(function () {
            $('#add_hook_dialog').modal('hide');
        }, 200);
    })

}

$(document).ready(function () {
    $("#export-off-button").click(function () {
        let data = get_all_plugin();
        $("#plugin_select").empty()
        for (let datum of data) {
            let filename = datum.fileName

            $("#plugin_select").append('<option value="' + filename + '">' + filename + '</option>');
        }
        $("#export_agent_dialog").modal();

        $('#export_plugin_button').click(function () {
            $('#export_agent_dialog').modal('hide');
            $("#agent_form").submit();
        });
    });
});

