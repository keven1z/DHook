var HtmlUtil = {
    /*1.用浏览器内部转换器实现html转码*/
    htmlEncode:function (html){
        //1.首先动态创建一个容器标签元素，如DIV
        var temp = document.createElement ("div");
        //2.然后将要转换的字符串设置为这个元素的innerText(ie支持)或者textContent(火狐，google支持)
        (temp.textContent !== undefined ) ? (temp.textContent = html) : (temp.innerText = html);
        //3.最后返回这个元素的innerHTML，即得到经过HTML编码转换的字符串了
        var output = temp.innerHTML;
        temp = null;
        return output;
    },
    /*2.用浏览器内部转换器实现html解码*/
    htmlDecode:function (text){
        //1.首先动态创建一个容器标签元素，如DIV
        var temp = document.createElement("div");
        //2.然后将要转换的字符串设置为这个元素的innerHTML(ie，火狐，google都支持)
        temp.innerHTML = text;
        //3.最后返回这个元素的innerText(ie支持)或者textContent(火狐，google支持)，即得到经过HTML解码的字符串了。
        var output = temp.innerText || temp.textContent;
        temp = null;
        return output;
    }
};
function load_classMap() {
    var url = "/classmap/all?agentId=" + $("#small").text();
    $.ajax({
        //async:false,非异步，modal窗口失效；
        async: true,
        url: url,
        type: 'GET',
        dataType: 'json',
    }).done(function (data) {
        var defaultData = [];
        for (var i = 0, l = data.length; i < l; i++) {
            var className = data[i]["className"]
            defaultData[i] = {
                text: className,
                href: '#' + data[i]["packageName"],
                tags: [i]
            }
        }
        $('#treeview1').treeview({
            data: defaultData,
            collapseIcon: 'fas fa-minus',
            expandIcon: 'fas fa-plus'
        }).on('nodeSelected', function (event, data) {
            $("#classDetail").show();
            $("#all_class_info").hide();
            $('#class').text("Class " + data["text"]);
            $('#packageName').text(data["href"].substring(1).replaceAll("/", "."));
        });
    })
}

function get_class_info() {
    let url = "/classmap/class/target/add?className=" + $('#class').text().replace("Class ", "") + "&packageName=" + $('#packageName').text()
    $.ajax({
        //async:false,非异步，modal窗口失效；
        async: true,
        url: url,
        type: 'GET',
        dataType: 'json',
    }).done(function (data) {
        if (data === 0) {
            receive_info();
        } else {
            setTimeout(function () {
                receive_info();
            }, 2000);
        }

    })
}

function receive_info() {
    let url = "/classmap/class/seek?className=" + $('#class').text().replace("Class ", "") + "&packageName=" + $('#packageName').text()
    $.ajax({
        //async:false,非异步，modal窗口失效；
        async: true,
        url: url,
        type: 'GET',
        dataType: 'json',
    }).done(function (data) {
        $("#all_class_info").show();
        if (data === "") {
            return;
        }
        let super_class = data.superClass
        if (super_class == null){
            alert("未获取到类信息")
        }
        let interfaces = data.interfaces
        $("#super_class").text(super_class);
        $("#interfaces").text(interfaces);
        let fields = data.fields;
        let fieldBody = $("#field_body");
        fieldBody.empty();
        if (fields !== "" && fields != null) {
            let f = fields.split("#")
            for (let i = 0; i < f.length; i++) {
                let _ = f[i].split(" ");
                let desc = "<tr><td>" + HtmlUtil.htmlEncode(_[0]) + "</td><td>" + HtmlUtil.htmlEncode(_[1]) + "</td></tr>"
                fieldBody.append(desc);
            }
        }

        let methods = data.methods;
        let methodBody = $("#method_body");
        methodBody.empty();
        if (methods === "" || methods == null) return;
        let m = methods.split("#")
        for (let i = 0; i < m.length; i++) {
                let _ = m[i].split(" ");
            let desc = "<tr><td>" + HtmlUtil.htmlEncode(_[0])+ "</td><td>" + HtmlUtil.htmlEncode(_[1]) + "</td></tr>"
            methodBody.append(desc);
        }
    })
}
function get_code(){
    let url = "/classmap/code/get?className=" + $('#class').text().replace("Class ", "") + "&packageName=" + $('#packageName').text()
    $.ajax({
        //async:false,非异步，modal窗口失效；
        async: true,
        url: url,
        type: 'GET',
        dataType: 'json',
    }).done(function (data) {


    })
}