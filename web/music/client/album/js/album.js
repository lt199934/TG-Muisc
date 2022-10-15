$(function () {
    getAlbum(1);
    window.setInterval("reinitIframe()", 200);
});
//iframe自适应高度,解决了动态更换页面高度无法自适应问题
function reinitIframe() {
    var content = document.getElementById("albums");
    try {
        //bHeight 和 dHeight 如果相等，用其一等于iframe.height 即可
        // var bHeight = iframe.contentWindow.document.body.scrollHeight;
        var dHeight = content.contentWindow.document.documentElement.scrollHeight;
        // var height = Math.max(bHeight, dHeight);
        // console.log(dHeight)
        content.height = dHeight;
    } catch (ex) { }
}
var user=JSON.parse(sessionStorage.getItem("user"));//获取用户信息
console.log(user)
if(null==user){
    toastr.options.onHidden = function() {
        location.href="/login";
    }
    toastr.warning("请先登录！")
}

function getAlbum(pageNum){
    var data = {
        "pageNum": pageNum,
        "pageSize": 2,
    }
    $.ajax({
        "url": "/albums/"+getUrlParam("albumId"),
        method: "post",
        data:data,
        success: function (data) {
            $("#songLists").val();
            $("#albums").empty();
            // makePage(data);
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
//分页
// function makePage(data) {
//     if (data.list.length != 0) {
//         console.log(data)
//         var pageNums = data.navigatepageNums;
//         if (data.pages == 1) {
//             return;
//         } else {
//             $li = $("<li></li>");
//             $btn = $("<a aria-label='Next' href='javascript:getSongLists(" + 1 + ");'><span aria-hidden=\"true\">首页</span></a>");
//             $li.append($btn);
//             $(".pagination").append($li);
//         }
//         //是否由上一页
//         if (data.hasPreviousPage == true) {
//             var pageNum = data.pageNum - 1;
//             $li = $("<li></li>");
//             $btn = $("<a aria-label='Previous' href='javascript:getSongLists(" + pageNum + ");'><span aria-hidden=\"true\">&laquo;</span></a>");
//             $li.append($btn);
//             $(".pagination").append($li);
//         } else {
//             $li = $("<li class='disabled'></li>");
//             $btn = $("<a aria-label='Previous'><span aria-hidden=\"true\">&laquo;</span></a>");
//             $li.append($btn);
//             $(".pagination").append($li);
//         }
//         // 页数
//         for (var i = 0; i < pageNums.length; i++) {
//             var pageNum = pageNums[i];
//             $li = $("<li></li>");
//             $btn = $("<a href='javascript:getSongLists(" + pageNum + ");'>" + pageNum + "</a>");
//             $li.append($btn);
//             $(".pagination").append($li);
//         }
//         if (data.hasNextPage == true) {
//             var pageNum = data.pageNum + 1;
//             $li = $("<li></li>");
//             $btn = $("<a aria-label='Next' href='javascript:getSongLists(" + pageNum + ");'><span aria-hidden=\"true\">&raquo;</span></a>");
//             $li.append($btn);
//             $(".pagination").append($li);
//         } else {
//             $li = $("<li class='disabled'></li>");
//             $btn = $("<a aria-label='Next'><span aria-hidden=\"true\">&raquo;</span></a>");
//             $li.append($btn);
//             $(".pagination").append($li);
//         }
//
//         if (data.isLastPage != true) {
//             var pageNum = data.pages;
//             $li = $("<li></li>");
//             $btn = $("<a aria-label='Next' href='javascript:getSongLists(" + pageNum + ");'><span aria-hidden=\"true\">尾页</span></a>");
//             $li.append($btn);
//             $(".pagination").append($li);
//         }
//     } else {
//         $(".pagination").empty();
//     }
//
// }
//播放全部
$("#playAll").click(function () {
    $(this).attr("href","/music/client/play/player.html?temp=album&albumId="+getUrlParam("albumId"));
})

