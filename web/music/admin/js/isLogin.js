var user=JSON.parse(sessionStorage.getItem("admin"));
// console.log(user)
if(null==user){
 window.location.href="/login";
}

$("#logout").click(function(){
    var message=confirm("是否退出登录?");
    if (message==true){
        var logout=sessionStorage.removeItem("admin");
        var user=JSON.parse(sessionStorage.getItem("admin"));
        console.log(user);
        window.location.href="/login";
    }

});
