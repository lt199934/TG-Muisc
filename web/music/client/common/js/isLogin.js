var userId=JSON.parse(sessionStorage.getItem("userId"));
if(null==userId){
    toastr.options.onHidden = function() {
        location.href="/login";
    }
    toastr.warning("请先登录！")
}else {
    $.ajax({
        url: "/getUserInfo/" + userId,
        method: "post",
        success: function (data) {
            toastr.success("欢迎您" + data.nickName + "回来(*∩_∩*)")
            $("#login").css("display", "none");
            $("#register").css("display", "none");
            $("#logout").css("display", "block");
            $("#personalPage").css("display", "block");
            $("#headimg").css("display", "block");
            $("#profile").css("display", "block");
            $("#headimg").attr("src", data.headImg);
            $("#uc").html(data.nickName);
        }
    });
}
$("#logout").click(function(){
    var message=confirm("是否退出登录?");
    if (message==true){
        sessionStorage.removeItem("userId");
        location.href="/logout";
    }
});
