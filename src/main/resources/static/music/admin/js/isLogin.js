let user = JSON.parse(sessionStorage.getItem("admin"));
// console.log(user)
if (null == user) {
    window.location.href = "/admin";
}

$("#logout").click(function () {
    let message = confirm("是否退出登录?");
    if (message === true) {
        sessionStorage.removeItem("admin");
        user = JSON.parse(sessionStorage.getItem("admin"));
        console.log(user);
        window.location.href = "/admin";
    }

});
