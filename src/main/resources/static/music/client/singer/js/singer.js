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
            console.log("歌手详情",data);
            var content=" ";
            var singers=data.list;
            for (var i=0;i<4;i++){
                content="<div class='col-md-6 text-primary' style='font-family: 楷体;font-size: 18px;'><dt class='pull-left'><img style='width: 200px;height: 200px;border: 1px solid cyan;' src='"+singers[i].imgUrl+"'></dt>";
                content+="<div class='pull-left col-md-6'><dd>歌手：<a href='http://localhost:8080/musicwebsite/music/user/singerpage.html?singerId="+singers[i].singerId+"'>"+singers[i].singerName+"</a></dd>";
                content+="<dd>性别："+singers[i].sex+"</dd>";
                content+="<dd>个人简介 ："+singers[i].intrduction+"</dd></div>";
                content+="<input type='hidden' id='singerId' value="+singers[i].singerId+"></div>";
                $("#singer dl").append(content);
            }

        }
    });
}
//通过id删除指定歌手
function delSinger(singerId) {
    if(confirm("确定删除该位歌手吗?")){
        $.ajax({
            "url": "/delSong/"+singerId,
            method: "post",
            success: function (data) {
                console.log(data);
                if (data==1){
                    alert("删除成功！");
                    getSongs();
                }
            }
        });
    }

}