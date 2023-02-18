var userId=JSON.parse(sessionStorage.getItem("userId"));
if(null==userId){
    $("#login").css("display","block");
    $("#register").css("display","block");
    $("#logout").css("display","none");
    $("#personalPage").css("display","none");
    $("#headimg").css("display","none");
    $("#profile").css("display","none");
    $("#uc").html(" ");
}else {
    $.ajax({
        url: "/getUserInfo/" + userId,
        method: "post",
        success: function (data) {
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