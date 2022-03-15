var user=JSON.parse(sessionStorage.getItem("user"));//获取用户信息
var date = new Date();
var time=date.getFullYear()+"‐"+date.getMonth()+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
console.log(time);

$(function () {
    getAlbums(1);
    getSongLists(1);
    getSongs(1);
    getSingers(1);
});
var col=document.getElementById("collect");
console.log(col)

$("#reg").click(function () {
    $("#myLoginModal").modal("hide");
    $("#myRegisterModal").modal("show");
});
$("#log").click(function () {
    $("#myLoginModal").modal("show");
    $("#myRegisterModal").modal("hide");
});

//精选专辑
function getAlbums(pageNum){
    var data = {
        "pageNum": pageNum,
        "pageSize": 6,
    }
    $.ajax({
        "url": "http://localhost:8080/musicwebsite/allAlbums",
        method: "post",
        data:data,
        success: function (data) {
            $("#albums").empty();
            var albums=data.list;
            console.log("精选专辑",data);
            for (var i=0;i<albums.length;i++){
               var content="<div class='col-xs-6 col-sm-1-5 col-md-1-5' >";
                content+="<div class='box'>";
                content+="<div class='box-img'>";
                content+="<img src='"+albums[i].albumImg+"' />";
                content+="</div>";
                content+="<div class='box-content'>";
                content+="<a href=javascript:void(0);><h4 class='title'>"+albums[i].album+"</h4></a>";
                content+="</div>";
                content+="</div>";
                content+="<input type='hidden' id='albumId' value='"+albums[i].albumId+"'/></div>";
                $("#albums").append(content);
            }

        }
    });
}

//精选歌单
function getSongLists(pageNum){
    var data = {
        "pageNum": pageNum,
        "pageSize": 4,
    }
    $.ajax({
        "url": "http://localhost:8080/musicwebsite/allSongLists",
        method: "post",
        data:data,
        success: function (data) {
            $("#songLists").empty();
            var songList =data.list;
            console.log("精选歌单",data);
            for (var i=0; i < songList.length; i++){
                var  content="<div  class='col-xs-12 col-sm-4 col-md-3 filtr-item' data-category='2, 3' data-sort='欧美/流行'>";
                content+="<a href=javascript:void(0);><img class='img-responsive' src='"+songList[i].imgUrl+"' alt='sample image'></a>";
                content+="<a href=javascript:void(0);><span class='item-desc'>"+songList[i].songList+"</span></a>";
                content+="<input type='hidden' id='songListId' value='"+songList[i].songListId+"'></div>";
                $("#songLists").append(content);
            }

        }
    });
}

//新歌精选
function getSongs(pageNum) {
    var data = {
        "pageNum": pageNum,
        "pageSize": 5,
    }
    $.ajax({
        "url": "http://localhost:8080/musicwebsite/allSongs",
        method: "post",
        data: data,
        success: function (data) {
            var songs=data.list;
            console.log("新歌精选",data);
            for (var i = 0; i < songs.length; i++) {
                content="<tr>";
                content+="<td>"+(i+1)+"</td>";
                content+="<td><img src="+songs[i].album.albumImg+"></td>";
                content+="<td>"+songs[i].song+"</td>";
                content+="<td><a id='singer' href=javascript:void(0);>"+songs[i].singer.singerName+"</a><input type='hidden' id='singerId' value='"+songs[i].singer.singerId+"'></td>";
                content+="<td><span id='collect' class='glyphicon glyphicon-heart-empty' style='font-size: 16px'></span></td>";
                content+="<td><a id='playOne' target='play' href=javascript:void(0);><span id='play' class='glyphicon glyphicon-play' style='color:cyan;font-size: 18px' ></span></a></td>";
                content+="<input type='hidden' id='songId' value="+songs[i].songId+">";
                $("#music").append(content);
            }
        }
    });
}
//歌手
function getSingers(pageNum){
    var data= {
        "pageNum": pageNum,
        "pageSize": 4,
    }
    console.log(pageNum);
    $.ajax({
        "url": "http://localhost:8080/musicwebsite/selectAllSingers",
        method: "post",
        data:data,
        success: function (data) {
            console.log("歌手信息",data);
            var singers=data.list;
            for (var i=0;i<singers.length;i++){
               var content="<div class='tupian'>";
                content+="<div style='background-image: url("+singers[i].imgUrl+")' class='p1'></div>"
                content+="<div class='bq'>";
                content+="<a href=javascript:void(0);>"+singers[i].singerName+"<span class='glyphicon glyphicon-home'></span></a>"
                content+="</div>";
                content+="<input type='hidden' id='singerId' value="+singers[i].singerId+"></div>";
                $("#singerList").append(content);
            }

        }
    });
}


//收藏歌曲
$("#music").on("click","#collect",function () {
    var num =$(this).parent().parent().find("input[type='hidden']").val();
    var className=$(this).attr("class");
    if (null!=user){
                    $(this).removeClass("glyphicon-heart glyphicon-heart-empty");
                    if(className=="glyphicon glyphicon-heart-empty"){
                        console.log("你要收藏的歌曲id为"+num)
                        $(this).css("color","red");
                        $(this).addClass("glyphicon glyphicon-heart");
                        $.ajax({
                            url: "http://localhost:8080/musicwebsite/collectSongs",
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
                            url: "http://localhost:8080/musicwebsite/delCollectedSongs",
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
        window.location.href="http://localhost:8080/musicwebsite/music/user/index.html";
        $("#myLoginModal").modal("show");
    }

})
//播放量
$("#music").on("click","#playOne",function () {
    var num =$(this).parent().parent().find("input[type='hidden']").val();
    $.ajax({
        url: "http://localhost:8080/musicwebsite/updatePlayCount/"+num,
        method: "post",
        success: function (data) {
            console.log(data);
            if (data == 1) {
                console.log("播放量加1");
            }
        }
    });
});



