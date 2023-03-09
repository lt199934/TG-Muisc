$(function () {
    getAlbum(1);
});

var user=JSON.parse(sessionStorage.getItem("user"));//获取用户信息
console.log(user)
if(null==user){
    toastr.options.onHidden = function() {
        window.replace("/login");
    }
    toastr.warning("请先登录！")
}

function getAlbum(pageNum){
    var data = {
        "pageNum": pageNum,
        "pageSize": 10,
    }
    $.ajax({
        "url": "/albums/"+getUrlParam("albumId"),
        method: "post",
        data:data,
        success: function (data) {
            $("#songLists").val();
            $("#albums").empty();
            console.log("专辑详情",data);
            var album =data;
            var songs=data.songs;
            var singer=data.singer;
            var content="";
            $("#songListImg").attr("src",album.albumImg);
            $(".songList__name").html(album.album);
            $(".singer").html("歌手：<a href='/music/client/singer/singer.html?singerId="+singer.singerId+"'>"+singer.singerName+"</a>");
            $(".songList__number").html(album.count+"首歌");
            $("#introduction").html(album.introduction);
            $("#time").html(album.time);
            $("#company").html(album.company);
            var content="";
            for (var i=0;i<songs.length;i++){
                content+="<tr>";
                content+="<td>"+(i+1)+"</td>";
                content+="<td>"+songs[i].song+"</td>";
                content+="<td><span id='collect' title='收藏音乐' class='glyphicon glyphicon-heart-empty' style='font-size: 16px'></span></td>";
                content+="<td><a id='playOne' target='play' href=/music/client/play/player.html?temp=song&songId="+songs[i].songId+"><span id='play' title='播放音乐' class='glyphicon glyphicon-play' style='color:cyan;font-size: 18px' ></span></a><audio src="+songs[i].url+"></audio></td>";
                content+="<input type='hidden' value="+songs[i].songId+">";
                content+="</tr>";
                $("#albums").html(content);
            }

        }
    });
}



function delAlbum(albumId) {
    if(confirm("确定删除该张专辑?")) {
        $.ajax({
            "url": "/delSong/"+albumId,
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
}
//收藏歌曲
$("#albums").on("click","#collect",function () {
    var num =$(this).parent().parent().find("input[type='hidden']").val();
    console.log("你要收藏的歌曲id为"+num);
    var className=$(this).attr("class");
    if (null!=user){
        $(this).removeClass("glyphicon-heart glyphicon-heart-empty");
        if(className=="glyphicon glyphicon-heart-empty"){

            $(this).css("color","red");
            $(this).addClass("glyphicon glyphicon-heart");
            $.ajax({
                url: "/collectSongs",
                method: "post",
                data:{"userId":user.userId,"songId":num},
                success: function (data) {
                    console.log(data);
                    if (data == 1) {
                        alert("收藏成功");
                    }

                }
            });
        }else {
            console.log("你要取消收藏的歌曲id为"+num)
            $(this).css("color","");
            $(this).addClass("glyphicon glyphicon-heart-empty");
            $.ajax({
                url: "/delCollectedSongs",
                method: "post",
                data:{"userId":user.userId,"songId":num},
                success: function (data) {
                    console.log(data);
                    if (data == 1) {
                        alert("取消收藏");
                    }

                }
            });
        }
    }else {
        alert("请先登录");
        window.location.href="/";
        $("#myLoginModal").modal("show");
    }

})
//播放量
$("#albums").on("click","#playOne",function () {
    var num =$(this).parent().parent().find("input[type='hidden']").val();
    $.ajax({
        url: "/updatePlayCount/"+num,
        method: "post",
        success: function (data) {
            console.log(data);
            if (data == 1) {
                console.log("播放量加1");
            }
        }
    });
});
// 收藏歌单
$("#collectSongList").click(function () {
    var className=$(this).attr("class");
    if (null!=user){
        console.log("你要收藏的专辑id为"+getUrlParam("albumId"));
        $(this).removeClass("glyphicon-heart glyphicon-heart-empty");
        if(className=="glyphicon glyphicon-heart-empty"){
            $(this).css("color","red");
            $(this).addClass("glyphicon glyphicon-heart");
            $.ajax({
                url: "/collectAlbums",
                method: "post",
                data:{"userId":user.userId,"albumId":getUrlParam("albumId")},
                success: function (data) {
                    console.log(data);
                    if (data == 1) {
                        alert("收藏成功");
                    }

                }
            });
        }else {
            console.log("你要取消收藏的专辑id为"+getUrlParam("albumId"));
            $(this).css("color","");
            $(this).addClass("glyphicon glyphicon-heart-empty");
            $.ajax({
                url: "/delCollectAlbums",
                method: "post",
                data:{"userId":user.userId,"albumId":getUrlParam("albumId")},
                success: function (data) {
                    console.log(data);
                    if (data == 1) {
                        alert("取消收藏");
                    }
                }
            });
        }
    }else {
        alert("请先登录");
        window.location.href="/";
        $("#myLoginModal").modal("show");
    }
});
//播放全部
$("#playAll").click(function () {
    $(this).attr("href","/music/client/play/player.html?temp=album&albumId="+getUrlParam("albumId"));
})

