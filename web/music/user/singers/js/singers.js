$(function () {
    getSingers(1);
});
//获取所有歌手信息
function getSingers(pageNum){
    var data= {
        "pageNum": pageNum,
        "pageSize": $("#pageSize").val(),
    }
    console.log(pageNum);
    $.ajax({
        "url": "http://localhost:8080/musicwebsite/selectAllSingers",
        method: "post",
        data:data,
        success: function (data) {
            $(".navigate").empty();
            $("#singerList").empty();
            makePage(data);
            console.log(data);
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
        $btn.attr("onclick","getSingers("+pageNum+")").text("首页");
        $(".navigate").append($li);
    }
    if(data.hasPreviousPage==true){
        var pageNum=data.pageNum-1;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getSingers("+pageNum+")").text("上一页");
        $(".navigate").append($li);
    }
    for(var i=0;i<pageNums.length;i++){
        var pageNum=pageNums[i];
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getSingers("+pageNum+")").text(pageNum);
        $(".navigate").append($li);
    }
    if(data.hasNextPage==true){
        var pageNum=data.pageNum+1;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getSingers("+pageNum+")").text("下一页");
        $(".navigate").append($li);
    }

    if(data.isLastPage!=true){
        var pageNum=data.pages;
        $li=$("<li class='pull-left'></li>");
        $btn=$("<button class='btn btn-primary'></button>");
        $li.append($btn);
        $btn.attr("onclick","getSingers("+pageNum+")").text("尾页");
        $(".navigate").append($li);
    }
}