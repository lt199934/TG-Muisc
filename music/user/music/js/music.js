$(function () {
    getSongs(1);
});

function getSongs(pageNum) {
    var data = {
        "pageNum": pageNum,
        "pageSize": $("#pageSize").val(),
    }
    console.log(pageNum);
    $.ajax({
        "url": "http://localhost:8080/musicwebsite/allSongs",
        method: "post",
        data: data,
        success: function (data) {
            $(".navigate").empty();
            $("#music").empty();
            makePage(data);
            console.log(data);
            for (var i = 0; i <10 ; i++) {
                var songs=data.list;
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

function delSong(songid) {
    $.ajax({
        "url": "http://localhost:8080/musicwebsite/delSong/"+songid,
        method: "post",
        success: function (data) {
            console.log(data);
            if (data==1){
                alert("删除成功！");
                getSongs();
            }
        }
    });
}

//收藏歌曲
$("#music").on("click","#collect",function () {
    var className=$(this).attr("class");
    if (null!=user){
        $(this).removeClass("glyphicon-heart glyphicon-heart-empty");
        if(className=="glyphicon glyphicon-heart-empty"){
            $(this).css("color","red");
            $(this).addClass("glyphicon glyphicon-heart");
            $.ajax({
                url: "http://localhost:8080/musicwebsite/collectSongs",
                method: "post",
                data:{"userId":user.userId,"songId":$("[type='hidden']").val()},
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
                url: "http://localhost:8080/musicwebsite/delCollectedSongs",
                method: "post",
                data:{"userId":user.userId,"songId":$("[type='hidden']").val()},
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
        $("#myLoginModal").modal("show");
    }

})



//分页
function makePage(data){
    var pageNums=data.navigatepageNums;
    // $li=$("<li class='pull-left'></li>");
    // $btn=$("<button class='btn btn-primary'></button>");
    // $li.append($btn);
    // $btn.attr("onclick","getSongs("+pageNum+")").text("首页");
    // $(".navigate").append($li);
    if(data.pages==1){
        return;
    }
    if(data.isFirstPage!= true){
        var pageNum=1;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getSongs("+pageNum+")").text("首页");
        $(".navigate").append($li);
    }
    if(data.hasPreviousPage==true){
        var pageNum=data.pageNum-1;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getSongs("+pageNum+")").text("上一页");
        $(".navigate").append($li);
    }
    for(var i=0;i<pageNums.length;i++){
        var pageNum=pageNums[i];
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getSongs("+pageNum+")").text(pageNum);
        $(".navigate").append($li);
    }
    if(data.hasNextPage==true){
        var pageNum=data.pageNum+1;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getSongs("+pageNum+")").text("下一页");
        $(".navigate").append($li);
    }

    if(data.isLastPage!=true){
        var pageNum=data.pages;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getSongs("+pageNum+")").text("尾页");
        $(".navigate").append($li);
    }
}


