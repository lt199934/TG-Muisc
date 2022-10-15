$(function () {
    getAlbums(1);
});




function getAlbums(pageNum){
    var data = {
        "pageNum": pageNum,
        "pageSize": $("#pageSize").val(),
    }
    $.ajax({
        "url": "/allAlbums",
        method: "post",
        data:data,
        success: function (data) {
            $(".pagination").empty();
            $("#albums").empty();
            makePage(data);
            console.log("专辑列表",data);
            var albums=data.list;
            for (var i=0;i<albums.length;i++){
               var content="<div class='col-xs-6 col-sm-6 col-md-2' >";
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
function makePage(data) {
    if (data.list.length != 0) {
        console.log(data)
        var pageNums = data.navigatepageNums;
        if (data.pages == 1) {
            return;
        } else {
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:getAlbums(" + 1 + ");'><span aria-hidden=\"true\">首页</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        //是否由上一页
        if (data.hasPreviousPage == true) {
            var pageNum = data.pageNum - 1;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Previous' href='javascript:getAlbums(" + pageNum + ");'><span aria-hidden=\"true\">&laquo;</span></a>");
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
            $btn = $("<a href='javascript:getAlbums(" + pageNum + ");'>" + pageNum + "</a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        if (data.hasNextPage == true) {
            var pageNum = data.pageNum + 1;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:getAlbums(" + pageNum + ");'><span aria-hidden=\"true\">&raquo;</span></a>");
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
            $btn = $("<a aria-label='Next' href='javascript:getAlbums(" + pageNum + ");'><span aria-hidden=\"true\">尾页</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
    } else {
        $(".pagination").empty();
    }

}

