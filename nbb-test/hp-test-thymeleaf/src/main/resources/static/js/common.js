(function ($, window, undefined) {
    $(function () {
        /* 添加/修改弹出层 */
        $(document).on("click", ".open-popup, .open-popup-param", function () {
            var title = $(this).data("title");
            var url = $(this).attr("data-url");
            if ($(this).hasClass("open-popup-param")) {
                var tdcheckbox = $(".timo-table td .timo-checkbox :checkbox:checked");
                var param = '';
                if (tdcheckbox.length === 0) {
                    layer.msg('请选择一条记录');
                    return;
                }

                if (tdcheckbox.length > 1 && $(this).data("type") === 'radio') {
                    layer.msg('只允许选中一个');
                    return;
                }
                tdcheckbox.each(function (key, val) {
                    param += "ids=" + $(val).attr("value") + "&";
                });
                param = param.substr(0, param.length - 1);
                url += "?" + param;
            }
            var size = $(this).attr("data-size");
            var maxmin = false;
            if (size === undefined || size === "auto") {
                size = ['50%', '80%'];
            } else if (size === "max") {
                size = ['100%', '100%'];
                maxmin = true
            } else if (size.indexOf(',') !== -1) {
                var split0 = size.split(",")[0];
                var split1 = size.split(",")[1];
                if (split0 == "auto") {
                    split0 = '50%'
                }
                if (split1 == "auto") {
                    split1 = '80%'
                }
                if (!isNaN(split0)) {
                    split0 = split0 + 'px';
                }
                if (!isNaN(split1)) {
                    split1 = split1 + 'px';
                }
                size = [split0, split1];
            }

            var index = js.openLayer({
                title: title,
                area: size,
                maxmin: maxmin,
                content: [js.ctxPath(url), 'on']
            })
        });

        //删除操作监听
        $(document).on("click", ".ajax-post, .ajax-post-param", function (e) {
            e.preventDefault();
            var title = $(this).data("title");
            var url = $(this).attr("data-url");
            var event = $(this).data("event");
            var trigger = $(this).data("trigger");
            var param = [];
            if ($(this).hasClass("ajax-post-param")) {
                var tdcheckbox = $(".timo-table td .timo-checkbox input:checked");

                if (tdcheckbox.length === 0) {
                    layer.msg('请选择一条记录');
                    return;
                }
                tdcheckbox.each(function (key, val) {
                    param[key] = $(val).attr("value");
                });
                if (param.length > 0) {
                    param = {"ids[]": param};
                } else {
                    param = null;
                }
            }
            if (title !== undefined) {
                js.confirm(title + '？', function () {
                    js.ajaxReq({
                        url: url,
                        data: param,
                        type: 'POST',
                        mm  : false,
                        success: function (result) {
                            if (event != undefined) {
                                eval(event + "(result)");
                            } else {
                                if (result.code == 0) {
                                    js.showSuccessMessage(result.msg);
                                    if (trigger != undefined) {
                                        eval(trigger+"()");
                                    }
                                } else {
                                    js.showErrorMessage(result.msg)
                                }
                            }
                        }
                    })
                });
            } else {
                js.ajaxReq({
                    url: url,
                    type: 'POST',
                    success: function (result) {
                        if (event != undefined) {
                            eval(event + "(result)");
                        } else {
                            if (result.code == 0) {
                                js.showSuccessMessage(result.msg);
                                if (trigger != undefined) {
                                    eval(trigger+"()");
                                }
                            } else {
                                js.showErrorMessage(result.msg)
                            }
                        }
                    }
                })
            }
        });
        $(document).on("click", ".iframeAddTab", function (e) {
            e.preventDefault();
            var url = $(this).data("url");
            var title = $(this).data("title");
            js.addTab(url, title);
        });

        /* 关闭弹出层 */
        $(".close-popup").click(function (e) {
            e.preventDefault();
            parent.layer.close(window.parent.layerIndex);
        });


        /* 下拉按钮组 */
        $(".btn-group").click(function (e) {
            e.stopPropagation();
            $this = $(this);
            $this.toggleClass("show");
            $(document).one("click", function () {
                if ($this.hasClass("show")) {
                    $this.removeClass("show");
                }
            });
        });

        // 展示数据列表-多选框
        var thcheckbox = $(".timo-table th .timo-checkbox :checkbox");
        thcheckbox.on("change", function () {
            var tdcheckbox = $(".timo-table td .timo-checkbox :checkbox");
            if (thcheckbox.is(':checked')) {
                tdcheckbox.prop("checked", true);
            } else {
                tdcheckbox.prop("checked", false);
            }
        });

        // 检测列表数据是否为空
        var timoTable = $(".timo-table tbody");
        if (timoTable.length > 0) {
            var children = timoTable.children();
            if (children.length === 0) {
                var length = $(".timo-table thead th").length;
                var trNullInfo = "<tr><td class='timo-table-null' colspan='"
                    + length + "'>没有找到匹配的记录</td></tr>";
                timoTable.append(trNullInfo);
            }
        }

        $(".sortable").click(function () {
            sortTable($(this))
        })

        function sortTable(th) {
            var myCompFunc = function ($td1, $td2, isAsc) {
                var v1 = $.trim($td1.text()).replace(/,|\s+|%/g, '');
                var v2 = $.trim($td2.text()).replace(/,|\s+|%/g, '');
                var pattern = /^\d+(\.\d*)?$/;
                if (pattern.test(v1) && pattern.test(v2)) {
                    v1 = parseFloat(v1);
                    v2 = parseFloat(v2);
                }

                return isAsc ? v1 > v2 : v1 < v2;
            };

            var doSort = function ($tbody, index, compFunc, isAsc) {
                var $trList = $tbody.find("tr");
                var len = $trList.length;
                for (var i = 0; i < len - 1; i++) {
                    for (var j = 0; j < len - i - 1; j++) {
                        var $td1 = $trList.eq(j).find("td").eq(index);
                        var $td2 = $trList.eq(j + 1).find("td").eq(index);

                        if (compFunc($td1, $td2, isAsc)) {
                            var t = $trList.eq(j + 1);
                            $trList.eq(j).insertAfter(t);
                            $trList = $tbody.find("tr");
                        }
                    }
                }
            }

            var init = function (th) {
                var asc = th.attr('data-asc');
                th.siblings().removeClass("asc").removeClass("desc");
                var index = th.index();

                isAsc = asc === undefined ? true : (asc > 0 ? true : false);

                doSort($(th).closest("table").find("tbody"), index, myCompFunc, isAsc);

                $(th).attr('data-asc', 1 - (isAsc ? 1 : 0));
                if (isAsc) {
                    th.addClass("asc").removeClass("desc");
                } else {
                    th.addClass("desc").removeClass("asc");
                }


            };

            init(th);
        }


    });
    var js = {
        baseURL:"",
        ctxPath: function (url) {
            var ctxPath = js.baseURL;
            if (ctxPath==undefined || ctxPath=="") {
                ctxPath = js.cookie("cxtPath");
            }
            if (ctxPath == undefined || ctxPath == "") {
                ctxPath = "/";
            }
            if (url && url != '') {
                if (url.substr(0, 1) == "/") {
                    url = url.substr(1);
                }
                if (ctxPath.startsWith("/") && ("/" + url).startsWith(ctxPath)) {
                    return "/" + url;
                }

                return ctxPath + url;
            }
            return ctxPath;
        },
        log: function (msg) {
            if (typeof(console) !== "undefined") {
                console.log(msg)
            }
        },
        error: function (msg) {
            if (typeof(console) !== "undefined") {
                console.error(msg)
            }
        },
        encodeUrl: function (url) {
            return encodeURIComponent(url)
        },
        decodeUrl: function (url) {
            return decodeURIComponent(url)
        },
        ie: function () {
            var agent = navigator.userAgent.toLowerCase();
            return (!!window.ActiveXObject || "ActiveXObject" in window) ? ((agent.match(/msie\s(\d+)/) || [])[1] || (agent.match(/Trident/i) && agent.match(/rv:(\d+)/) || [])[1] || false) : false
        }(),
        val: function (jsonObj, attrName) {
            if (jsonObj === undefined) {
                return ""
            }
            if (attrName === undefined || attrName == "") {
                return typeof jsonObj === "string" ? jsonObj : ""
            }
            var ret = jsonObj[attrName], prm = [], p, i;
            if (ret === undefined) {
                try {
                    if (typeof attrName === "string") {
                        prm = attrName.split(".")
                    }
                    i = prm.length;
                    if (i) {
                        ret = jsonObj;
                        while (ret && i--) {
                            p = prm.shift();
                            ret = ret[p]
                        }
                    }
                } catch (e) {
                }
            }
            if (ret === undefined) {
                return ""
            }
            return ret
        },
        hashCode: function (str, caseSensitive) {
            if (caseSensitive != true) {
                str = str.toLowerCase()
            }
            var hash = 1315423911, i, ch;
            for (i = str.length - 1; i >= 0; i--) {
                ch = str.charCodeAt(i);
                hash ^= ((hash << 5) + ch + (hash >> 2))
            }
            return (hash & 2147483647)
        },
        loadFile: function (file, callback, error) {
            callback = callback || function () {
            };
            error = error || function (data) {
                js.showMessage(data)
            };
            var files = typeof file == "string" ? [file] : file;
            var htmlDoc = document.getElementsByTagName("head")[0], okCounts = 0, fileCounts = files.length, i,
                loadFilePath = null;
            for (i = 0; i < fileCounts; i++) {
                var includeFile = null, att = null, ext, hash;
                loadFilePath = files[i];
                hash = js.hashCode(loadFilePath);
                if (document.getElementById("loadHash_" + hash)) {
                    okCounts += 1;
                    if (okCounts == fileCounts) {
                        callback();
                        return true
                    }
                    continue
                }
                att = loadFilePath.split("?")[0].split(".");
                ext = att[att.length - 1].toLowerCase();
                switch (ext) {
                    case"css":
                        includeFile = document.createElement("link");
                        includeFile.setAttribute("rel", "stylesheet");
                        includeFile.setAttribute("type", "text/css");
                        includeFile.setAttribute("href", loadFilePath);
                        break;
                    case"js":
                        includeFile = document.createElement("script");
                        includeFile.setAttribute("type", "text/javascript");
                        includeFile.setAttribute("src", loadFilePath);
                        break;
                    case"jpg":
                    case"jpeg":
                    case"png":
                    case"gif":
                        includeFile = document.createElement("img");
                        includeFile.setAttribute("src", loadFilePath);
                        break;
                    default:
                        error("载入的格式不支持:" + loadFilePath);
                        return false
                }
                if (typeof includeFile != "undefined") {
                    includeFile.setAttribute("id", "loadHash_" + hash);
                    htmlDoc.appendChild(includeFile);
                    includeFile.onreadystatechange = function () {
                        if (includeFile.readyState == "loaded" || includeFile.readyState == "complete") {
                            okCounts += 1;
                            if (okCounts == fileCounts) {
                                callback();
                                return true
                            }
                        }
                    };
                    includeFile.onload = function () {
                        okCounts += 1;
                        if (okCounts == fileCounts) {
                            callback();
                            return true
                        }
                    };
                    includeFile.onerror = function () {
                        $("#loadhash_" + hash).remove();
                        error(loadFilePath + " load error");
                        return false
                    }
                } else {
                    error("文件创建失败");
                    return false
                }
            }
        },
        windowOpen: function (url, name, width, height) {
            if (!(width && height)) {
                width = window.screen.width - 200;
                height = window.screen.height - 150
            }
            var top = parseInt((window.screen.height - height) / 2 - 20, 10),
                left = parseInt((window.screen.width - width) / 2, 10),
                options = "location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,resizable=yes,scrollbars=yes,width=" + width + ",height=" + height + ",top=" + top + ",left=" + left;
            window.open(url, name, options)
        },
        windowClose: function () {
            setTimeout(function () {
                window.opener = null;
                window.open("", "_self");
                window.close()
            }, 100)
        },
        addParam: function (url, params) {
            if (params != "") {
                url += (url.indexOf("?") == -1 ? "?" : "&");
                url += params
            }
            return url
        },
        getParam: function (paramName, url) {
            var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)", "i");
            if (!url || url == "") {
                url = window.location.search
            } else {
                url = url.substring(url.indexOf("?"))
            }
            r = url.substr(1).match(reg);
            if (r != null) {
                return unescape(r[2])
            }
            return null
        },
        alertObj: function (obj) {
            var ob = eval(obj);
            var index = 1, property = "";
            for (var p in ob) {
                property += (index++) + "、" + p + " = " + ob[p] + "\n"
            }
            alert(property)
        },
        text: function (code, params) {
            if (code) {
                js.i18n = js.i18n || {};
                var text = js.i18n[code];
                if (!(text && text != "")) {
                    text = code
                }
                if (params) {
                    for (var i = 1; i < arguments.length; i++) {
                        var re = new RegExp("\\{" + (i - 1) + "\\}", "gm");
                        text = text.replace(re, arguments[i])
                    }
                }
                return text
            }
            return code
        },
        getDictLabel: function (dictListJson, value, defaultValue, inCss) {
            var result = [];
            for (var i = 0; i < dictListJson.length; i++) {
                var row = dictListJson[i];
                if (("," + value + ",").indexOf("," + row.dictValue + ",") != -1) {
                    var str = "";
                    if (inCss && (row.cssClass || row.cssStyle)) {
                        str += "<span";
                        if (row.cssClass) {
                            str += ' class="' + row.cssClass + '"'
                        }
                        if (row.cssStyle) {
                            str += ' style="' + row.cssStyle + '"'
                        }
                        result.push(str + ">" + row.dictLabel + "</span>")
                    } else {
                        result.push(row.dictLabel)
                    }
                }
            }
            return result.length > 0 ? result.join(",") : defaultValue
        },
        loading: function (message) {
            if (layui.layer == undefined) return;
            var index = layui.layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            return index;
        },
        closeLoading: function (index) {
            if (layui.layer == undefined) return;
            layui.layer.close(index);
        },
        resizeLayer:function (layerIndex,layerInitWidth,layerInitHeight) {
            var docWidth = $(document).width();
            var docHeight = $(document).height();
            var minWidth = layerInitWidth > docWidth ? docWidth : layerInitWidth;
            var minHeight = layerInitHeight > docHeight ? docHeight : layerInitHeight;

            js.layer.style(layerIndex, {
                top:0,
                width: minWidth,
                height:minHeight
            });
        },
        layer: function () {
            try {
                if (top.layer) {
                    return top.layer
                }
                if (parent.parent.layer) {
                    return parent.parent.layer
                }
                if (parent.layer) {
                    return parent.layer
                }
            } catch (e) {
            }
            if (window.layer) {
                return layer
            }
            return null
        }(),
        showMessage: function (message, title, type, timeout) {
            var msgType, layerIcon, msg = String(message), msgTimeout = timeout == undefined ? 4000 : timeout;
            var contains = function (str, searchs) {
                if (searchs) {
                    var ss = searchs.split(",");
                    for (var i = 0; i < ss.length; i++) {
                        if (msg.indexOf(ss[i]) >= 0) {
                            return true
                        }
                    }
                }
                return false
            };
            if (type == "error" || contains(msg, js.text("showMessage.error"))) {
                msgType = "error";
                layerIcon = 2
            } else {
                if (type == "warning" || contains(msg, js.text("showMessage.warning"))) {
                    msgType = "warning";
                    layerIcon = 5
                } else {
                    if (type == "success" || contains(msg, js.text("showMessage.success"))) {
                        msgType = "success";
                        layerIcon = 1
                    } else {
                        msgType = "info";
                        layerIcon = 6
                    }
                }
            }
            try {
                if (top.toastr) {
                    var positionClass = "toast-bottom-right";
                    if (msg && msg.length >= 8 && msg.indexOf("posfull:") >= 0) {
                        if (timeout == undefined) {
                            msgTimeout = 0
                        }
                        positionClass = "toast-top-full-width";
                        msg = msg.substring(8);
                        js.log(msg)
                    }
                    top.toastr.options = {closeButton: true, positionClass: positionClass, timeOut: msgTimeout};
                    top.toastr[msgType](msg, title);
                    return
                }
            } catch (e) {
            }
            if (!js.layer) {
                alert(msg);
                return
            }
            if (layerIcon) {
                js.layer.msg(msg, {icon: layerIcon, time: msgTimeout})
            } else {
                js.layer.msg(msg, {time: msgTimeout})
            }
        },
        showSuccessMessage: function (message) {
            js.showMessage(message, null, "success");
        },
        showErrorMessage: function (responseText) {
            if (responseText && responseText != "") {
                js.error(js.abbr(responseText, 500));
                if (responseText.indexOf("<html ") != -1 || responseText.indexOf("<head ") != -1 || responseText.indexOf("<body ") != -1) {
                    js.showMessage("未知错误，F12查看异常信息！", null, "error")
                } else {
                    try {
                        var json = JSON.parse(responseText);
                        if (typeof json == "object" && typeof json.message != "undefined") {
                            js.showMessage(json.message, null, "error");
                            return
                        }
                    } catch (e) {
                    }
                    js.showMessage(responseText, null, "error")
                }
            }
        },
        closeMessage: function () {
            try {
                if (top.toastr) {
                    top.toastr.clear()
                }
            } catch (e) {
            }
        },
        alert: function (message, options, closed) {
            if (typeof options != "object") {
                closed = options;
                options = {icon: 1, skin: "layui-layer-lan"}
            }
            if (!js.layer) {
                alert(message);
                if (typeof closed == "function") {
                    closed()
                }
                return
            }
            js.layer.alert(message, options, function (index) {
                if (typeof closed == "function") {
                    closed(index)
                }
                js.layer.close(index)
            })
        },
        confirm: function (message, func) {
            if (!js.layer) {
                if (confirm(message)) {
                    func();
                }
                return
            }
            var options = {
                icon: 3,
                skin: "layui-layer-lan",
                title: "系统提示",
                btn: ['确认', '取消'],
                btnclass: ['btn btn-primary', 'btn btn-danger']
            };
            js.layer.confirm(message, options, function (index) {
                func();
                js.layer.close(index)
            });
            return false
        },
        openLayer: function (options) {
            var index = layer.open($.extend(options, {
                skin: "layui-layer-lan", type: 2,
                shadeClose: true,
                size: '690,400'
            }));
            window.layerIndex = index;
            return index;
        },
        openLayer2: function (options) {
            var index = layer.open($.extend(options, {
                skin: "layui-layer-lan", type: 2,
                shadeClose: true,
                size: '690,400'
            }));
            return index;
        },
        template: function (id, data, callback) {
            var tpl = String($("#" + id).html()).replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, ""), data = data || [];
            if (typeof callback == "function") {
                laytpl(tpl).render(data || [], function (render) {
                    callback(render)
                });
                return null
            }
            return laytpl(tpl).render(data || [])
        },
        goLogin: function () {
            top.location.href = js.ctxPath() + "/login.html";
        },
        goUnauth: function () {
            window.location.href = js.ctxPath() + "/error/unauth.html";
        },
        unauth: function (msg) {
            js.showErrorMessage(msg);
        },
        initTable: function (el, data,pageNum,pageSize) {
            if (!$(el)) return;
            if (!data) return;
            var thead = $(el).find("thead");
            var tbody = $(el).find("tbody");
            tbody.empty();
            var rows = [];
            var dict = [];
            var theadTr = thead.find("tr");
            var eventTr = undefined;
            if (theadTr != undefined) {
                eventTr = theadTr.data("event");
            }

            thead.find("th").each(function (index, value) {
                var field = $(this).data("field");
                var event = $(this).data("event");
                var style = $(this).data("style");
                var type = $(this).data("type");
                var aclass = $(this).data("class");
                var dictType = $(this).data("dicttype");
                rows.push({field: field, event: event, style: style, type: type, class: aclass, dictType: dictType});

                //判断是否存在字典
                if (dictType != undefined) {
                    js.getDictType(dictType, "", function (adict) {
                        dict[field] = adict;
                    });
                }
                $(this).removeAttr("data-field");
                $(this).removeAttr("data-event");
                $(this).removeAttr("data-style");
                $(this).removeAttr("data-type");
                $(this).removeAttr("data-class");
                $(this).removeAttr("data-dicttype");

            });

            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                var tr = $("<tr></tr>");
                if (eventTr !=undefined) {
                    eval(eventTr + "(i,item)");
                }

                for (var j = 0; j < rows.length; j++) {
                    var row = rows[j];
                    var td = $("<td ></td>");
                    if (row.class != undefined || row.style != undefined) {
                        if (row.style == undefined) {
                            row.style = ""
                        }
                        var aclass = "";
                        if (row.class != undefined) {
                            aclass = "class=\'" + row.class + "\' ";
                        }
                        td = $("<td " + aclass + row.style + "></td>");
                    }
                    if (row.event != undefined && row.event != "") {
                        var value = eval(row.event + "(i + 1 , item,row.field ,item[row.field])");
                        td.append(value);
                    } else if (row.type == "checkbox" ) {

                        td.append(" <label class=\"timo-checkbox\"><input  value=\"" + item[row.field] + "\" type=\"checkbox\">\n" +
                            "                            <i class=\"layui-icon layui-icon-ok\"></i></label>");
                        $(td).attr("data-index",i);
                    } else if (row.type=="radio") {
                        td.append(" <label class=\"timo-checkbox timo-radio\"><input name=\'"+row.field+"\'  value=\"" + item[row.field] + "\" type=\"radio\">\n" +
                            "                           <i class=\"layui-icon layui-icon-ok\"></i></label>")
                        $(td).attr("data-index",i);
                    }
                    else if (row.field != undefined && row.field != "") {
                        if (row.field == "[index]") {
                            td.append((pageNum-1)*pageSize + i + 1);
                        } else if (row.dictType != undefined && row.dictType != '') {
                            var dicts = dict[row.field];
                            for (var k = 0; k < dicts.length; k++) {
                                if (dicts[k]["dictValue"] == item[row.field]) {
                                    if (dicts[k]["listClass"] != undefined && dicts[k]["listClass"] !="") {
                                       td.append("<span class=\"layui-btn layui-btn-xxs layui-btn-"+ dicts[k]["listClass"] +"\">"+dicts[k]["dictLabel"]+"</span>");
                                    } else {
                                        td.append(dicts[k]["dictLabel"]);
                                    }
                                    break;
                                }
                            }
                        } else {
                            td.append(item[row.field]);
                        }
                    }
                    tr.append(td);
                }

                tbody.append(tr);
            }
            if (data.length == 0) {
                tbody.append("<tr><td class='timo-table-null' colspan='" + rows.length + "'>没有找到匹配的记录</td></tr>");
            }
        },
        getFormData: function (form) {
            var row = form.serializeArray();
            var reqparmas = {};
            for (var i = 0; i < row.length; i++) {
                reqparmas[row[i]["name"]] = row[i]["value"];
            }
            return reqparmas;
        },
        transformData: function (data, pre) {
            var ret = ''
            if (pre == undefined) {
                pre = '';
            }
            for (var it in data) {
                if (data[it] == undefined || data[it] === "") {
                    continue;
                }
                var key = pre + it;
                if (data[it] instanceof Array) {
                    for (var i = 0; i < data[it].length; i++) {
                        ret += transformData(data[it][i], key + "[" + i + "].")
                    }
                } else if (data[it] instanceof Object) {
                    ret += transformData(data[it], key + ".")
                } else {
                    ret +=
                        encodeURIComponent(key) +
                        '=' +
                        encodeURIComponent(data[it]) +
                        '&'
                }
            }
            return ret
        },
        encrypt: null,
        mmParam: function (params, method) {
            var key = uuid();
            var RSA = function (content) {
                var publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD67aYRYlyuFzQziwqax3/5Z3A0GFmmMFiGLs6sqBzRMKAR2d8YNKicAZTiMZnzH9BPWeLFgTTLUhuUbFGtVx+f6BKKZNOq1xqRKcyMWh/Ae69ENGquCbRgjZz7r5vyKp35bSDryu/kFl+8z9xMkRVfisD0zwDDwXfVqcu2YaraYQIDAQAB";
                if (js.encrypt == null) {
                    js.encrypt = new JSEncrypt();
                    js.encrypt.setPublicKey(publicKey);
                }
                return js.encrypt.encrypt(content);
            }
            var mmkey = RSA(key);
            var s4 = new SM4Util();
            s4.secretKey = key;
            if (params == null || params == undefined) {
                params = {};
            } else if (params instanceof Array) {
                var formData = {};
                for (var i = 0; i < params.length; i++) {
                    formData[params[i]["name"]] = params[i]["value"];
                }
                params = formData;
            }
            try {
                params = JSON.stringify(params);
            } catch (e) {
                console.log(e)
            }
            var mm = s4.encryptData_ECB(params);
            var mmJson = {
                mmkey: mmkey,
                mmdata: mm
            }
            if (method == "get" || method == "GET") {
                mmJson = js.transformData(mmJson);
            }
            return mmJson;
        },
        ajaxReq: function (options) {
            var url = options.url;
            url = js.ctxPath(url);

            var processData = options.processData ;
            if (processData == undefined) {
                processData = true;
            }
            var method = options.type || "GET";
            var contentType = options.contentType;
            var data = options.data;

            //不加密的话注释一下判断加密部分代码
            // if (options.mm == true || options.mm == undefined) {
            //     data = js.mmParam(options.data);
            // }

            if (contentType == undefined) {
               contentType = "application/x-www-form-urlencoded;charset=UTF-8";
            }
            if (contentType && contentType.indexOf("application/json") > -1) {
                data = JSON.stringify(data);
            }
            var async = options.async;
            if (async == undefined) {
                async = true;
            }
            var index = js.loading(options.message == undefined ? js.text("loading.submitMessage") : options.message);
            $.ajax({
                url: url,
                type: method,
                contentType: contentType, //关键是要加上这行
                traditional: true, //这使json格式的字符不会被转码
                xhrFields: {
                    withCredentials: true
                },
                async: async,
                processData:processData,
                data: data,
                crossDomain: true,
                beforeSend: function (XMLHttpRequest) {
                    XMLHttpRequest.setRequestHeader("X-Requested-With", "XMLHttpRequest");
                },
                error: function (data) {
                    $(".btn").attr("disabled", false);
                    js.showErrorMessage(data.responseText);
                    js.closeLoading(index);
                    if (typeof options.error == "function") {
                        options.error(data, status, xhr)
                    } else {
                        js.log(data)
                    }
                },
                success: function (data, status, xhr) {
                    js.closeLoading(index);
                    try {
                        if (data['code'] == "401") {
                            js.layer.msg(data["msg"], {time: 1000}, function () {
                                js.goLogin();
                            });
                        } else if (data['code'] == "403") {
                            js.layer.msg(data["msg"], {time: 1000}, function () {
                                js.unauth();
                            });
                        } else {
                            if (options.success) {
                                options.success(data);
                            } else {
                                js.log(data)
                            }
                        }
                    } catch (e) {
                        js.error(e);
                    }
                }
            });
        },
        pageSearch: function (pageNum, pageSize,isfirst,options) {
            var url = options.url + "?pageNum=" + pageNum + "&pageSize=" + pageSize;
            js.ajaxReq({
                url: url,
                type: options.type,
                data: options.data,
                success: function (data) {
                    js.initTable(options.tableEl, data.rows,pageNum,pageSize);
                    if (options.success) {
                        options.success(data.rows);
                    }
                    //自定义样式
                    if (isfirst) {
                        isfirst = false;
                        layui.laypage.render({
                            elem: 'tablePage'
                            , count: data.total
                            , theme: '#1E9FFF'
                            , layout: ['limit', 'prev', 'page', 'next']
                            , limit : pageSize
                            , jump: function (obj, first) {
                                if (options.jump) {
                                    options.jump(obj,first);
                                }
                                if (!first) {
                                    js.pageSearch(obj.curr,obj.limit,isfirst,options);
                                }
                            }
                        });
                    }
                }
            });
        },
        download: function (filename, del) {
            if (del == undefined) {
                del = true
            }
            window.location.href = js.ctxPath("common/download?fileName=" + filename + "&delete=" + del, "get");
        },
        trim: function (str) {
            return jQuery.trim(str)
        },
        startWith: function (str, start) {
            var reg = new RegExp("^" + start);
            return reg.test(str)
        },
        endWith: function (str, end) {
            var reg = new RegExp(end + "$");
            return reg.test(str)
        },
        abbr: function (name, maxLength) {
            if (!maxLength) {
                maxLength = 20
            }
            if (name == null || name.length < 1) {
                return ""
            }
            var w = 0;
            var s = 0;
            var p = false;
            var b = false;
            var nameSub;
            for (var i = 0; i < name.length; i++) {
                if (i > 1 && b == false) {
                    p = false
                }
                if (i > 1 && b == true) {
                    p = true
                }
                var c = name.charCodeAt(i);
                if ((c >= 1 && c <= 126) || (65376 <= c && c <= 65439)) {
                    w++;
                    b = false
                } else {
                    w += 2;
                    s++;
                    b = true
                }
                if (w > maxLength && i <= name.length - 1) {
                    if (b == true && p == true) {
                        nameSub = name.substring(0, i - 2) + "..."
                    }
                    if (b == false && p == false) {
                        nameSub = name.substring(0, i - 3) + "..."
                    }
                    if (b == true && p == false) {
                        nameSub = name.substring(0, i - 2) + "..."
                    }
                    if (p == true) {
                        nameSub = name.substring(0, i - 2) + "..."
                    }
                    break
                }
            }
            if (w <= maxLength) {
                return name
            }
            return nameSub
        },
        formatNumber: function (num, cent, isThousand, defaultValue) {
            if (num == undefined || num == "") {
                return defaultValue || ""
            }
            num = num.toString().replace(/\$|\,/g, "");
            if (isNaN(num)) {
                num = "0"
            }
            var sign = (num == (num = Math.abs(num)));
            num = Math.floor(num * Math.pow(10, cent) + 0.50000000001);
            var cents = num % Math.pow(10, cent);
            num = Math.floor(num / Math.pow(10, cent)).toString();
            cents = cents.toString();
            while (cents.length < cent) {
                cents = "0" + cents
            }
            if (isThousand) {
                for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++) {
                    num = num.substring(0, num.length - (4 * i + 3)) + "," + num.substring(num.length - (4 * i + 3))
                }
            }
            if (cent > 0) {
                return (((sign) ? "" : "-") + num + "." + cents)
            } else {
                return (((sign) ? "" : "-") + num)
            }
        },
        formatMoney: function (s, n) {
            if (s == undefined || s == "") {
                return "0.00"
            }
            n = n >= 0 && n <= 20 ? n : 2;
            s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
            var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1], i, t = "";
            for (i = 0; i < l.length; i++) {
                t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "")
            }
            return t.split("").reverse().join("") + (r ? "." + r : "")
        },
        numberPad: function (num, n) {
            var len = num.toString().length;
            while (len < n) {
                num = "0" + num;
                len++
            }
            return num
        },
        formatDate: function (date, f) {
            if (date == undefined) {
                return ""
            }
            if (f == undefined) {
                f = "yyyy-MM-dd HH:mm"
            }
            var o = {
                "M+": date.getMonth() + 1,
                "d+": date.getDate(),
                "H+": date.getHours(),
                "m+": date.getMinutes(),
                "s+": date.getSeconds(),
                "q+": Math.floor((date.getMonth() + 3) / 3),
                S: date.getMilliseconds()
            };
            if (/(y+)/.test(f)) {
                f = f.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length))
            }
            for (var k in o) {
                if (new RegExp("(" + k + ")").test(f)) {
                    f = f.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length))
                }
            }
            return f
        },
        parseDate: function (date) {
            if (date == undefined) {
                return null
            }
            return new Date(date.replace(/-/g, "/"))
        },
        addDate: function (date, dadd) {
            date = date.valueOf();
            date = date + dadd * 24 * 60 * 60 * 1000;
            return new Date(date)
        },
        quickSelectDate: function (type, beginDateId, endDateId) {
            var now = new Date(), nowYear = now.getFullYear(), nowMonth = now.getMonth(), nowDay = now.getDate(),
                nowDayOfWeek = now.getDay(), beginDate = $("#" + beginDateId), endDate = $("#" + endDateId),
                formatDate = function (date) {
                    return js.formatDate(date, "yyyy-MM-dd")
                }, getMonthDays = function (myMonth) {
                    var monthStartDate = new Date(nowYear, myMonth, 1);
                    var monthEndDate = new Date(nowYear, myMonth + 1, 1);
                    var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
                    return days
                };
            if (type == "1") {
                beginDate.val(formatDate(now));
                endDate.val(beginDate.val())
            } else {
                if (type == "2") {
                    nowDayOfWeek = nowDayOfWeek == 0 ? 6 : nowDayOfWeek - 1;
                    var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
                    var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));
                    beginDate.val(formatDate(weekStartDate));
                    endDate.val(formatDate(weekEndDate))
                } else {
                    if (type == "3") {
                        var monthStartDate = new Date(nowYear, nowMonth, 1);
                        var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
                        beginDate.val(formatDate(monthStartDate));
                        endDate.val(formatDate(monthEndDate))
                    } else {
                        if (type == "4") {
                            var quarterStartMonth = 0;
                            if (nowMonth < 3) {
                                quarterStartMonth = 0
                            }
                            if (2 < nowMonth && nowMonth < 6) {
                                quarterStartMonth = 3
                            }
                            if (5 < nowMonth && nowMonth < 9) {
                                quarterStartMonth = 6
                            }
                            if (nowMonth > 8) {
                                quarterStartMonth = 9
                            }
                            var quarterEndMonth = quarterStartMonth + 2;
                            var quarterStartDate = new Date(nowYear, quarterStartMonth, 1);
                            var quarterEndDate = new Date(nowYear, quarterEndMonth, getMonthDays(quarterEndMonth));
                            beginDate.val(formatDate(quarterStartDate));
                            endDate.val(formatDate(quarterEndDate))
                        } else {
                            if (type == "5") {
                                var lastMonthDate = new Date();
                                lastMonthDate.setDate(1);
                                lastMonthDate.setMonth(lastMonthDate.getMonth() - 1);
                                var lastYear = lastMonthDate.getYear();
                                var lastMonth = lastMonthDate.getMonth();
                                var lastMonthStartDate = new Date(nowYear, lastMonth, 1);
                                var lastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth));
                                beginDate.val(formatDate(lastMonthStartDate));
                                endDate.val(formatDate(lastMonthEndDate))
                            }
                        }
                    }
                }
            }
            beginDate.change();
            endDate.change()
        },
        cookie: function (name, value, options) {
            if (typeof value != "undefined") {
                options = options || {};
                if (value === null) {
                    value = "";
                    options.expires = -1
                }
                var expires = "";
                if (options.expires && (typeof options.expires == "number" || options.expires.toUTCString)) {
                    var date;
                    if (typeof options.expires == "number") {
                        date = new Date();
                        date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000))
                    } else {
                        date = options.expires
                    }
                    expires = "; expires=" + date.toUTCString()
                }
                var path = options.path ? "; path=" + options.path : (window.ctxPath ? "; path=" + window.ctxPath : "");
                var domain = options.domain ? "; domain=" + options.domain : "";
                var secure = options.secure ? "; secure" : "";
                document.cookie = [name, "=", encodeURIComponent(value), expires, path, domain, secure].join("")
            } else {
                var cookieValue = null;
                if (document.cookie && document.cookie != "") {
                    var cookies = document.cookie.split(";");
                    for (var i = 0; i < cookies.length; i++) {
                        var cookie = jQuery.trim(cookies[i]);
                        if (cookie.substring(0, name.length + 1) == (name + "=")) {
                            cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                            break
                        }
                    }
                }
                return cookieValue
            }
        },
        doChangeCheck: function () {
            if ($.fn.iCheck !== undefined) {
                $("input").iCheck({
                    checkboxClass: "icheckbox_" + ($(this).data("style") || "square-blue"),
                    radioClass: "iradio_" + ($(this).data("style") || "square-blue")
                });
            }
        },
        doChangeSelect: function () {
            if ($.fn.select2 !== undefined) {
                $("select.form-control:not(.noselect2)").each(function () {
                    $(this).select2({minimumResultsForSearch: 10}).on("change", function () {
                        try {
                            $(this).valid()
                        } catch (e) {
                        }
                    })
                })
            }
        },
        hasPerm: function (perm, fun) {
            js.ajaxReq({
                url: "hasperm/" + perm,
                async: false,
                success: function (data) {
                    if ($.isFunction(fun)) {
                        fun(data);
                        return;
                    }
                    if (fun == undefined && data == "false") {
                        js.goUnauth();
                    }
                    if (fun != undefined && data == "false") {
                        $("#" + fun).hide();
                    }
                }
            })
        },
        hasPerms: function (perm, fun) {
            js.ajaxReq({
                url: "hasperms",
                async: false,
                type: "post",
                contentType: "application/json;charset=UTF-8",
                data: perm,
                success: function (data) {
                    if ($.isFunction(fun)) {
                        if (!fun(data)) {
                            return;
                        }
                    }
                    if (data != undefined) {
                        for (var i = 0; i < data.length; i++) {
                            if (data[i]["flag"] == "false") {
                                $("#" + data[i]["key"]).hide();
                            }
                        }
                    }
                }
            })
        },
        dateRange: function (startEl, endEl) {
            // 开始时间
            var start = layui.laydate.render({
                elem: startEl,
                theme: '#038cf3',
                trigger: 'click',
                done: function (value, date) {
                    end.config.min = {
                        year: date.year,
                        month: date.month - 1,
                        date: date.date,
                        hours: date.hours,
                        minutes: date.minutes,
                        seconds: date.seconds
                    };
                    // 作为 结束选择 的 开始时间
                    end.config.value = value;
                }
            });
            // 结束时间
            var end = layui.laydate.render({
                // 绑定元素
                elem: endEl,
                //  主题色
                theme: '#038cf3',
                //  触发方式
                trigger: 'click',
                // 底部按钮
                // btns: ['clear', 'confirm'],
                // showBottom: false,
                // 选择完成回调
                done: function (value, date) {
                    start.config.max = {
                        year: date.year,
                        month: date.month - 1,
                        date: date.date,
                        hours: date.hours,
                        minutes: date.minutes,
                        seconds: date.seconds
                    };
                    start.config.value = value;
                }
            });
            layui.laydate.render(start);
            layui.laydate.render(end);
        },
        dateEl: function (el, options) {
            layui.laydate.render(
                $.extend({
                    elem: el,
                    theme: '#038cf3',
                    trigger: 'click',
                }, options)
            );
        },
        getDictOptions: function (id, data) {
            if (id != undefined && data != undefined) {
                var select = $(id);
                select.empty();
                select.append("<option value=\"\">请选择</option>");
                for (var i = 0; i < data.length; i++) {
                    select.append("<option value=" + data[i]["dictValue"] + ">" + data[i]["dictLabel"] + "</option>");
                }
            }
        },
        getDictType: function (dictType, id, fun, value) {
            js.ajaxReq({
                url: "/common/getdict",
                type: "get",
                data: {
                    "dictType": dictType
                },
                async: false,
                success: function (result) {
                    var data = result.data;
                    if ($.isFunction(fun)) {
                        if (!fun(data)) {
                            return;
                        }
                    }

                    if (id != undefined && data != undefined) {
                        var select = $(id);
                        select.empty();
                        select.append("<option value=\"\">请选择</option>");
                        for (var i = 0; i < data.length; i++) {
                            if (data[i]["dictValue"] == value) {
                                select.append("<option selected value=" + data[i]["dictValue"] + ">" + data[i]["dictLabel"] + "</option>");
                            } else {
                                select.append("<option value=" + data[i]["dictValue"] + ">" + data[i]["dictLabel"] + "</option>");
                            }
                        }
                    }
                }
            });
        },
        getDictLabel: function (DictData, value) {
            for (var i = 0; i < DictData.length; i++) {
                if (DictData[i]["dictValue"] == value) {
                    if (DictData[i]["listClass"] != '') {
                        return "<span class='btn btn-" + DictData[i]["listClass"] + " btn-xs'>" + DictData[i]["dictLabel"] + "</span>";
                    } else if (DictData[i]["cssClass"] != "") {
                        return "<span style='" + DictData[i]["cssClass"] + "'>" + DictData[i]["dictLabel"] + "</span>";
                    } else {
                        return DictData[i]["dictLabel"];
                    }
                }
            }
            return value;
        },
        exportTable: function (el, url, type, data) {
            if (type == undefined) {
                type = "get";
            }
            if (data == undefined) {
                data = el.serializeArray();
            }
            js.ajaxReq({
                url: url,
                data: data,
                type: type,
                success: function (data) {
                    if (data.code == 0) {
                        window.location.href = js.ctxPath() + "common/download?fileName=" + data.msg + "&delete=" + true;
                    } else {
                        js.showErrorMessage(data.msg);
                    }
                }
            });
        },
        addTab: function (url, title) {
            var addEvent = undefined;
            if (top.addTab) {
                addEvent = top.addTab
            }
            if (addEvent == undefined && parent.parent.addTab) {
                addEvent = parent.parent.addTab
            }
            if (addEvent == undefined && parent.addTab) {
                addEvent = parent.addTab
            }

            if (addEvent) {
                addEvent(url, title);
            }
        },
        formatJson: function (txt, compress) {
            var indentChar = '    ';
            if (/^\s*$/.test(txt)) {
                console.log('数据为空,无法格式化! ');
                return;
            }
            try {
                var data = eval('(' + txt + ')');
            } catch (e) {
                console.log('数据源语法错误,格式化失败! 错误信息: ' + e.description, 'err');
                return;
            }
            var draw = [],
                last = false,
                This = this,
                line = compress ? '' : '\n',
                nodeCount = 0,
                maxDepth = 0;

            var notify = function (name, value, isLast, indent, formObj) {
                nodeCount++;
                /*节点计数*/
                for (var i = 0, tab = ''; i < indent; i++)
                    tab += indentChar;
                /* 缩进HTML */
                tab = compress ? '' : tab;
                /*压缩模式忽略缩进*/
                maxDepth = ++indent;
                /*缩进递增并记录*/
                if (value && value.constructor == Array) {
                    /*处理数组*/
                    draw.push(
                        tab + (formObj ? '"' + name + '":' : '') + '[' + line
                    );
                    /*缩进'[' 然后换行*/
                    for (var i = 0; i < value.length; i++)
                        notify(i, value[i], i == value.length - 1, indent, false);
                    draw.push(
                        tab + ']' + (isLast ? line : ',' + line)
                    );
                    /*缩进']'换行,若非尾元素则添加逗号*/
                } else if (value && typeof value == 'object') {
                    /*处理对象*/
                    draw.push(
                        tab + (formObj ? '"' + name + '":' : '') + '{' + line
                    );
                    /*缩进'{' 然后换行*/
                    var len = 0,
                        i = 0;
                    for (var key in value)
                        len++;
                    for (var key in value)
                        notify(key, value[key], ++i == len, indent, true);
                    draw.push(
                        tab + '}' + (isLast ? line : ',' + line)
                    );
                    /*缩进'}'换行,若非尾元素则添加逗号*/
                } else {
                    if (typeof value == 'string') value = '"' + value + '"';
                    draw.push(
                        tab +
                        (formObj ? '"' + name + '":' : '') +
                        value +
                        (isLast ? '' : ',') +
                        line
                    );
                }
            };
            var isLast = true,
                indent = 0;
            notify('', data, isLast, indent, false);
            return draw.join('');
        }

    };
    window.js = js;
    window.log = js.log;
    window.error = js.error;
    window.lang = window.lang || "zh_CN";
    window.text = js.text
})(window.jQuery, window);