$(function () {
    getAlbums(1);
});




function getAlbums(pageNum){
    var data = {
        "pageNum": pageNum,
        "pageSize": $("#pageSize").val(),
    }
    $.ajax({
        "url": "http://localhost:8080/musicwebsite/allAlbums",
        method: "post",
        data:data,
        success: function (data) {
            $(".navigate").empty();
            $("#albums").empty();
            makePage(data);
            console.log(data);
            var albums=data.list;
            for (var i=0;i<albums.length;i++){
               var content="<div class='col-xs-6 col-sm-1-5 col-md-1-5' >";
                content+="<div class='box'>";
                content+="<div class='box-img'>";
                content+="<img src="+albums[i].albumImg+">";
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

//分页
function makePage(data){
    var pageNums=data.navigatepageNums;
    if(data.pages==1){
        return;
    }

    if(data.isFirstPage!=true){
        var pageNum=1;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getAlbums("+pageNum+")").text("首页");
        $(".navigate").append($li);
    }
    if(data.hasPreviousPage==true){
        var pageNum=data.pageNum-1;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getAlbums("+pageNum+")").text("上一页");
        $(".navigate").append($li);
    }
    for(var i=0;i<pageNums.length;i++){
        var pageNum=pageNums[i];
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getAlbums("+pageNum+")").text(pageNum);
        $(".navigate").append($li);
    }
    if(data.hasNextPage==true){
        var pageNum=data.pageNum+1;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getAlbums("+pageNum+")").text("下一页");
        $(".navigate").append($li);
    }

    if(data.isLastPage!=true){
        var pageNum=data.pages;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getAlbums("+pageNum+")").text("尾页");
        $(".navigate").append($li);
    }
}

