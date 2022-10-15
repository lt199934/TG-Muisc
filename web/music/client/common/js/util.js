function getUrlParam(name) {
    var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
    var r=window.location.search.substr(1).match(reg);
    if (r!=null){
        return unescape(r[2]);
    }
    return null;
}
// 消息提示框
toastr.options = {
    closeButton: true,
    debug: false,
    progressBar: true,
    positionClass: "toast-top-right",
    onclick: null,
    showDuration: "1000",
    hideDuration: "1000",
    timeOut: "3000",
    extendedTimeOut: "1000",
    showEasing: "swing",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut",
    closeMethod: "fadeOut",
};


//隐藏地址
$("#albums").on("click","a",function () {
    var albumId= $(this).parent().parent().parent().find("#albumId").val();
    $(this).attr("href","/albumDetail?albumId="+albumId);
});

$("#songLists").on("click","a",function () {
    // alert(window.location.href);

    var songListId= $(this).parent().find("#songListId").val();
    $(this).attr("href","/songListDetail?songListId="+songListId);
});

$("#music").on("click","#playOne",function () {
    // alert(window.location.href);
    // var url=window.location.href;
    // alert( url.substring(0,url.indexOf("/")+1))
    var songId= $(this).parent().parent().find("#songId").val();
    $(this).attr("href","/play?temp=song&songId="+songId);
});

$("#music").on("click","#singer",function () {
    var singerId= $(this).parent().parent().find("#singerId").val();
    $(this).attr("href","/singerDetail?singerId="+singerId);
});
$("#singerList").on("click","#home",function () {
    var singerId= $(this).parent().parent().parent().parent().find("#singerId").val();
    $(this).attr("href","/singerDetail?singerId="+singerId);
});
