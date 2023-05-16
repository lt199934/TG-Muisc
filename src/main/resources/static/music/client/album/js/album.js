$(function () {
    getAlbum(1);
    if (null != userId) {
        isCollectedAlbum();
    }
});

//获取详细专辑信息
function getAlbum(pageNum) {
    var data = {
        "pageNum": pageNum, "pageSize": 2,
    }
    $.ajax({
        "url": "/albums/" + getUrlParam("albumId"), method: "post", data: data, success: function (data) {
            // makePage(data);
            console.log("专辑详情", data);
            var album = data;
            var songs = data.songs;
            var singer = data.singer;
            $("#songListImg").attr("src", album.albumImg);
            $(".songList__name").html('<span class="glyphicon glyphicon-cd"></span>' + album.album + '<span class="badge" style="margin-top: -16px;">' + songs.length + '</span>');
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
                content += "<td><a id='playOne' class='glyphicon glyphicon-play' target='play' style='color:cyan;font-size: 18px' title='播放音乐' href=javascript:void(0);></a></td>";
                content += "<input type='hidden' value=" + songs[i].songId + ">";
                content += "</tr>";
            }
            $("#albums").html(content);
            if (null != userId) {
                //收藏歌曲状态
                $(".collect").each(function (i, n) {
                    $.ajax({
                        url: "/user/songStatus", method: "post", data: {
                            "userId": userId, "songId": songs[i].songId
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

//收藏专辑状态
function isCollectedAlbum() {
    $.ajax({
        url: "/user/albumStatus",
        method: "post",
        data: {"userId": userId, "albumId": getUrlParam("albumId")},
        success: function (data) {
            console.log(data);
            if (data === 1) {
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
    if (null != userId) {
        $(this).find("span").removeClass("glyphicon-heart glyphicon-heart-empty");
        if (className === "glyphicon glyphicon-heart-empty") {
            console.log("你要收藏的专辑id为" + getUrlParam("albumId"));
            $(this).find("span").css("color", "red");
            $(this).find("span").addClass("glyphicon glyphicon-heart");
            $.ajax({
                url: "/user/collectAlbums",
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
                url: "/user/delCollectAlbums",
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
        isLogin();
    }
});

//收藏歌曲
$("#albums").on("click", ".collect", function () {
    var num = $(this).parent().parent().find("input[type='hidden']").val();
    var className = $(this).attr("class");
    if (null != userId) {
        console.log(className)
        $(this).removeClass("glyphicon-heart glyphicon-heart-empty collect");
        if (className === "glyphicon glyphicon-heart-empty collect") {
            console.log("你要收藏的歌曲id为" + num);
            $(this).css("color", "red");
            $(this).addClass("glyphicon glyphicon-heart collect");
            $.ajax({
                url: "/user/collectSongs",
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
                url: "/user/delCollectedSongs",
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
        isLogin();
    }
})
//播放量
$("#albums").on("click", "#playOne", function () {
    $("#player").css("display","block");
    var songId = $(this).parent().parent().find("input[type='hidden']").val();
    console.log(songId);
    $(this).attr("href", "/play?temp=song&songId=" + songId);
    $.ajax({
        url: "/updatePlayCount/" + songId, method: "post", success: function (data) {
            console.log(data);
            if (data == 1) {
                console.log("播放量加1");
            }
        }
    });
});

//播放全部
$("#playAll").click(function () {
    $(this).attr("href", "/play?temp=album&albumId=" + getUrlParam("albumId"));
})

