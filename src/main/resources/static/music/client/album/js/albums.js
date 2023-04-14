$(function () {
    isFirst(1);
    getAlbums(1);
});

function getAlbums(pageNum) {
    var data = {
        "pageNum": pageNum,
        "pageSize": 12,
    }
    $.ajax({
        "url": "/allAlbums",
        method: "post",
        data: data,
        success: function (data) {
            $(".pagination").empty();
            $("#albums").empty();
            $(".num").html(data.total);
            makePage(data, 'getAlbums');
            var albums = data.list;
            for (var i = 0; i < albums.length; i++) {
                var content = "<div class='col-xs-6 col-sm-6 col-md-2' >";
                content += "<div class='box'>";
                content += "<div class='box-img'>";
                content += "<img src=" + albums[i].albumImg + ">";
                content += "</div>";
                content += "<div class='box-content'>";
                content += "<a href=javascript:void(0);><h4 class='title'>" + albums[i].album + "</h4></a>";
                content += "</div>";
                content += "</div>";
                content += "<input type='hidden' id='albumId' value='" + albums[i].albumId + "'/></div>";
                $("#albums").append(content);
            }
        }
    });
}