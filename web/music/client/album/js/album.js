$(function () {
    getAlbum(1);
    isCollectedAlbum();
});
//判断登录状态
var userId = JSON.parse(sessionStorage.getItem("userId"));//获取用户信息
console.log(userId)
if (null == userId) {
    toastr.options.onHidden = function () {
        location.href = "/login";
    }
    toastr.warning("请先登录！")
}
//
function getAlbum(pageNum) {
    var data = {
        "pageNum": pageNum,
        "pageSize": 2,
    }
    $.ajax({
        "url": "/albums/" + getUrlParam("albumId"),
        method: "post",
        data: data,
        success: function (data) {
            // makePage(data);
            console.log("专辑详情", data);
            var album = data;
            var songs = data.songs;
            var singer = data.singer;
            $("#songListImg").attr("src", album.albumImg);
            $(".songList__name").html('<span class="glyphicon glyphicon-cd"></span>'+album.album+'<span class="badge" style="margin-top: -16px;">'+songs.length+'</span>');
            $(".singer").html("歌手：<a href='/singerDetail?singerId=" + singer.singerId + "'>" + singer.singerName + "</a>");
            $(".songList__number").html(album.count + "首歌");
            $("#introduction").html(album.introduction);
            $("#time").html(album.time);
            $("#company").html(album.company);
            var content = "";
            for (var i = 0; i < songs.length; i++) {
                content += "<tr>";
                content += "<td>" + (i + 1) + "</td>";
                content += "<td>" + songs[i].song + "</td>";
                content += "<td><span title='收藏音乐' class='glyphicon glyphicon-heart-empty collect' style='font-size: 16px'></span></td>";
                content += "<td><a id='playOne' target='play' href=/play?temp=song&songId=" + songs[i].songId + "><span id='play' title='播放音乐' class='glyphicon glyphicon-play' style='color:cyan;font-size: 18px' ></span></a><audio src=" + songs[i].url + "></audio></td>";
                content += "<input type='hidden' value=" + songs[i].songId + ">";
                content += "</tr>";
            }
            $("#songs").html(content);
            //收藏歌曲状态
            $(".collect").each(function (i,n){
                $.ajax({
                    url: "/songStatus",
                    method: "post",
                    data: {
                        "userId": userId,
                        "songId": songs[i].songId
                    }, success: function (data) {
                        console.log(data);
                        if (data === 1) {
                            $(n).removeClass("glyphicon-heart-empty");
                            $(n).css("color", "red");
                            $(n).addClass("glyphicon glyphicon-heart");
                        }
                    }
                });
            });
        }
    });
}
//收藏专辑状态
function isCollectedAlbum() {
    $.ajax({
        url: "/albumStatus",
        method: "post",
        data: {"userId": userId, "albumId": getUrlParam("albumId")},
        success: function (data) {
            console.log(data);
            if (data == 1) {
                var className = $("#collectAlbum").find("span").attr("class");
                $("#collectAlbum").find("span").removeClass("glyphicon-heart-empty");
                $("#collectAlbum").find("span").css("color", "red");
                $("#collectAlbum").find("span").addClass("glyphicon glyphicon-heart");
            }
        }
    });
}

// 收藏歌单
$("#collectAlbum").click(function () {
    var className = $(this).find("span").attr("class");
    if (null != user) {
        $(this).find("span").removeClass("glyphicon-heart glyphicon-heart-empty");
        if (className == "glyphicon glyphicon-heart-empty") {
            console.log("你要收藏的专辑id为" + getUrlParam("albumId"));
            $(this).find("span").css("color", "red");
            $(this).find("span").addClass("glyphicon glyphicon-heart");
            $.ajax({
                url: "/collectAlbums",
                method: "post",
                data: {"userId": userId, "albumId": getUrlParam("albumId")},
                success: function (data) {
                    console.log(data);
                    if (data == 1) {
                        toastr.success("收藏成功")
                    }
                }
            });
        } else {
            $(this).removeClass("glyphicon-heart-empty");
            console.log("你要取消收藏的专辑id为" + getUrlParam("albumId"));
            $(this).find("span").addClass("glyphicon glyphicon-heart-empty");
            $(this).find("span").css("color", "");
            $.ajax({
                url: "/delCollectAlbums",
                method: "post",
                data: {"userId": userId, "albumId": getUrlParam("albumId")},
                success: function (data) {
                    console.log(data);
                    if (data == 1) {
                        toastr.info("取消收藏")
                    }
                }
            });
        }
    } else {
        toastr.options.onHidden = function () {
            location.href = "/login";
        }
        toastr.warning("请先登录！")
    }
});

//收藏歌曲
$("#songs").on("click", ".collect", function () {
    var num = $(this).parent().parent().find("input[type='hidden']").val();
    var className = $(this).attr("class");
    if (null != user) {
        console.log(className)
        $(this).removeClass("glyphicon-heart glyphicon-heart-empty collect");
        if (className == "glyphicon glyphicon-heart-empty collect") {
            console.log("你要收藏的歌曲id为" + num);
            $(this).css("color", "red");
            $(this).addClass("glyphicon glyphicon-heart collect");
            $.ajax({
                url: "/collectSongs",
                method: "post",
                data: {"userId": userId, "songId": num},
                success: function (data) {
                    console.log(data);
                    if (data == 1) {
                        toastr.success("收藏成功")
                    }

                }
            });
        } else {
            console.log("你要取消收藏的歌曲id为" + num)
            console.log(className)
            $(this).css("color", "");
            $(this).addClass("glyphicon glyphicon-heart-empty collect");
            $.ajax({
                url: "/delCollectedSongs",
                method: "post",
                data: {"userId": userId, "songId": num},
                success: function (data) {
                    console.log(data);
                    if (data == 1) {
                        toastr.info("取消收藏")
                    }
                }
            });
        }
    } else {
        toastr.options.onHidden = function () {
            location.href = "/login";
        }
        toastr.warning("请先登录！")
    }

})
//播放量
$("#albums").on("click", "#playOne", function () {
    var num = $(this).parent().parent().find("input[type='hidden']").val();
    $.ajax({
        url: "/updatePlayCount/" + num, method: "post", success: function (data) {
            console.log(data);
            if (data == 1) {
                console.log("播放量加1");
            }
        }
    });
});

//分页
function makePage(data) {
    if (data.list.length != 0) {
        console.log(data)
        var pageNums = data.navigatepageNums;
        if (data.pages == 1) {
            return;
        } else {
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:getSongLists(" + 1 + ");'><span aria-hidden=\"true\">首页</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        //是否由上一页
        if (data.hasPreviousPage == true) {
            var pageNum = data.pageNum - 1;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Previous' href='javascript:getSongLists(" + pageNum + ");'><span aria-hidden=\"true\">&laquo;</span></a>");
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
            $btn = $("<a href='javascript:getSongLists(" + pageNum + ");'>" + pageNum + "</a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        if (data.hasNextPage == true) {
            var pageNum = data.pageNum + 1;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:getSongLists(" + pageNum + ");'><span aria-hidden=\"true\">&raquo;</span></a>");
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
            $btn = $("<a aria-label='Next' href='javascript:getSongLists(" + pageNum + ");'><span aria-hidden=\"true\">尾页</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
    } else {
        $(".pagination").empty();
    }

}

//播放全部
$("#playAll").click(function () {
    $(this).attr("href", "/play?temp=album&albumId=" + getUrlParam("albumId"));
})

