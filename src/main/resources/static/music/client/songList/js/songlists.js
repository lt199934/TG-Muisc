$(function () {
    $('.simplefilter').on("click", "li", function () {
        var fenId = $(this).find("#fenId").val();
        getSongLists(1, fenId);
        $('.simplefilter li').removeClass('active');
        $(this).addClass('active');
    });
    isFirst(1);
    getSongLists(1, 0);
    getFenLei();
    $('.filtr-container').filterizr();

});

function getSongLists(pageNum, fenId) {
    var data = {
        "pageNum": pageNum,
        "pageSize": 1,
        "fenId": fenId ? fenId : 0,
    }
    $.ajax({
        "url": "/songList/page",
        method: "post",
        data: data,
        success: function (data) {
            $(".pagination").empty();
            $("#songLists").empty();
            $(".num").html(data.total);
            makePage(data, 'getSongLists');
            console.log("歌单列表", data);
            var songList = data.list;
            if (0 === songList.length) {
                $("#songLists").html("<h1 class='text-center'>暂无数据</h1>");
                return;
            }
            for (var i = 0; i < songList.length; i++) {
                let content = "<div  class='col-xs-12 col-sm-12 col-md-3 filtr-item' data-category='2, 3' data-sort='欧美/流行'>";
                content += "<a target='_blank' rel='opener'  href=javascript:void(0);><img class='img-responsive img-rounded' src='" + songList[i].imgUrl + "' alt='sample image'></a>";
                content += "<a target='_blank' rel='opener' href=javascript:void(0);><span class='item-desc'>" + songList[i].songList + "</span></a>";
                content += "<input type='hidden' id='songListId' value='" + songList[i].songListId + "'>";
                content += "</div>";
                $("#songLists").append(content);
            }
        }
    });
}


function getFenLei() {
    $(".simplefilter").empty();
    $.ajax({
        "url": "/songList/fenLei",
        method: "post",
        success: function (data) {
            // console.log(data);
            var content = " <li class='active' data-filter='all'><input type='hidden' id='fenId' value=0>全部</li>";
            for (var i = 0; i < data.length; i++) {
                // console.log(data[i].fenId)
                content += "<li ><input type='hidden' id='fenId' value='" + data[i].fenId + "'>" + data[i].content + "</li>";
                $(".simplefilter").html(content);
            }
        }
    });
}

