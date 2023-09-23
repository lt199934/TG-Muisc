$(function () {
    getUsers(1);
    $("#selectForm").change(function () {
        getUsers(1);
    });
    $("#pageSize").change(function () {
        getUsers(1);
    });

    $("#subBtn").click(function () {
        // console.log($(this).val())
        if ($(this).val() === "更新") {
            save();
            return;
        }
        checkRegisterForm();
        var form = $userForm[0];
        var formData = new FormData(form);
        $userForm.data('bootstrapValidator').validate();//手动开启验证
        var flag = $userForm.data('bootstrapValidator').isValid();
        console.log(flag);
        if (flag) {
            $.ajax({
                url: saveURL,
                method: "post",
                data: formData,
                dataType: 'JSON',
                cache: false,
                processData: false,
                contentType: false,
                success: function (data) {
                    console.log(data);
                    if (data.code === 200) {
                        if (data.code === 200) {
                            toastr.success(data.msg, null, {positionClass: "toast-top-center"});
                            $userForm[0].reset();
                            $userForm.data('bootstrapValidator').resetForm(true);
                            $userForm.modal("hide");
                            getUsers(1);
                        } else {
                            toastr.error(data.msg, null, {positionClass: "toast-top-center"});
                        }
                    }
                }
            });
        }

    });

    $("#menu").on('click', 'li', function () {
        $('#menu li').removeClass('active');
        $(this).addClass('active');
    })
});

let pageURL = "/user/page"
let saveURL = "/user/save";
let deleteURL = "/user/delUser/";
let $userForm = $("#userForm");
const init = () => {
    $("#myModalLoginLabel").html("添加用户");
    $("#subBtn").val("添加");
    $(".userId").css('display', 'none');
    $("#imgSrc").css('display','none');
    $userForm[0].reset();
    $userForm.data('bootstrapValidator').destroy();
    $userForm.data('bootstrapValidator', null);

};
const resetForm = () => {
    $("#selectForm")[0].reset();
    getUsers(1);
};

const edit = (user) => {
    $(".userId").css('display', 'block');
    $("#imgSrc").css('display','block');
    $("#myModalLoginLabel").html("修改用户信息");
    $("#subBtn").val("更新");
    $("#userId").val(user.userId);
    $("#account").val(user.account);
    $("#userName").val(user.userName);
    $("#pwd").val(user.pwd);
    $("#nickName").val(user.nickName);
    $("#phone").val(user.phone);
    $("#email").val(user.email);
    $("#imgSrc").attr("src",user.headImg);
    let sexes = document.getElementById("sexes");
    for (var i = 0; i < sexes.length; i++) {
        if (sexes[i].value === user.sex) {
            sexes[i].selected = true;
        }
    }
    $("#birthday").val(user.birthday);
    $("#personalSignature").val(user.personalSignature);
    $("#userModal").modal("show");
}

const save = () => {
    checkSaveForm();
    let form = $userForm[0];
    let formData = new FormData(form);
    $.ajax({
        url: saveURL,
        method: "post",
        data: formData,
        dataType: 'JSON',
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            console.log(data);
            if (data.code === 200) {
                $("#userModal").modal("hide");
                toastr.success(data.msg, null, {positionClass: "toast-top-center"});
            } else {
                toastr.error(data.msg, null, {positionClass: "toast-top-center"});
            }
            getUsers(1);
            $userForm[0].reset();
            $userForm.data('bootstrapValidator').resetForm();
        }
    });
}

const getUsers = (pageNum) => {
    $(".pagination").empty();
    $("#userBox").empty();
    var data = {
        "userId": $("#id").val(),
        "account": $("#name").val(),
        "phone": $("#userphone").val(),
        "email": $("#useremail").val(),
        "sex": $("#sex").val(),
        "startDate": $("#startDate").val(),
        "endDate": $("#endDate").val(),
        "pageNum": pageNum,
        "pageSize": $("#pageSize").val(),
    }
    $.ajax({
        headers: {
            'Accept': 'application/json', 'Content-Type': 'application/json'
        }, url: pageURL, method: "post", data: JSON.stringify(data), success: function (res) {
            makePage(res.data, 'getUsers');
            var users = res.data.list;
            $("#total").html(res.data.total);
            for (var i = 0; i < users.length; i++) {
                var $tr = $("<tr>");
                var $userId = $("<td>");
                var $account = $("<td>");
                var $pwd = $("<td>");
                var $nickName = $("<td>");
                var $phone = $("<td>");
                var $email = $("<td>");
                var $username = $("<td>");
                var $sex = $("<td>");
                var $headImg = $("<td>");
                var $type = $("<td>");
                var $personalSignature = $("<td>");
                var $birthday = $("<td>");
                var $time = $("<td>");
                var $opera = $("<td>");
                $tr.append($userId.html(users[i].userId))
                    .append($headImg.html('<img src="' + users[i].headImg + '" style="width:50px;height:50px;border: 1px solid;" alt="加载失败">'))
                    .append($account.html(users[i].account))
                    .append($pwd.html(users[i].pwd))
                    .append($nickName.html(users[i].nickName))
                    .append($phone.html(users[i].phone))
                    .append($email.html(users[i].email))
                    .append($username.html(users[i].userName))
                    .append($sex.html(users[i].sex))
                    .append($type.html(users[i].type))
                    .append($personalSignature.html(users[i].personalSignature))
                    .append($birthday.html(users[i].birthday))
                    .append($time.html(users[i].time))
                    .append($opera.html('<button type="button" class="btn btn-success btn-sm" data-target="#userModal" onclick="edit(' + JSON.stringify(users[i]).replace(/\"/g, "&quot;").replace(/\'/g, "\\\'") + ')"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑 </button> <button type="button" class="btn btn-danger btn-sm" onclick="del(' + users[i].userId + ')"><i class="glyphicon glyphicon-remove" aria-hidden="true"></i> 删除 </button>'));
                $("#userBox").append($tr);
            }
        }
    });
}

const del = (userId) => {
    deleteURL = deleteURL + userId;
    $.ajax({
        url: deleteURL, method: "DELETE", success: function (data) {
            if (data === 1) {
                alert("删除成功");
            }
            getUsers(1);
        }
    });
}





