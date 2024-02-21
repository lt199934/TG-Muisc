$(function () {
    isFirst(1);
    getSingers(1);
});
//获取所有歌手信息
function getSingers(pageNum){
    var data= {
        "pageNum": pageNum,
        "pageSize": 3,
    }
    // console.log(pageNum);
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: "/singer/page",
        method: "get",
        data: data,
        success: function (res) {
            $(".pagination").empty();
            $("#singerList").empty();
            $(".num").html(res.data.total);
            makePage(res.data,'getSingers');
            console.log("歌手列表",data);
            var singers = res.data.list;
            for (var i=0;i<singers.length;i++){
                var content = "<div class='col-xs-4 col-sm-4 col-md-3'>";
                content += "<div class='thumbnail'>"
                content += "<img style='height: 147px'  src=" + singers[i].imgUrl + " alt=" + singers[i].singerName + ">";
                content += "<div class='aption'>";
                content += "<h4>" + singers[i].singerName + "</h4>";
                content += "<p style='overflow: hidden;white-space: nowrap;text-overflow: ellipsis'>" + singers[i].introduction + "</p>";
                content += "<p><a href='javascript:void(0);' id='home' class='btn btn-primary' role='button'>主页</a> <a href='#' class='btn btn-default' role='button'>Button</a></p>";
                content += "</div>";
                content += "</div>";
                content += "<input type='hidden' id='singerId' value=" + singers[i].singerId + "></div>";
                $("#singerList").append(content);
            }

        }
    });
}