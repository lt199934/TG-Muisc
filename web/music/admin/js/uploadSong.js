    $(function () {

    });

    $("#subBtn").click(function () {
        var date = new Date();
        var time=date.getFullYear()+"-"+date.getMonth()+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
        console.log(time);
        var form=$("#songForm")[0];
        var song=new FormData(form);
        song.append("time",time);
        console.log(song);
        $.ajax({
            "url": "/uploadSong",
            method: "post",
            data: song,
            async:false,
            contentType:false,
            processData:false,
            success: function (data) {
                console.log(data);

            }
        });
    });

    function getSongs(pageNum) {
        var data = {
            "pageNum": pageNum,
            "pageSize": $("#pageSize").val(),
        }
        console.log(pageNum);
        $.ajax({
            "url": "/songs",
            method: "post",
            data: data,
            success: function (data) {
                console.log(data);

            }
        });
    }



