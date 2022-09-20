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
        let interfaces = data.interfaces
        $("#super_class").text(super_class);
        $("#interfaces").text(interfaces);
        let fields = data.fields;
        if (fields !== "") {
            let f = fields.split("#")
            let fieldBody = $("#field_body");
            fieldBody.empty();
            for (let i = 0; i < f.length; i++) {
                let _ = f[i].split(" ");
                let desc = "<tr><td>" + _[0] + "</td><td>" + _[1] + "</td></tr>"
                fieldBody.append(desc);
            }
        }

        let methods = data.methods;
        if (methods === "") return;
        let m = methods.split("#")
        let methodBody = $("#method_body");
        methodBody.empty();
        for (let i = 0; i < m.length; i++) {
            let _ = m[i].split(" ");
            let desc = "<tr><td>" + _[0] + "</td><td>" + _[1] + "</td></tr>"
            methodBody.append(desc);
        }
    })
}