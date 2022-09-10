$(function () {
    //1.初始化Table
    var oTable = new pluginTable();
    oTable.Init();

});
var pluginTable = function () {
    var ti = new Object();
    //初始化Table
    ti.Init = function () {
        $('#plugin_departments').bootstrapTable({
            url: '/plugin/find?agentId=' + $("small").text(),         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            dataType: 'json',
            toolbar: '#plugin_toolbar',                //工具按钮用哪个容器
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
            uniqueId: "pluginName",                     //每一行的唯一标识，一般为主键列
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
                }, {
                    field: 'fileName',
                    title: '文件名'
                },
                {
                    field: 'pluginName',
                    title: '插件名'
                },
                {
                    field: 'desc',
                    title: '描述'
                }]
        });
    };
    // oTableInit.queryParams = function (params) {
    //     // alert(JSON.stringify(params));
    //     var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    //         id: $("#small").text(),
    //         // statu: $("#txt_search_statu").val()
    //     };
    //     return temp;
    // };
    return ti;
};

function refresh() {
    $('#plugin_departments').bootstrapTable('refresh', {
        query: {
            pageNumber: 1
        }
    });
}

function getFileNameSelections() {
    return $.map($("#plugin_departments").bootstrapTable('getSelections'), function (row) {
        return row.fileName
    })
}
function get_all_plugin() {
    return $('#plugin_departments').bootstrapTable('getData',{useCurrentPage:false,includeHiddenRows:false});
}
function unload_plugin() {
    $.ajax({
        url: '/plugin/unload?agentId=' + $("small").text() + '&fileName=' + getFileNameSelections()[0],
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

