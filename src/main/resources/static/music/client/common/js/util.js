// 封装获取参数方法
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

// 消息提示框
toastr.options = {
    closeButton: true,
    debug: false,
    progressBar: true,
    positionClass: "toast-top-right",
    onclick: null,
    showDuration: "1000",
    hideDuration: "1000",
    timeOut: "3000",
    extendedTimeOut: "1000",
    showEasing: "swing",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut",
    closeMethod: "fadeOut",
};


//隐藏地址
$("#albums").on("click", "a", function () {
    var albumId = $(this).parent().parent().parent().find("#albumId").val();
    $(this).attr("href", "/albumDetail?albumId=" + albumId);
});

$("#songLists").on("click", "a", function () {
    // alert(window.location.href);

    var songListId = $(this).parent().find("#songListId").val();
    $(this).attr("href", "/songListDetail?songListId=" + songListId);
});

$("#music").on("click", "#playOne", function () {
    // alert(window.location.href);
    // var url=window.location.href;
    // alert( url.substring(0,url.indexOf("/")+1))
    $("#player").css("display","block");
    var songId = $(this).parent().parent().find("#songId").val();
    $(this).attr("href", "/play?temp=song&songId=" + songId);
});

$("#music").on("click", "#singer", function () {
    var singerId = $(this).parent().parent().find("#singerId").val();
    $(this).attr("href", "/singerDetail?singerId=" + singerId);
});
$("#singerList").on("click", "#home", function () {
    var singerId = $(this).parent().parent().parent().parent().find("#singerId").val();
    $(this).attr("href", "/singerDetail?singerId=" + singerId);
});

// 初始化菜单
function initNav() {
    $("#login").css("display", "block");
    $("#register").css("display", "block");
    $("#logout").css("display", "none");
    $("#personalPage").css("display", "none");
    $("#headimg").css("display", "none");
    $("#profile").css("display", "none");
    $("#uc").html(" ");
}

initNav();

// 登录以后
function isLoginNav(data,num) {
    if(num == 0){
        toastr.success("欢迎您" + data.nickName + "回来(*∩_∩*)")
    }
    $("#login").css("display", "none");
    $("#register").css("display", "none");
    $("#logout").css("display", "block");
    $("#personalPage").css("display", "block");
    $("#headimg").css("display", "block");
    $("#profile").css("display", "block");
    $("#headimg").attr("src", data.headImg);
    $("#uc").html(data.nickName);
}

var userId = JSON.parse(localStorage.getItem("userId"));

// 未登录
function isLogin() {
    toastr.options.onHidden = function () {
        location.href = "/login";
    }
    toastr.warning("请先登录！");
}

// 首页不需要强制登录
function isFirst(num) {
    if (null == userId) {
        initNav();
    } else {
        $.ajax({
            url: "/user/getUserInfo/" + userId, method: "get", success: function (data) {
                console.log(data)
                if ("user is not exited" !== $.trim(data)) {
                    isLoginNav(data,num);
                } else {
                    localStorage.removeItem("userId");
                    initNav();
                }
            }
        });
    }
}

// 退出登录
$("#logout").click(function () {
    var message = confirm("是否退出登录?");
    if (message === true) {
        $.ajax({
            url: "/user/logout", data: {"userId": userId}, method: "post", success: function (data) {
                location.href = data;
                localStorage.removeItem("userId");
            }
        });
    }
});

//分页
function makePage(data,type) {
    if (data.list.length != 0) {
        console.log(data)
        var pageNums = data.navigatepageNums;
        if (data.pages == 1) {
            return;
        } else {
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:"+type+"(" + 1 + ");'><span aria-hidden=\"true\">首页</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        //是否由上一页
        if (data.hasPreviousPage == true) {
            var pageNum = data.pageNum - 1;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Previous' href='javascript:"+type+"(" + pageNum + ");'><span aria-hidden=\"true\">&laquo;</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        } else {
            $li = $("<li class='disabled'></li>");
            $btn = $("<a aria-label='Previous'><span aria-hidden=\"true\">&laquo;</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        // 页数
        for (var i = 0; i < pageNums.length; i++) {
            var pageNum = pageNums[i];
            $li = $("<li></li>");
            $btn = $("<a href='javascript:"+type+"(" + pageNum + ");'>" + pageNum + "</a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        if (data.hasNextPage == true) {
            var pageNum = data.pageNum + 1;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:"+type+"(" + pageNum + ");'><span aria-hidden=\"true\">&raquo;</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        } else {
            $li = $("<li class='disabled'></li>");
            $btn = $("<a aria-label='Next'><span aria-hidden=\"true\">&raquo;</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }

        if (data.isLastPage != true) {
            var pageNum = data.pages;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:"+type+"(" + pageNum + ");'><span aria-hidden=\"true\">尾页</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
    } else {
        $(".pagination").empty();
    }
}

