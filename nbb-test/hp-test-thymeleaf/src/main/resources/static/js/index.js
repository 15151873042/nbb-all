layui.define(['element', 'form', 'layer', 'upload'], function (exports) {
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

    var refreshThisTabs = function () {
        var iframe = $(APP_BODY).find(".layui-show").find("iframe")[0];
        if (iframe!=undefined) {
            iframe.contentWindow.location.reload(true);
        }
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
    $("#refreshThisTabs").click(function () {
        refreshThisTabs();
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
        if (url.indexOf("?") > 0) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (text == undefined || text == "") {
            var item = $('[lay-url="' + url + '"]');
            text = item.attr("lay-icon") === 'true' ? item.html()
                : item.children(".layui-nav-title").text();
            //如果依然为空值，则是个人信息或修改密码
            if (text == '' || text == undefined) {
                text = item.data("title");
            }
        }
        url = url ;
        if (text != undefined) {
            localStorage.setItem("portraitTabName", text);
        } else {
            text = localStorage.getItem("portraitTabName");
            if (text == undefined) {
                text = "新标签页"
            }
        }
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
        var target = $this.attr('lay-target');
        if (target == "_blank") {
           window.open(url,target);
        } else {
            tabs(url, $this.text());
        }
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
    $(".layadmin-iframe").attr("src", js.ctxPath("/first"));
    window.onhashchange();


    /* 初始化时展开子菜单 */
    $("dd.layui-this").parents(".layui-nav-child").parent()
        .addClass("layui-nav-itemed");

    /* 刷新iframe页面 */
    $(".refresh-btn").click(function () {
        location.reload();
    });

    $(".addTab").click(function () {

        var url = $(this).data("url");
        var title = $(this).data("title");
        tabs(url,title);
    })


    exports('addTab', function (url,title) {//函数参数
        tabs(url, title);
    });
})
