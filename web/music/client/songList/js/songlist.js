$(function () {
    getSongList(1);
    isCollectedSongList();
});
var userId = JSON.parse(sessionStorage.getItem("userId"));//获取用户信息
function getSongList(pageNum) {
    var data = {
        "pageNum": pageNum,
        "pageSize": 2,
    }
    console.log(getUrlParam("songListId"))
    $.ajax({
        url: "/songList/" + getUrlParam("songListId"),
        method: "post",
        data: data, success: function (data) {
            $("#fenLei").empty();
            // $("#songs").empty();
            console.log("歌单详情", data);
            var songList = data;
            var songDto = data.songDto;
            $("#songListImg").attr("src", songList.imgUrl);
            $(".songList__name").html(songList.songList+'<span class="badge" style="margin-top: -16px;">'+songDto.length+'</span>');
            $("#introduction").html(songList.introduction);
            $("#time").html(songList.time);
            $("#user").html("<a href=/user?userId=" + songList.userId + ">" + songList.createUser + "</a>");
            var content = "";
            for (var j = 0; j < data.fenLeis.length; j++) {
                content += data.fenLeis[j].content + " ,";
            }
            if(data.fenLeis.length != 0){
                $("#fenLei").append(content.substring(0, content.lastIndexOf(",")));
            }else{
                $("#fenLei").html("未分类");
            }

            var con = "";
            for (var i = 0; i < songDto.length; i++) {
                con += "<tr class='song'>";
                con += "<td>" + (i + 1) + "</td>";
                con += "<td class='text-center'><img class='img-responsive center-block' src=" + songDto[i].albumUrl + " /></td>";
                con += "<td>" + songDto[i].song + "</td>";
                con += "<td><a href='/music/user/singer/singer.html?singerId=" + songDto[i].singerId + "'>" + songDto[i].singerName + "</a></td>";
                con += "<td><a href='/music/user/album/album.html?albumId=" + songDto[i].albumId + "'>" + songDto[i].albumName + "</td>";
                con += "<td><span title='收藏音乐' class='glyphicon glyphicon-heart-empty collect' style='font-size: 16px'></span></td>";
                con += "<td><a id='playOne' target='play' href=/play?temp=song&songId=" + songDto[i].songId + "><span id='play' title='播放音乐' class='glyphicon glyphicon-play' style='color:cyan;font-size: 18px' ></span></a></td>";
                con += "<input type='hidden' value=" + songDto[i].songId + ">"
                con += "</tr>";
            }
            $("#songs").html(con);
            $(".collect").each(function (i,n){
                $.ajax({
                    url: "/songStatus",
                    method: "post",
                    data: {
                        "userId": userId,
                        "songId": songDto[i].songId
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

//分页
function makePage(data) {
    if (data.list.length != 0) {
        console.log(data)
        var pageNums = data.navigatepageNums;
        if (data.pages == 1) {
            return;
        } else {
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:getSongList(" + 1 + ");'><span aria-hidden=\"true\">首页</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        //是否由上一页
        if (data.hasPreviousPage == true) {
            var pageNum = data.pageNum - 1;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Previous' href='javascript:getSongList(" + pageNum + ");'><span aria-hidden=\"true\">&laquo;</span></a>");
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
            $btn = $("<a href='javascript:getSongList(" + pageNum + ");'>" + pageNum + "</a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        if (data.hasNextPage == true) {
            var pageNum = data.pageNum + 1;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:getSongList(" + pageNum + ");'><span aria-hidden=\"true\">&raquo;</span></a>");
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
            $btn = $("<a aria-label='Next' href='javascript:getSongList(" + pageNum + ");'><span aria-hidden=\"true\">尾页</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
    } else {
        $(".pagination").empty();
    }

}

//收藏歌单状态
function isCollectedSongList() {
    $.ajax({
        url: "/songListStatus",
        method: "post",
        data: {"userId": userId, "songListId": getUrlParam("songListId")},
        success: function (data) {
            console.log(data);
            if (data == 1) {
                $("#collectSongList").find("span").removeClass("glyphicon-heart-empty");
                $("#collectSongList").find("span").css("color", "red");
                $("#collectSongList").find("span").addClass("glyphicon glyphicon-heart");
            }
        }
    });
}

// 收藏歌单
$("#collectSongList").click(function () {
    console.log("你要收藏的歌单id为" + getUrlParam("songListId"))
    var className = $(this).find("span").attr("class");
    console.log(className)
    if (null != userId) {
        $(this).find("span").removeClass("glyphicon-heart glyphicon-heart-empty");
        if (className == "glyphicon glyphicon-heart-empty") {
            $(this).find("span").css("color", "red");
            $(this).find("span").addClass("glyphicon glyphicon-heart");
            $.ajax({
                url: "/collectSongLists",
                method: "post",
                data: {"userId": userId, "songListId": getUrlParam("songListId")},
                success: function (data) {
                    console.log(data);
                    if (data == 1) {
                        toastr.success("收藏成功")
                    }

                }
            });
        } else {
            $(this).find("span").css("color", "");
            $(this).find("span").addClass("glyphicon glyphicon-heart-empty");
            $.ajax({
                url: "/delCollectSongLists",
                method: "post",
                data: {"userId": userId, "songListId": getUrlParam("songListId")},
                success: function (data) {
                    console.log(data);
                    if (data == 1) {
                        toastr.info("取消收藏")
                    }

                }
            });
        }
    } else {
        alert("请先登录");
        window.location.href = "/music/user/index.html";
        $("#myLoginModal").modal("show");
    }
})
//收藏歌曲
$("#songs").on("click", ".collect", function () {
    var num = $(this).parent().parent().find("input[type='hidden']").val();
    var className = $(this).attr("class");
    if (null != userId) {
        console.log(className)
        $(this).removeClass("glyphicon-heart glyphicon-heart-empty collect");
        if (className == "glyphicon glyphicon-heart-empty collect") {
            console.log("你要收藏的歌曲id为" + num)
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
            console.log(className)
            console.log("你要取消收藏的歌曲id为" + num)
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
$("#song").on("click", "#playOne", function () {
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

//播放全部
$("#playAll").click(function () {
    $(this).attr("href", "/play?temp=songList&songListId=" + getUrlParam("songListId"));
})