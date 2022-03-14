function getUrlParam(name) {
    var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
    var r=window.location.search.substr(1).match(reg);
    if (r!=null){
        return unescape(r[2]);
    }
    return null;
}

//隐藏地址
$("#albums").on("click","a",function () {
    var albumId= $(this).parent().parent().parent().find("#albumId").val();
    $(this).attr("href","http://localhost:8080/musicwebsite/music/user/album/album.html?albumId="+albumId);
});

$("#songLists").on("click","a",function () {
    var songListId= $(this).parent().find("#songListId").val();
    $(this).attr("href","http://localhost:8080/musicwebsite/music/user/songList/songlist.html?songListId="+songListId);
});

$("#music").on("click","#playOne",function () {
    var songId= $(this).parent().parent().find("#songId").val();
    $(this).attr("href","http://localhost:8080/musicwebsite/music/user/play/player.html?temp=song&songId="+songId);
});

$("#music").on("click","#singer",function () {
    var singerId= $(this).parent().parent().find("#singerId").val();
    $(this).attr("href","http://localhost:8080/musicwebsite/music/user/singer/singer.html?singerId="+singerId);
});
$("#singerList").on("click","a",function () {
    var singerId= $(this).parent().parent().find("#singerId").val();
    $(this).attr("href","http://localhost:8080/musicwebsite/music/user/singer/singer.html?singerId="+singerId);
});
