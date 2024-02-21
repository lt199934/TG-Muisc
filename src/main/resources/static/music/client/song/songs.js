$(function () {
    isFirst(1);
    getSongs(1);
});

function getSongs(pageNum) {
    var data = {
        "pageNum": pageNum, "pageSize": 12,
    }
    console.log(pageNum);
    $.ajax({
        "url": "/song/all", method: "post", data: data, success: function (data) {
            $(".pagination").empty();
            $("#music").empty();
            $(".num").html(data.total);
            makePage(data,'getSongs');
            console.log("曲库信息", data);
            var songs = data.list;
            for (var i = 0; i < songs.length; i++) {
                let content = "<div class='col-xs-6 col-sm-4 col-md-3 media' style='padding: 15px;padding-top: 0;'>";
                content += "<a class='pull-left' href='#'><img alt='' class='media-object img-rounded' src='"+songs[i].album.albumImg+"' style='width: 64px;height: 64px;'></a>";
                content += "<div style='padding: 10px;' class='media-body mx-auto'>";
                content += "<div>";
                content += "<h5 class='media-heading''>"+songs[i].song+"</h5>";
                content += "<small class='text-muted'>"+songs[i].singer.singerName+"</small>";
                content += "<div>";
                content += "<a id='playOne' target='play' style='color:cyan;font-size: 16px;text-decoration: none' class='glyphicon glyphicon-play' href=javascript:void(0);></a>";
                content += "<span class='glyphicon glyphicon-heart-empty collect' style='font-size: 16px;margin-left: 5px;cursor: pointer'></span>";
                content += "</div>'"
                content += "</div>";
                content += "</div>";
                content += "<input type='hidden' id='songId' value=" + songs[i].songId + ">";
                content += "</div>";
                $("#music").append(content);
            }
            if (null != userId) {
                //收藏歌曲状态
                $(".collect").each(function (i, n) {
                    $.ajax({
                        url: "/songStatus", method: "post", data: {
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

//删除歌曲
function delSong(songid) {
    $.ajax({
        "url": "/delSong/" + songid, method: "post", success: function (data) {
            console.log(data);
            if (data == 1) {
                alert("删除成功！");
                getSongs();
            }
        }
    });
}

//收藏歌曲
$("#music").on("click", ".collect", function () {
    var num = $(this).parent().parent().find("input[type='hidden']").val();
    var className = $(this).attr("class");
    if (null != userId) {
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
                        toastr.success("收藏成功！")
                    }
                }
            });
        } else {
            console.log("你要取消收藏的歌曲id为" + num)
            $(this).css("color", "");
            $(this).addClass("glyphicon glyphicon-heart-empty collect");
            $.ajax({
                url: "/delCollectedSongs",
                method: "post",
                data: {"userId": user.userId, "songId": num},
                success: function (data) {
                    console.log(data);
                    if (data == 1) {
                        toastr.info("取消收藏！")
                    }

                }
            });
        }
    } else {
        isLogin();
    }
})

$(".pagination").on("click", "a", function () {
    // alert($(this).html())
    $(".pagination li").removeClass();
    $(this).parent().addClass("active");
});

