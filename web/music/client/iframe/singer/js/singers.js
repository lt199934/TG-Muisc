$(function () {
    getSingers(1);
});
//获取所有歌手信息
function getSingers(pageNum){
    var data= {
        "pageNum": pageNum,
        "pageSize": 3,
    }
    console.log(pageNum);
    $.ajax({
        "url": "/selectAllSingers",
        method: "post",
        data:data,
        success: function (data) {
            $(".pagination").empty();
            $("#singerList").empty();
            makePage(data);
            console.log("歌手列表",data);
            var singers=data.list;
            for (var i=0;i<singers.length;i++){
                var content="<div class='col-xs-6 col-sm-6 col-md-3'>";
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
function makePage(data) {
    if (data.list.length != 0) {
        console.log(data)
        var pageNums = data.navigatepageNums;
        if (data.pages == 1) {
            return;
        } else {
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:getSingers(" + 1 + ");'><span aria-hidden=\"true\">首页</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        //是否由上一页
        if (data.hasPreviousPage == true) {
            var pageNum = data.pageNum - 1;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Previous' href='javascript:getSingers(" + pageNum + ");'><span aria-hidden=\"true\">&laquo;</span></a>");
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
            $btn = $("<a href='javascript:getSingers(" + pageNum + ");'>" + pageNum + "</a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        if (data.hasNextPage == true) {
            var pageNum = data.pageNum + 1;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:getSingers(" + pageNum + ");'><span aria-hidden=\"true\">&raquo;</span></a>");
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
            $btn = $("<a aria-label='Next' href='javascript:getSingers(" + pageNum + ");'><span aria-hidden=\"true\">尾页</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
    } else {
        $(".pagination").empty();
    }

}