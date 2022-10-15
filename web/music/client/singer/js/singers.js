$(function () {
    getSingers(1);
});
//获取所有歌手信息
function getSingers(pageNum){
    var data= {
        "pageNum": pageNum,
        "pageSize": 2,
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
                var content="<div class='col-sm-6 col-md-4'>";
                content+="<div class='thumbnail'>"
                content+="<img style='width: 350px;height: 350px'  src="+singers[i].imgUrl+" alt="+singers[i].singerName+">";
                content+="<div class='aption'>";
                content+="<h3>"+singers[i].singerName+"</h3>";
                content+= "<p style='overflow: hidden;white-space: nowrap;text-overflow: ellipsis'>"+singers[i].intrduction+"</p>";
                content+= "<p><a href='javascript:void(0);' id='home' class='btn btn-primary' role='button'>主页</a> <a href='#' class='btn btn-default' role='button'>Button</a></p>";
                content+= "</div>";
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