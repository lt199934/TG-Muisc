$(function () {
    checkLoginForm();
});
let userSession = JSON.parse(localStorage.getItem("userId"));
console.log(userSession)
if (null != userSession) {
    toastr.options.onHidden = function () {
        window.location.href = "/";
    }
    toastr.warning('请勿重复登录！');
}

//校验登录信息
function checkLoginForm() {
    $('#loginForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'green',
            invalid: 'red',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            account: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空！'
                    },
                    stringLength: {
                        max: 6,
                        message: '用户名长度不能超过6位！'
                    },
                    different: {
                        field: 'pwd',
                        message: '用户名不能和密码一样！'
                    }
                }
            },
            pwd: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空！'
                    },
                    stringLength: {
                        min: 6,
                        max: 12,
                        message: '密码长度必须为6-12位！！'
                    },
                    identical: {
                        field: 'repwd',
                        message: '两次输入的密码不相符'
                    },
                    different: {
                        field: 'account',
                        message: '密码不能和用户名一样！'
                    }
                }
            }
        }
    });
}

//提交登录信息
$("#subBtn").click(function () {
    const user = {
        "account": $("#account").val(),
        "pwd": $("#pwd").val(),
        "type": "普通用户"
    }
    $('#loginForm').data('bootstrapValidator').validate();//手动开启验证
    var flag = $('#loginForm').data('bootstrapValidator').isValid();//
    console.log(flag);
    var isExited = true;
    console.log(userSession)
    if (null == userSession) {
        isExited = false;
    } else {
        toastr.options.onHidden = function () {
            window.location.href = "/";
        }
        toastr.warning('请勿重复登录！');
    }
    if (flag === true && isExited === false) {
        $.ajax({
            "url": "/user/userLogin",
            method: "post",
            data: user,
            success: function (res) {
                console.log(res.data);
                if (res.code === 200) {
                    console.log(res.data)
                    localStorage.setItem("userId", JSON.stringify(res.data));
                    toastr.options.onHidden = function () {
                        window.location.href = "/";
                    }
                    toastr.success(res.msg);
                } else {
                    toastr.error(res.msg);
                    $("#loginForm")[0].reset();
                    $("#loginForm").data('bootstrapValidator').resetForm();
                }
            }
        });
    }
});
//回车登录
$('#loginForm').bind('keydown', function (event) {
    if (event.keyCode === "13") {
        $('#subBtn').click();
    }
});


