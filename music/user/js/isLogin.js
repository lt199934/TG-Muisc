var user=JSON.parse(sessionStorage.getItem("user"));
if(null==user){
    $("#myLoginModal").modal("show");
    $("#login").css("display","block");
    $("#register").css("display","block");
    $("#logout").css("display","none");
    $("#personalPage").css("display","none");
    $("#headimg").css("display","none");
    $("#uc").html(" ");
}else {
    console.log(user.headImg);
    $("#login").css("display","none");
    $("#register").css("display","none");
    $("#logout").css("display","block");
    $("#personalPage").css("display","block");
    $("#headimg").css("display","block");
    $("#headimg").attr("src",user.headImg);
    $("#uc").html("用户："+user.account);
}

$("#logout").click(function(){
    var message=confirm("是否退出登录?");
    if (message==true){
        var logout=sessionStorage.removeItem("user");
        var user=JSON.parse(sessionStorage.getItem("user"));
        console.log(user);
        window.location.href="http://localhost:8080/musicwebsite/music/user/index.html";
    }

});
