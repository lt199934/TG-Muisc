var user=JSON.parse(sessionStorage.getItem("user"));
if(null==user){
    $("#login").css("display","block");
    $("#register").css("display","block");
    $("#logout").css("display","none");
    $("#personalPage").css("display","none");
    $("#headimg").css("display","none");
    $("#profile").css("display","none");
    $("#uc").html(" ");
}else {
    $("#login").css("display","none");
    $("#register").css("display","none");
    $("#logout").css("display","block");
    $("#personalPage").css("display","block");
    $("#headimg").css("display","block");
    $("#profile").css("display","block");
    $("#headimg").attr("src",user.headImg);
    $("#uc").html(user.nickName);
}