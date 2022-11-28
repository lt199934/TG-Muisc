$(function () {
    getSongs(1);
});

function getSongs(pageNum) {
    var data = {
        "pageNum": pageNum,
        "pageSize": 4,
    }
    console.log(pageNum);
    $.ajax({
        "url": "/allSongs",
        method: "post",
        data: data,
        success: function (data) {
            $(".pagination").empty();
            makePage(data);
            console.log("曲库信息", data);
            var songs = data.list;
            var content = "";
            for (var i = 0; i < songs.length; i++) {
                content += "<tr>";
                content += "<td>" + songs[i].songId + "</td>";
                content += "<td><img src=" + songs[i].album.albumImg + " class='img-responsive'></td>";
                content += "<td>" + songs[i].song + "</td>";
                content += "<td><a id='singer' href=javascript:void(0);>" + songs[i].singer.singerName + "</a><input type='hidden' id='singerId' value='" + songs[i].singer.singerId + "'></td>";
                content += "<td><span class='glyphicon glyphicon-heart-empty collect' style='font-size: 16px'></span></td>";
                content += "<td><a id='playOne' target='play' href=javascript:void(0);><span id='play' class='glyphicon glyphicon-play' style='color:cyan;font-size: 18px' ></span></a></td>";
                content += "<input type='hidden' id='songId' value=" + songs[i].songId + ">";
                content += "</tr>";
            }
            $("#music").html(content);
            //收藏歌曲状态
            $(".collect").each(function (i, n) {
                $.ajax({
                    url: "/songStatus",
                    method: "post",
                    data: {
                        "userId": user.userId,
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

//删除歌曲
function delSong(songid) {
    $.ajax({
        "url": "/delSong/" + songid,
        method: "post",
        success: function (data) {
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
    if (null != user) {
        $(this).removeClass("glyphicon-heart glyphicon-heart-empty collect");
        if (className == "glyphicon glyphicon-heart-empty collect") {
            console.log("你要收藏的歌曲id为" + num)
            $(this).css("color", "red");
            $(this).addClass("glyphicon glyphicon-heart collect");
            $.ajax({
                url: "/collectSongs",
                method: "post",
                data: {"userId": user.userId, "songId": num},
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
        toastr.options.onHidden = function () {
            location.href = "/login";
        }
        toastr.warning("请先登录！")
    }
})

//分页
function makePage(data) {
    if (data.list.length != 0) {
        console.log(data)
        var pageNums = data.navigatepageNums;
        if (data.pages == 1) {
            return;
        } else {
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:getSongs(" + 1 + ");'><span aria-hidden=\"true\">首页</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        //是否由上一页
        if (data.hasPreviousPage == true) {
            var pageNum = data.pageNum - 1;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Previous' href='javascript:getSongs(" + pageNum + ");'><span aria-hidden=\"true\">&laquo;</span></a>");
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
            $btn = $("<a href='javascript:getSongs(" + pageNum + ");'>" + pageNum + "</a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        if (data.hasNextPage == true) {
            var pageNum = data.pageNum + 1;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:getSongs(" + pageNum + ");'><span aria-hidden=\"true\">&raquo;</span></a>");
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
            $btn = $("<a aria-label='Next' href='javascript:getSongs(" + pageNum + ");'><span aria-hidden=\"true\">尾页</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
    } else {
        $(".pagination").empty();
    }

}

