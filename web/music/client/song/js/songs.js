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
            $("#music").empty();
            makePage(data);
            console.log("曲库信息",data);
            var songs=data.list;
            for (var i = 0; i <songs.length; i++) {
                content="<tr>";
                content+="<td>"+(i+1)+"</td>";
                content+="<td><img src="+songs[i].album.albumImg+" class='img-responsive'></td>";
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

//删除歌曲
function delSong(songid) {
    $.ajax({
        "url": "/delSong/"+songid,
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
                url: "/collectSongs",
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
                url: "/delCollectedSongs",
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

