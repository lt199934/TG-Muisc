let admin = JSON.parse(localStorage.getItem("admin"));
// console.log(user)
if (null == admin) {
    window.location.href = "/admin";
}

$.ajax({
    url: "/admin/isLogin",
    method: "get",
    data: {"superId": admin},
    success: function (res) {
        if (!res) {
            localStorage.removeItem("admin");
            window.location.href = "/admin";
        }
    }
})

$("#logout").click(function () {
    let message = confirm("是否退出登录?");
    if (message === true) {
        sessionStorage.removeItem("admin");
        user = JSON.parse(sessionStorage.getItem("admin"));
        console.log(user);
        window.location.href = "/admin";
    }

});
