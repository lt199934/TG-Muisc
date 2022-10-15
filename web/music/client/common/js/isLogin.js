var user=JSON.parse(sessionStorage.getItem("user"));
if(null==user){
    toastr.options.onHidden = function() {
        location.href="/login";
    }
    toastr.warning("请先登录！")
}else {
    toastr.success("欢迎您"+user.nickName+"回来(*∩_∩*)")
}
$("#logout").click(function(){
    var message=confirm("是否退出登录?");
    if (message==true){
        var logout=sessionStorage.removeItem("user");
        var user=JSON.parse(sessionStorage.getItem("user"));
        console.log(user);
        location.replace("/login");
    }
});
