$(function () {
    isFirst(0);
    getAlbums(1);
    getSongLists(1);
    getSongs(1);
    getSingers(1);
});
var col = document.getElementById("collect");

//精选歌单
function getSongLists(pageNum) {
    var data = {
        "pageNum": pageNum, "pageSize": 4,"fenId": 0,
    }
    $.ajax({
        "url": "/songList/all", method: "post", data: data, success: function (data) {
            $("#songLists").empty();
            var songList = data.list;
            for (var i = 0; i < songList.length; i++) {
                let content = "<div  class='col-xs-6 col-sm-6 col-md-3 filtr-item' data-category='2, 3' data-sort='欧美/流行'>";
                content += "<a target='_blank' rel='opener'  href=javascript:void(0);>";
                content +=    "<img class='img-responsive img-rounded' src='" + songList[i].imgUrl + "' alt='sample image'>";
                content += "</a>";
                content += "<a target='_blank' rel='opener' href=javascript:void(0);>";
                content +=    "<span class='item-desc'>" + songList[i].songList + "</span>";
                content += "</a>";
                content += "<input type='hidden' id='songListId' value='" + songList[i].songListId + "'>";
                content += "</div>";
                $("#songLists").append(content);
            }
        }
    });
}

//新歌精选
function getSongs(pageNum) {
    var data = {
        "pageNum": pageNum, "pageSize": 4,
    }
    $.ajax({
        "url": "/allSongs", method: "post", data: data, success: function (data) {
            $("#songs").empty();
            var songs = data.list;
            for (var i = 0; i < songs.length; i++) {
                let content = "<div class='panel panel-default col-xs-6 col-sm-4 col-md-3' style='box-shadow: 5px 5px 5px #eee'>";
                content += "<div class='panel-body media ' style='padding: 0 0 15px;'>";
                content += "<a style='padding: 15px;' class='pull-left' href='#' >";
                content +="    <img alt='' class='media-object img-rounded' src='" + songs[i].album.albumImg + "' style='width: 94px;height: 94px;'>" ;
                content +="</a>";
                content += "<div style='padding: 15px;' class='media-body mx-auto'>";
                content +=  "<div>";
                content +=      "<h4 class='media-heading''>" + songs[i].song + "</h4>";
                content +=      "<small class='text-muted'>" + songs[i].singer.singerName + "</small>";
                content +=  "<div style='margin-top: 10px'>";
                content +=  "<a id='playOne' target='play' style='color:cyan;font-size: 18px;text-decoration: none' class='glyphicon glyphicon-play' href=javascript:void(0);></a>";
                content +=  "<span class='glyphicon glyphicon-heart-empty collect' style='font-size: 18px;margin-left: 5px;cursor: pointer'></span>";
                content +=  "</div>'"
                content +=  "</div>";
                content += "</div>";
                content += "<input type='hidden' id='songId' value=" + songs[i].songId + ">";
                content += "</div>";
                content += "</div>";
                $("#songs").append(content);
            }
            //收藏歌曲状态
            if (null !== userId) {
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

//精选专辑
function getAlbums(pageNum) {
    var data = {
        "pageNum": pageNum, "pageSize": 6,
    }
    $.ajax({
        "url": "/allAlbums", method: "post", data: data, success: function (data) {
            $("#albums").empty();
            var albums = data.list;
            // console.log("精选专辑", data);
            for (var i = 0; i < albums.length; i++) {
                var content = "<div class='col-xs-6 col-sm-6 col-md-3' >";
                content += "<div class='box'>";
                content += "<div class='box-img'>";
                content += "<img src='" + albums[i].albumImg + "' />";
                content += "</div>";
                content += "<div class='box-content'>";
                content += "<a target='_blank' rel='opener' href=javascript:void(0);><h4 class='title'>" + albums[i].album + "</h4></a>";
                content += "</div>";
                content += "</div>";
                content += "<input type='hidden' id='albumId' value='" + albums[i].albumId + "'/></div>";
                $("#albums").append(content);
            }

        }
    });
}

//歌手
function getSingers(pageNum) {
    var data = {
        "pageNum": pageNum, "pageSize": 3,
    }
    console.log(pageNum);
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: "/singer/page",
        method: "post",
        data: JSON.stringify(data), success: function (res) {
            // console.log("歌手信息", data);
            var singers = res.data.list;
            for (var i = 0; i < singers.length; i++) {
                let content = "<div class='col-xs-4 col-sm-4 col-md-3'>";
                content += "<div class='thumbnail '>"
                content += "<img style='height: 147px'  src=" + singers[i].imgUrl + " alt=" + singers[i].singerName + "/>";
                content += "<div class='aption'>";
                content += "<h4>" + singers[i].singerName + "</h4>";
                content += "<p style='overflow: hidden;white-space: nowrap;text-overflow: ellipsis'>" + singers[i].intrduction + "</p>";
                content += "<p><a href='javascript:void(0);' id='home' class='btn btn-primary' role='button'>主页</a></p>";
                content += "</div>";
                content += "</div>";
                content += "<input type='hidden' id='singerId' value=" + singers[i].singerId + "></div>";
                $("#singerList").append(content);
            }

        }
    });
}

//收藏歌曲
$("#songs").on("click", ".collect", function () {
    alert("Collect")
    var num = $(this).parent().parent().parent().find("input[type='hidden']").val();

    var className = $(this).attr("class");
    console.log(userId)
    if (null != userId) {
        $(this).removeClass("glyphicon-heart glyphicon-heart-empty collect");
        if (className === "glyphicon glyphicon-heart-empty collect") {
            // console.log("你要收藏的歌曲id为" + num)
            $(this).css("color", "red");
            $(this).addClass("glyphicon glyphicon-heart collect");
            $.ajax({
                url: "/user/collectSongs",
                method: "post",
                data: {"userId": user.userId, "songId": num},
                success: function (data) {
                    console.log(data);
                    if (data === 1) {
                        toastr.success("收藏成功！")
                    }
                }
            });
        } else {
            // console.log("你要取消收藏的歌曲id为" + num)
            $(this).css("color", "");
            $(this).addClass("glyphicon glyphicon-heart-empty collect");
            $.ajax({
                url: "/user/delCollectedSongs",
                method: "post",
                data: {"userId": user.userId, "songId": num},
                success: function (data) {
                    console.log(data);
                    if (data === 1) {
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
//播放量
$("#songs").on("click", "#playOne", function () {
    var num = $(this).parent().parent().parent().parent().find("input[type='hidden']").val();
    $.ajax({
        url: "/updatePlayCount/" + num, method: "post", success: function (data) {
            console.log(data);
            if (data === 1) {
                console.log("播放量加1");
            }
        }
    });
});



