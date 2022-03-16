$(function () {
    getSongList(1);
});

var user=JSON.parse(sessionStorage.getItem("user"));//获取用户信息


function getSongList(pageNum){
    var data = {
        "pageNum": pageNum,
        "pageSize": 2,
    }
    console.log(getUrlParam("songListId"))
    $.ajax({
        "url": "/songList/"+getUrlParam("songListId"),
        method: "post",
        data:data,
        success: function (data) {
            // $("#songLists").empty();
            $("#fenLei").empty();
            $("#song").empty();
            console.log("歌单详情",data);
            var songList =data;
            var songDto=data.songDto;
            $("#songListImg").attr("src",songList.imgUrl);
            $(".songList__name").html(songList.songList);
            $(".songList__number").html(songDto.length+"首歌");
            $("#introduction").html(songList.introduction);
            $("#time").html(songList.time);
            $("#user").html("<a href=/music/user/user/user.html?userId="+songList.userId+">"+songList.createUser+"</a>");
            var content="";
            for(var j=0;j<data.fenLeis.length;j++){
                 content+=data.fenLeis[j].content+" ,";
            }
            $("#fenLei").append(content.substring(0,content.lastIndexOf(",")));
                for (var i=0;i<songDto.length;i++){
                           var content="<tr>";
                            content+="<td>"+(i+1)+"</td>";
                            content+="<td class='text-center'><img class='img-responsive center-block' src="+songDto[i].albumUrl+"></td>";
                            content+="<td>"+songDto[i].song+"</td>";
                            content+="<td><a href='/music/user/singers.html?singerId="+songDto[i].singerId+"'>"+songDto[i].singerName+"</a></td>";
                            content+="<td><a href='/music/user/album.html?albumId="+songDto[i].albumId+"'>"+songDto[i].albumName+"</td>";
                            content+="<td><span id='collect' title='收藏音乐' class='glyphicon glyphicon-heart-empty' style='font-size: 16px'></span></td>";
                            content+="<td><a id='playOne' target='play' href=/music/user/play/player.html?temp=song&songId="+songDto[i].songId+"><span id='play' title='播放音乐' class='glyphicon glyphicon-play' style='color:cyan;font-size: 18px' ></span></a></td>";
                            content+="<input type='hidden' value="+songDto[i].songId+">"
                            content+="</tr>";
                            $("#song").append(content);
                }

            }


    });
}


function delSongList(songListId) {
    if(confirm("确定删除该张歌单?")) {
        $.ajax({
            "url": "/delSongList/" + songListId,
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
$("#song").on("click","#collect",function () {
    var num =$(this).parent().parent().find("input[type='hidden']").val();
    var className=$(this).attr("class");
    if (null!=user){
        $(this).removeClass("glyphicon-heart glyphicon-heart-empty");
        if(className=="glyphicon glyphicon-heart-empty"){
            console.log("你要收藏的歌曲id为"+num)
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
        window.location.href="/music/user/index.html";
        $("#myLoginModal").modal("show");
    }

})
//播放量
$("#song").on("click","#playOne",function () {
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
    console.log("你要收藏的歌单id为"+getUrlParam("songListId"))
    var className=$(this).attr("class");
    if (null!=user){
        $(this).removeClass("glyphicon-heart glyphicon-heart-empty");
        if(className=="glyphicon glyphicon-heart-empty"){
            $(this).css("color","red");
            $(this).addClass("glyphicon glyphicon-heart");
            $.ajax({
                url: "/collectSongLists",
                method: "post",
                data:{"userId":user.userId,"songListId":getUrlParam("songListId")},
                success: function (data) {
                    console.log(data);
                    if (data == 1) {
                        alert("收藏成功");
                    }

                }
            });
        }else {
            $(this).css("color","");
            $(this).addClass("glyphicon glyphicon-heart-empty");
            $.ajax({
                url: "/delCollectSongLists",
                method: "post",
                data:{"userId":user.userId,"songListId":getUrlParam("songListId")},
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
        window.location.href="/music/user/index.html";
        $("#myLoginModal").modal("show");
    }
})
//播放全部
$("#playAll").click(function () {
    $(this).attr("href","/music/user/play/player.html?temp=songList&songListId="+getUrlParam("songListId"));
})