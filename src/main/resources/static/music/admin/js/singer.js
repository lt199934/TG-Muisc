$(function () {
    getSingers(1);
    checkSingerForm();
});
$("#selectForm").change(function () {
    getSingers(1);
});
$("#pageSize").change(function () {
    getSingers(1);
});

let baseUrl = "/singer";
let singerId;
let $singerForm = $("#singerForm");

function getSingers(pageNum) {
    $("#singerTbody").empty();
    var url = baseUrl + "/page";
    var data = {
        "singerName": $("#name").val(),
        "sex": $("#singerSex").val(),
        "startDate": $("#startDate").val(),
        "endDate": $("#endDate").val(),
        "pageNum": pageNum,
        "pageSize": $("#pageSize").val(),
    }
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: url,
        method: "post",
        data: JSON.stringify(data),
        success: function (res) {
            $(".pagination").empty();
            makePage(res.data, 'getSingers');
            var singer = res.data.list;
            $("#total").text(res.data.total);
            for (var i = 0; i < singer.length; i++) {
                var $tr = $("<tr>");
                var $singerId = $("<td>");
                var $imgUrl = $("<td>");
                var $singerName = $("<td>");
                var $sex = $("<td>");
                var $birthday = $("<td>");
                var $intrduction = $("<td>");
                var $opera = $("<td>");
                console.log(singer[i].singerId);
                console.log(singer[i].imgUrl);
                $tr.append($singerId.html(singer[i].singerId))
                    .append($imgUrl.html('<img src="' + singer[i].imgUrl + '" style="width:50px;height:50px;border: 1px solid;" alt="加载失败">'))
                    .append($singerName.html(singer[i].singerName))
                    .append($sex.html(singer[i].sex))
                    .append($birthday.html(singer[i].birthday)).append($intrduction.html(singer[i].intrduction))
                    .append($opera.html('<button type="button" class="btn btn-success btn-sm" data-target="#singerModal" onclick="edit(' + JSON.stringify(singer[i]).replace(/\"/g, "&quot;").replace(/\'/g, "\\\'") + ')"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑 </button> <button type="button" class="btn btn-danger btn-sm" onclick="del(\' + singer[i].singerId + \')"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 删除 </button>'));
                $("#singerTbody").append($tr);
            }
        }
    });
}

const init = () => {
    $("#myModalLoginLabel").html("添加歌手");
    $("#subBtn").val("添加");
    $(".singerId").css('display', 'none');
    $("#imgSrc").css('display','none');
    $singerForm[0].reset();
    $singerForm.data('bootstrapValidator').destroy();
    $singerForm.data('bootstrapValidator', null);

};
const resetForm = () => {
    $("#selectForm")[0].reset();
    getSingers(1);
};


$("#subBtn").click(function () {
    var url = baseUrl + "/save";
    var date = new Date();
    var form = $("#singerForm")[0];
    var time = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate();
    // console.log(time);
    var formData = new FormData(form);
    formData.append("time",time);
    console.log(formData);
    $singerForm.data('bootstrapValidator').validate();//手动开启验证
    var flag = $singerForm.data('bootstrapValidator').isValid();
    $.ajax({
        url: url,
        method: "post",
        data: formData,
        dataType: 'JSON',
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            if (data.code === 200) {
                toastr.success(data.msg, null, {positionClass: "toast-top-center"});
                $singerForm[0].reset();
                $("#singerModal").modal("hide");
            } else {
                toastr.warning(data.msg, null, {positionClass: "toast-top-center"});
            }

            getSingers(1);
        }
    });
});


function del(singerId) {
    var url = "/delSinger/" + singerId;
    $.ajax({
        url: url,
        method: "post",
        success: function (data) {
            console.log(data);
            if (data == 1) {
                alert("删除成功！");
                getUsers();
            }
        }
    });
}

function edit(singer) {
    $("#subBtn").val("更新");
    $("#singerId").val(singer.singerId);
    $("#singerName").val(singer.singerName);
    $("#intrduction").val(singer.intrduction);
    $("#birthday").val(singer.birthday);
    $("#imgSrc").css("display",'block');
    $("#imgSrc").attr("src",singer.imgUrl)
    console.log(singer.birthday)
    let sexes = document.getElementById("sex");
    for (var i = 0; i < sexes.length; i++) {
        if (sexes[i].value === singer.sex) {
            sexes[i].selected = true;
        }
    }
    $("#singerModal").modal("show");
}