$(function () {
    getSongList(1);
    if (null != userId) {
        isCollectedSongList();
    }
});

function getSongList(pageNum) {
    var data = {
        "pageNum": pageNum, "pageSize": 5,
    }
    console.log(getUrlParam("songListId"))
    $.ajax({
        url: "/songList/" + getUrlParam("songListId"), method: "post", data: data, success: function (data) {
            $("#fenLei").empty();
            // $("#songs").empty();
            console.log("歌单详情", data);
            // makePage(data,'getSongList');
            var songList = data;
            var songDto = data.songDto;
            $("#songListImg").attr("src", songList.imgUrl);
            $(".songList__name").html(songList.songList + '<span class="badge" style="margin-top: -7px;">' + songDto.length + '</span>');
            $("#introduction").html(songList.introduction);
            $("#time").html(songList.time);
            $(".songList__number").html(songList.songDto.length + "首歌");
            $("#user").html("<a href=/user?userId=" + songList.userId + ">" + songList.createUser + "</a>");
            var content = "";
            for (var j = 0; j < data.fenLeis.length; j++) {
                content += data.fenLeis[j].content + " ,";
            }
            if (data.fenLeis.length !== 0) {
                $("#fenLei").append(content.substring(0, content.lastIndexOf(",")));
            } else {
                $("#fenLei").html("未分类");
            }
            var con = "";
            for (var i = 0; i < songDto.length; i++) {
                con += "<tr class='song'>";
                con += "<td>" + (i + 1) + "</td>";
                con += "<td class='center-block'><img style='width: 64px;height: 64px' class='img-rounded' src=" + songDto[i].albumUrl + " /></td>";
                con += "<td>" + songDto[i].song + "</td>";
                con += "<td><a href='/music/user/singer/singer.html?singerId=" + songDto[i].singerId + "'>" + songDto[i].singerName + "</a></td>";
                con += "<td><a href='/music/user/album/album.html?albumId=" + songDto[i].albumId + "'>" + songDto[i].albumName + "</td>";
                con += "<td><span title='收藏音乐' class='glyphicon glyphicon-heart-empty collect' style='font-size: 16px'></span></td>";
                con += "<td><a id='playOne' class='glyphicon glyphicon-play' target='play' style='color:cyan;font-size: 18px' title='播放音乐' href=javascript:void(0);></a></td>";
                con += "<input type='hidden' value=" + songDto[i].songId + ">"
                con += "</tr>";
            }
            $("#music").html(con);
            if (null != userId) {
                $(".collect").each(function (i, n) {
                    $.ajax({
                        url: "/user/songStatus", method: "post", data: {
                            "userId": userId, "songId": songDto[i].songId
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
        }
    });
}

//收藏歌单状态
function isCollectedSongList() {
    $.ajax({
        url: "/user/songListStatus",
        method: "post",
        data: {"userId": userId, "songListId": getUrlParam("songListId")},
        success: function (data) {
            console.log(data);
            if (data === 1) {
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
        if (className === "glyphicon glyphicon-heart-empty") {
            $(this).find("span").css("color", "red");
            $(this).find("span").addClass("glyphicon glyphicon-heart");
            $.ajax({
                url: "/user/collectSongLists",
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
                url: "/user/delCollectSongLists",
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
        isLogin();
    }
})
//收藏歌曲
$("#music").on("click", ".collect", function () {
    var num = $(this).parent().parent().find("input[type='hidden']").val();
    var className = $(this).attr("class");
    if (null != userId) {
        console.log(className)
        $(this).removeClass("glyphicon-heart glyphicon-heart-empty collect");
        if (className === "glyphicon glyphicon-heart-empty collect") {
            console.log("你要收藏的歌曲id为" + num)
            $(this).css("color", "red");
            $(this).addClass("glyphicon glyphicon-heart collect");
            $.ajax({
                url: "/user/collectSongs",
                method: "post",
                data: {"userId": userId, "songId": num},
                success: function (data) {
                    console.log(data);
                    if (data === 1) {
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
                url: "/user/delCollectedSongs",
                method: "post",
                data: {"userId": userId, "songId": num},
                success: function (data) {
                    console.log(data);
                    if (data === 1) {
                        toastr.info("取消收藏")
                    }
                }
            });
        }
    } else {
        isLogin();
    }
})

//播放量
$("#music").on("click", "#playOne", function () {
    $("#player").css("display","block");
    var songId = $(this).parent().parent().find("input[type='hidden']").val();
    console.log(songId);
    $(this).attr("href", "/play?temp=song&songId=" + songId);
    $.ajax({
        url: "/song/updatePlayCount/" + songId, method: "post", success: function (data) {
            console.log(data);
            if (data === 1) {
                console.log("播放量加1");
            }
        }
    });
});

//播放全部
$("#playAll").click(function () {
    $("#player").css("display","block");
    $(this).attr("href", "/play?temp=songList&songListId=" + getUrlParam("songListId"));
})
