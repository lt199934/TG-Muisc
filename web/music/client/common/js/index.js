var userId = JSON.parse(sessionStorage.getItem("userId"));//获取用户信息
if (null == userId) {
    $("#login").css("display", "block");
    $("#register").css("display", "block");
    $("#logout").css("display", "none");
    $("#personalPage").css("display", "none");
    $("#headimg").css("display", "none");
    $("#profile").css("display", "none");
    $("#uc").html(" ");
} else {
    $.ajax({
        url: "/getUserInfo/"+userId,
        method: "post",
        success: function (data) {
            console.log(data);
            if(null != data){
                toastr.success("欢迎您" + data.nickName + "回来(*∩_∩*)")
                console.log(data.headImg);
                $("#login").css("display", "none");
                $("#register").css("display", "none");
                $("#logout").css("display", "block");
                $("#personalPage").css("display", "block");
                $("#headimg").css("display", "block");
                $("#profile").css("display", "block");
                $("#headimg").attr("src", data.headImg);
                $("#uc").html(data.nickName);
            }

        }
    });
}

var date = new Date();
var time = date.getFullYear() + "‐" + date.getMonth() + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();


$("#logout").click(function () {
    var message = confirm("是否退出登录?");
    if (message == true) {
        sessionStorage.removeItem("userId");
        location.href="/logout";
    }
});
$(function () {
    getAlbums(1);
    getSongLists(1);
    getSongs(1);
    getSingers(1);
});
var col = document.getElementById("collect");
console.log(col)

//精选专辑
function getAlbums(pageNum) {
    var data = {
        "pageNum": pageNum,
        "pageSize": 6,
    }
    $.ajax({
        "url": "/allAlbums",
        method: "post",
        data: data,
        success: function (data) {
            $("#albums").empty();
            var albums = data.list;
            console.log("精选专辑", data);
            for (var i = 0; i < albums.length; i++) {
                var content = "<div class='col-xs-6 col-sm-6 col-md-2' >";
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

//精选歌单
function getSongLists(pageNum) {
    var data = {
        "pageNum": pageNum,
        "pageSize": 4,
    }
    $.ajax({
        "url": "/allSongLists",
        method: "post",
        data: data,
        success: function (data) {
            $("#songLists").empty();
            var songList = data.list;
            console.log("精选歌单", data);
            for (var i = 0; i < songList.length; i++) {
                var content = "<div  class='col-xs-6 col-sm-6 col-md-2 filtr-item' data-category='2, 3' data-sort='欧美/流行'>";
                content += "<a target='_blank' rel='opener'  href=javascript:void(0);><img class='img-responsive' src='" + songList[i].imgUrl + "' alt='sample image'></a>";
                content += "<a target='_blank' rel='opener' href=javascript:void(0);><span class='item-desc'>" + songList[i].songList + "</span></a>";
                content += "<input type='hidden' id='songListId' value='" + songList[i].songListId + "'></div>";
                $("#songLists").append(content);
            }

        }
    });
}

//新歌精选
function getSongs(pageNum) {
    var data = {
        "pageNum": pageNum,
        "pageSize": 6,
    }
    $.ajax({
        "url": "/allSongs",
        method: "post",
        data: data,
        success: function (data) {
            var songs = data.list;
            console.log("新歌精选", data);
            var content = "";
            for (var i = 0; i < songs.length; i++) {
                content += "<tr>";
                content += "<td>" + (i + 1) + "</td>";
                content += "<td><img src=" + songs[i].album.albumImg + " class='img-rounded'></td>";
                content += "<td>" + songs[i].song + "</td>";
                content += "<td><a target='_blank' rel='opener' id='singer' href=javascript:void(0);>" + songs[i].singer.singerName + "</a><input type='hidden' id='singerId' value='" + songs[i].singer.singerId + "'></td>";
                content += "<td><span class='glyphicon glyphicon-heart-empty collect' style='font-size: 16px'></span></td>";
                content += "<td><a id='playOne' target='play' href=javascript:void(0);><span id='play' class='glyphicon glyphicon-play' style='color:cyan;font-size: 18px' ></span></a></td>";
                content += "<input type='hidden' id='songId' value=" + songs[i].songId + ">";
            }
            $("#music").html(content);
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

//歌手
function getSingers(pageNum) {
    var data = {
        "pageNum": pageNum,
        "pageSize": 3,
    }
    console.log(pageNum);
    $.ajax({
        "url": "/selectAllSingers",
        method: "post",
        data: data,
        success: function (data) {
            console.log("歌手信息", data);
            var singers = data.list;
            for (var i = 0; i < singers.length; i++) {
                var content = "<div class='col-sm-6 col-md-4'>";
                content += "<div class='thumbnail'>"
                content += "<img style='width: 350px;height: 350px'  src=" + singers[i].imgUrl + " alt=" + singers[i].singerName + ">";
                content += "<div class='aption'>";
                content += "<h3>" + singers[i].singerName + "</h3>";
                content += "<p style='overflow: hidden;white-space: nowrap;text-overflow: ellipsis'>" + singers[i].intrduction + "</p>";
                content += "<p><a href='javascript:void(0);' id='home' class='btn btn-primary' role='button'>主页</a> <a href='#' class='btn btn-default' role='button'>Button</a></p>";
                content += "</div>";
                content += "</div>";
                content += "<input type='hidden' id='singerId' value=" + singers[i].singerId + "></div>";
                $("#singerList").append(content);
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
//播放量
$("#music").on("click", "#playOne", function () {
    var num = $(this).parent().parent().find("input[type='hidden']").val();
    $.ajax({
        url: "/updatePlayCount/" + num,
        method: "post",
        success: function (data) {
            console.log(data);
            if (data == 1) {
                console.log("播放量加1");
            }
        }
    });
});



