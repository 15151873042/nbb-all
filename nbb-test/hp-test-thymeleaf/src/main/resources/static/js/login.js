if(window.top!==window.self){window.top.location=window.location};
 layui.use(['element',"form","layer"], function () {
    var $ = layui.jquery;

    var form = layui.form;
    form.render();

    $(document).on('click', '.captcha-img', function () {
        var src = this.src.split("?")[0];
        this.src = src + "?type=char"+ "&" + Math.random();;
    });

    $(document).on('click', '.ajax-login', function (e) {
        e.preventDefault();
        var form = $(this).parents("form");
        var url = form.attr("action");
        var serializeArray = form.serializeArray();
        if (serializeArray != null && serializeArray.length > 0) {
            for (var i = 0; i < serializeArray.length; i++) {
                if (serializeArray[i].name == "password") {
                    serializeArray[i].value = encryption(serializeArray[i].value);
                    break;
                }
            }
        }
        js.ajaxReq({
            url:url,
            type:"POST",
            data:serializeArray,
            success:function(result){
                if(result.code != 0){
                    $('.captcha-img').click();
                    js.showErrorMessage(result.msg)
                } else {
                    location.href = js.ctxPath("index");
                }
            }
        })
    })
});