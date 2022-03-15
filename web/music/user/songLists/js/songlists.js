$(function () {
    getSongLists(1);
    getFenLei();
});
function getSongLists(pageNum){
    var data = {
        "pageNum": pageNum,
        "pageSize": $("#pageSize").val(),
    }
    $.ajax({
        "url": "http://localhost:8080/musicwebsite/allSongLists",
        method: "post",
        data:data,
        success: function (data) {
            $(".navigate").empty();
            $("#songLists").empty();
            makePage(data);
            console.log("歌单列表",data);
            var songList =data.list;
            for (var i=0;i<songList.length;i++){
                var  content="<div  class='col-xs-12 col-sm-4 col-md-3 filtr-item' data-category='2, 3' data-sort='欧美/流行'>";
                content+="<a href=javascript:void(0);><img class='img-responsive' src='"+songList[i].imgUrl+"' alt='sample image'></a>";
                content+="<a href=javascript:void(0);><span class='item-desc'>"+songList[i].songList+"</span></a>";
                content+="<input type='hidden' id='songListId' value='"+songList[i].songListId+"'></div>";
                $("#songLists").append(content);
            }

        }
    });
}
//分页
function makePage(data){
    console.log(data)
    var pageNums=data.navigatepageNums;
    if(data.pages==1){
        return;
    }
    if(data.isFirstPage!=true){
        var pageNum=1;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getSongLists("+pageNum+")").text("首页");
        $(".navigate").append($li);
    }
    if(data.hasPreviousPage==true){
        var pageNum=data.pageNum-1;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getSongLists("+pageNum+")").text("上一页");
        $(".navigate").append($li);
    }
    for(var i=0;i<pageNums.length;i++){
        var pageNum=pageNums[i];
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getSongLists("+pageNum+")").text(pageNum);
        $(".navigate").append($li);
    }
    if(data.hasNextPage==true){
        var pageNum=data.pageNum+1;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getSongLists("+pageNum+")").text("下一页");
        $(".navigate").append($li);
    }

    if(data.isLastPage!=true){
        var pageNum=data.pages;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getSongLists("+pageNum+")").text("尾页");
        $(".navigate").append($li);
    }
}
function getFenLei() {
    $(".simplefilter").empty();
    $.ajax({
        "url": "http://localhost:8080/musicwebsite/fenLei",
        method: "post",
        success: function (data) {
            console.log(data);
            var content=" <li class='active' data-filter='all'><input type='hidden' id='fenId' value=0>全部</li>";
            for (var i=0;i<data.length;i++){
                // console.log(data[i].fenId)
                content+="<li ><input type='hidden' id='fenId' value='"+data[i].fenId+"'>"+data[i].content+"</li>";
                $(".simplefilter").html(content);
            }

        }
    });
}

function getFenSongLists(pageNum,fenId){
    var data = {
        "pageNum": pageNum,
        "pageSize": $("#pageSize").val(),
        "fenId":fenId,
    }
    $.ajax({
        "url": "http://localhost:8080/musicwebsite/allFenSongLists",
        method: "post",
        data:data,
        success: function (data) {
            $(".navigate").empty();
            $("#songLists").empty();
            makePage(data);
            console.log(data);
            var songList =data.list;
            if(0==songList.length){
                $("#songLists").html("<h1 class='text-center'>暂无数据</h1>");
                return;
            }
            for (var i=0;i<songList.length;i++){
                var  content="<div  class='col-xs-12 col-sm-4 col-md-3 filtr-item' data-category='2, 3' data-sort='欧美/流行'>";
                content+="<a href=javascript:void(0);><img class='img-responsive' src='"+songList[i].imgUrl+"' alt='sample image'></a>";
                content+="<a href=javascript:void(0);><span class='item-desc'>"+songList[i].songList+"</span></a>";
                content+="<input type='hidden' id='songListId' value='"+songList[i].songListId+"'></div>";
                $("#songLists").append(content);
            }

        }
    });
}


$('.simplefilter').on("click","li",function () {
   var fenId=$(this).find("#fenId").val();
    getFenSongLists(1,fenId);
    $('.simplefilter li').removeClass('active');
    $(this).addClass('active');
});



