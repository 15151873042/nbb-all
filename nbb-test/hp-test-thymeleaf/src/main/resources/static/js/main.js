layui.use(['element', 'form', 'layer', 'upload'], function () {
    var $ = layui.jquery;
    var element = layui.element; //加载element模块
    var form = layui.form; //加载form模块
    var layer = layui.layer; //加载layer模块
    var laydate = layui.laydate;
    var SHOW = 'layui-show', HIDE = 'layui-hide', THIS = 'layui-this', DISABLED = 'layui-disabled'
        , APP_BODY = '#LAY_app_body', APP_FLEXIBLE = 'LAY_app_flexible'
        , FILTER_TAB_TBAS = 'iframe-tabs', TABS_BODY = 'layadmin-tabsbody-item'
    TABS_HEADER = '#LAY_app_tabsheader>li';

    var tabsPage = {};
    var setter = {pageTabs: true};

    //不开启页面标签时
    if (!setter.pageTabs) {
        $('#LAY_app_tabs').addClass(HIDE);
        $("#LAY_app").addClass('layadmin-tabspage-none');
    }


    /* 侧边栏开关 */
    $(".side-toggle").on("click", function (e) {
        e.preventDefault();
        var to = $(".layui-layout-admin");
        to.toggleClass("layui-side-shrink");
        to.attr("toggle") === 'on' ? to.attr("toggle", "off") : to.attr("toggle", "on");
    });
    $(".layui-side").on("click", function () {
        var to = $(".layui-layout-admin");
        if (to.attr("toggle") === 'on') {
            to.attr("toggle", "off");
            to.removeClass("layui-side-shrink");
        }
    });

    /* 最大化窗口 */
    $(".timo-screen-full").on("click", function (e) {
        e.preventDefault();
        if (!$(this).hasClass("full-on")) {
            var docElm = document.documentElement;
            var full = docElm.requestFullScreen || docElm.webkitRequestFullScreen ||
                docElm.mozRequestFullScreen || docElm.msRequestFullscreen;
            "undefined" !== typeof full && full && full.call(docElm);
        } else {
            document.exitFullscreen ? document.exitFullscreen()
                : document.mozCancelFullScreen ? document.mozCancelFullScreen()
                : document.webkitCancelFullScreen ? document.webkitCancelFullScreen()
                    : document.msExitFullscreen && document.msExitFullscreen()
        }
        $(this).toggleClass("full-on");
    });

    $(".timo-bell-message").on("click", function (e) {
        e.preventDefault();
    });
    //获取页面标签主体元素
    var tabsBody = function (index) {
        return $(APP_BODY).find('.' + TABS_BODY).eq(index || 0);
    }
    //切换页面标签主
    var tabsBodyChange = function (index) {
        tabsBody(index).addClass(SHOW).siblings().removeClass(SHOW);
        rollPage('auto', index);
    }
    var rollPage = function (type, index) {
        var tabsHeader = $('#LAY_app_tabsheader')
            , liItem = tabsHeader.children('li')
            , scrollWidth = tabsHeader.prop('scrollWidth')
            , outerWidth = tabsHeader.outerWidth()
            , tabsLeft = parseFloat(tabsHeader.css('left'));

        //右左往右
        if (type === 'left') {
            if (!tabsLeft && tabsLeft <= 0) return;

            //当前的left减去可视宽度，用于与上一轮的页标比较
            var prefLeft = -tabsLeft - outerWidth;

            liItem.each(function (index, item) {
                var li = $(item)
                    , left = li.position().left;

                if (left >= prefLeft) {
                    tabsHeader.css('left', -left);
                    return false;
                }
            });
        } else if (type === 'auto') { //自动滚动
            (function () {
                var thisLi = liItem.eq(index), thisLeft;

                if (!thisLi[0]) return;
                thisLeft = thisLi.position().left;

                //当目标标签在可视区域左侧时
                if (thisLeft < -tabsLeft) {
                    return tabsHeader.css('left', -thisLeft);
                }

                //当目标标签在可视区域右侧时
                if (thisLeft + thisLi.outerWidth() >= outerWidth - tabsLeft) {
                    var subLeft = thisLeft + thisLi.outerWidth() - (outerWidth - tabsLeft);
                    liItem.each(function (i, item) {
                        var li = $(item)
                            , left = li.position().left;

                        //从当前可视区域的最左第二个节点遍历，如果减去最左节点的差 > 目标在右侧不可见的宽度，则将该节点放置可视区域最左
                        if (left + tabsLeft > 0) {
                            if (left - tabsLeft > subLeft) {
                                tabsHeader.css('left', -left);
                                return false;
                            }
                        }
                    });
                }
            }());
        } else {
            //默认向左滚动
            liItem.each(function (i, item) {
                var li = $(item)
                    , left = li.position().left;

                if (left + li.outerWidth() >= outerWidth - tabsLeft) {
                    tabsHeader.css('left', -left);
                    return false;
                }
            });
        }
    }


    var closeThisTabs = function () {
        if (!tabsPage.index) return;
        $(TABS_HEADER).eq(tabsPage.index).find('.layui-tab-close').trigger('click');
    }

    //向右滚动页面标签
    $("#LAY_app_tabs").find("div.layui-icon-prev").on("click", function (e) {
        e.preventDefault();
        rollPage('left')
    })

    //向左滚动页面标签
    $("#LAY_app_tabs").find("div.layui-icon-next").on("click", function (e) {
        e.preventDefault();
        rollPage('right')
    })

    $("#closeThisTabs").click(function () {
        closeThisTabs();
    })
    var closeOtherTabs = function (type) {
        var TABS_REMOVE = 'LAY-system-pagetabs-remove';
        if (type === 'all') {
            $(TABS_HEADER + ':gt(0)').remove();
            $(APP_BODY).find('.' + TABS_BODY + ':gt(0)').remove();

            $(TABS_HEADER).eq(0).trigger('click');
        } else {
            $(TABS_HEADER).each(function (index, item) {
                if (index && index != tabsPage.index) {
                    $(item).addClass(TABS_REMOVE);
                    tabsBody(index).addClass(TABS_REMOVE);
                }
            });
            $('.' + TABS_REMOVE).remove();
        }
    }
    $("#closeOtherTabs").click(function () {
        closeOtherTabs();
    })
    $("#closeAllTabs").click(function () {
        closeOtherTabs("all");
    })

    //打开标签页
    var tabs = function (url, text) {
        if (url == undefined || url == "#" || url == "") {
            return;
        }
        //遍历页签选项卡
        var matchTo
            , tabs = $(TABS_HEADER)
            , path = url.replace(/(^http(s*):)|(\?[\s\S]*$)/g, '');

        tabs.each(function (index) {
            var li = $(this)
                , layid = li.attr('lay-id');

            if (layid === url) {
                matchTo = true;
                tabsPage.index = index;
            }
        });
        if (text == undefined || text == "") {
            var item = $('[lay-url="' + url + '"]');
            text = item.attr("lay-icon") === 'true' ? item.html()
                : item.children(".layui-nav-title").text();
        }

        text = text || '新标签页';

        if (setter.pageTabs) {
            //如果未在选项卡中匹配到，则追加选项卡
            if (!matchTo) {
                $(APP_BODY).append([
                    '<div class="layadmin-tabsbody-item layui-show">'
                    , '<iframe src="' + url + '" frameborder="0" class="layadmin-iframe"></iframe>'
                    , '</div>'
                ].join(''));
                tabsPage.index = tabs.length;
                element.tabAdd(FILTER_TAB_TBAS, {
                    title: '<span>' + text + '</span>'
                    , id: url
                    , attr: path
                });
            }
        } else {
            var iframe = tabsBody(tabsPage.index).find('.layadmin-iframe');
            iframe[0].contentWindow.location.href = url;
        }

        //定位当前tabs
        element.tabChange(FILTER_TAB_TBAS, url);

    }


    /* 监听导航栏事件，实现标签页的切换 */
    element.on("nav(layui-nav-side)", function ($this) {
        var url = $this.attr('lay-url');
        tabs(url, $this.text());
    });
    /* 监听标签栏事件，实现导航栏高亮显示 */
    element.on("tab(" + FILTER_TAB_TBAS + ")", function () {
        var layId = $(this).attr("lay-id");
        $(".layui-side .layui-this").removeClass("layui-this");
        $('[lay-url="' + layId + '"]').parent().addClass("layui-this");
        tabsPage.index = $(this).index()
        tabsBodyChange(tabsPage.index);
        // 改变地址hash值
        location.hash = this.getAttribute('lay-id');
    });
    //监听 tabspage 删除
    element.on('tabDelete(' + FILTER_TAB_TBAS + ')', function (obj) {
        var othis = $(TABS_HEADER + '.layui-this');
        obj.index && tabsBody(obj.index).remove();
    });
    /* 监听hash来切换选项卡*/
    window.onhashchange = function (e) {
        var url = location.hash.replace(/^#/, '');
        var index = $(".layui-layout-admin .layui-side .layui-nav-item")[0];
        $(index).children("a").attr("lay-icon", "true");
        if (url === "" || url === undefined) {
            url = $(index).children("[lay-url]").attr("lay-url");
        }
        tabs(url);
    };
    window.onhashchange();


    /* 初始化时展开子菜单 */
    $("dd.layui-this").parents(".layui-nav-child").parent()
        .addClass("layui-nav-itemed");

    /* 刷新iframe页面 */
    $(".refresh-btn").click(function () {
        location.reload();
    });

    /* AJAX请求默认选项，处理连接超时问题 */
    $.ajaxSetup({
        complete: function (xhr, status) {
            if (xhr.status == 401) {
                layer.confirm('session连接超时，是否重新登录？', {
                    btn: ['是', '否']
                }, function () {
                    if (window.parent.window != window) {
                        window.top.location = window.location.pathname + '/login';
                    }
                });
            }
        }
    });

    /*  漂浮消息 */
    $.fn.Messager = function (result) {
        if (result.code === 200) {
            layer.msg(result.msg, {offset: '15px', time: 3000, icon: 1});
            setTimeout(function () {
                if (result.data === 'submit[refresh]') {
                    parent.location.reload();
                    return;
                }
                if (result.data == null) {
                    window.location.reload();
                } else {
                    window.location.href = result.data
                }
            }, 2000);
        } else {
            layer.msg(result.msg, {offset: '15px', time: 3000, icon: 2});
        }
    };

    /* 提交表单数据 */
    $(document).on("click", ".ajax-submit", function (e) {
        e.preventDefault();
        var form = $(this).parents("form");
        var url = form.attr("action");
        var serializeArray = form.serializeArray();
        $.post(url, serializeArray, function (result) {
            if (result.data == null) {
                result.data = 'submit[refresh]';
            }
            $.fn.Messager(result);
        });
    });

    /* get方式异步 */
    $(document).on("click", ".ajax-post", function (e) {
        e.preventDefault();
        var msg = $(this).data("msg");
        if (msg !== undefined) {
            layer.confirm(msg + '？', {
                title: '提示',
                btn: ['确认', '取消']
            }, function () {
                $.get(e.target.href, function (result) {
                    $.fn.Messager(result);
                });
            });
        } else {
            $.get(e.target.href, function (result) {
                $.fn.Messager(result);
            });
        }
    });

    // post方式异步-操作状态
    $(".ajax-status").on("click", function (e) {
        e.preventDefault();
        var checked = [];
        var tdcheckbox = $(".timo-table td .timo-checkbox :checkbox:checked");
        if (tdcheckbox.length > 0) {
            tdcheckbox.each(function (key, val) {
                checked.push("ids=" + $(val).attr("value"));
            });
            $.post(e.target.href, checked.join("&"), function (result) {
                $.fn.Messager(result);
            });
        } else {
            layer.msg('请选择一条记录');
        }
    });


    /*/!* 添加/修改弹出层 *!/
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
        if (size === undefined || size === "auto") {
            size = ['50%', '80%'];
        } else if (size === "max") {
            size = ['100%', '100%'];
        } else if (size.indexOf(',') !== -1) {
            var split = size.split(",");
            size = [split[0] + 'px', split[1] + 'px'];
        }
        window.layerIndex = layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            maxmin: true,
            area: size,
            content: [url, 'on']
        });
    });

    /!* 关闭弹出层 *!/
    $(".close-popup").click(function (e) {
        e.preventDefault();
        parent.layer.close(window.parent.layerIndex);
    });


    /!* 下拉按钮组 *!/
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
*/

    var js = {
        ajaxReq: function (options) {

            var url = options.url;
            var method = options.type || "GET";
            var contentType = options.contentType || "application/x-www-form-urlencoded;charset=UTF-8";
            var data = options.data;
            if (contentType.indexOf("application/json") > -1) {
                data = JSON.stringify(data);
            }
            var async = options.async;
            if (async == undefined) {
                async = true;
            }
            var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
            $.ajax({
                url: url,
                type: method,
                contentType: contentType, //关键是要加上这行
                traditional: true, //这使json格式的字符不会被转码
                xhrFields: {
                    withCredentials: true
                },
                async: async,
                data: data,
                beforeSend: function (XMLHttpRequest) {
                    XMLHttpRequest.setRequestHeader("X-Requested-With", "XMLHttpRequest");
                },
                error: function (data) {
                    layer.close(index);
                    if (typeof options.error == "function") {
                        options.error(data, status, xhr)
                    }
                },
                success: function (data, status, xhr) {
                    layer.close(index);
                    try {
                        if (options.success) {
                            options.success(data);
                        }
                    } catch (e) {
                        js.error(e);
                    }
                }
            })
        },
        /*初始化表格*/
        tableInit: function (el, data, options) {
            if (!$(el)) return;
            if (!data) return;

            var thead = $(el).find("thead");
            var tbody = $(el).find("tbody");
            $(thead).find("tr.th").each(function (index, value) {
                var th = $(this);


            });

        }
    }
});