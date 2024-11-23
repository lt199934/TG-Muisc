// 封装获取参数方法
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
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
$("#albums").on("click", "a", function () {
    var albumId = $(this).parent().parent().parent().find("#albumId").val();
    $(this).attr("href", "/albumDetail?albumId=" + albumId);
});

$("#songLists").on("click", "a", function () {
    // alert(window.location.href);
    var songListId = $(this).parent().find("#songListId").val();
    $(this).attr("href", "/songListDetail?songListId=" + songListId);
});

$("#songs").on("click", "#playOne", function () {
    // alert(window.location.href);
    // var url=window.location.href;
    // alert( url.substring(0,url.indexOf("/")+1))
    $("#player").css("display", "block");
    var songId = $(this).parent().parent().parent().parent().find("#songId").val();
    $(this).attr("href", "/play?temp=song&songId=" + songId);
});

$("#music").on("click", "#singer", function () {
    var singerId = $(this).parent().parent().find("#singerId").val();
    $(this).attr("href", "/singerDetail?singerId=" + singerId);
});
$("#singerList").on("click", "#home", function () {
    var singerId = $(this).parent().parent().parent().parent().find("#singerId").val();
    $(this).attr("href", "/singerDetail?singerId=" + singerId);
});

let userId = JSON.parse(localStorage.getItem("userId"));

let i = 0;

// 初始化菜单
function initNav() {
    i = 0;
    $("#login").css("display", "block");
    $("#register").css("display", "block");
    $("#logout").css("display", "none");
    $("#personalPage").css("display", "none");
    $("#headimg").css("display", "none");
    $("#profile").css("display", "none");
    $("#console").css("display", "none");
    $("#uc").html(" ");
}


initNav();

// 登录以后
function isLoginNav(data, num) {
    console.log(data.type)
    if (num === 0 && i === 0) {
        toastr.success("欢迎您" + data.nickName + "回来(*∩_∩*)")
    }
    i++;
    localStorage.removeItem("admin");
    if (data.type === "管理员") {
        console.log(data.userId)
        $("#console").css("display", "block");
        localStorage.setItem("admin", data.userId);
    }
    $("#login").css("display", "none");
    $("#register").css("display", "none");
    $("#logout").css("display", "block");
    $("#personalPage").css("display", "block");
    $("#headimg").css("display", "block");
    $("#profile").css("display", "block");
    $("#headimg").attr("src", data.headImg);
    $("#uc").html(data.nickName);
}

// 未登录
function isLogin() {
    toastr.options.onHidden = function () {
        location.href = "/login";
    }
    toastr.warning("请先登录！");
}

// 首页不需要强制登录
function isFirst(num) {
    if (null == userId) {
        initNav();
    } else {
        $.ajax({
            url: "/user/getUserInfo/" + userId, method: "get", success: function (res) {
                try {
                    console.log(res.data)
                    if (res.code === 200) {
                        isLoginNav(res.data, num);
                    } else {
                        localStorage.removeItem("userId");
                        initNav();
                        toastr.error(res.msg);
                    }
                } catch (e) {
                    console.log(e);
                }

            }
        });
    }
}

// 退出登录
$("#logout").click(function () {
    var message = confirm("是否退出登录?");
    if (message === true) {
        $.ajax({
            url: "/user/logout",
            data: {"userId": userId},
            method: "post",
            success: function (data) {
                location.href = data;
                localStorage.removeItem("userId");
            }
        });
    }
});

//分页
function makePage(data, type) {
    console.log(data, type);
    if (data.list.length !== 0) {
        var pageNums = data.navigatepageNums;
        if (data.pages === 1) {
            return;
        } else {
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:" + type + "(" + 1 + ");'><span aria-hidden=\"true\">首页</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        //是否由上一页
        if (data.hasPreviousPage === true) {
            var pageNum = data.pageNum - 1;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Previous' href='javascript:" + type + "(" + pageNum + ");'><span aria-hidden=\"true\">&laquo;</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        } else {
            $li = $("<li class='disabled'></li>");
            $btn = $("<a aria-label='Previous'><span aria-hidden=\"true\">&laquo;</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        // 页数
        for (var i = 0; i < pageNums.length; i++) {
            var pageNum = pageNums[i];
            $li = $("<li></li>");
            $btn = $("<a href='javascript:" + type + "(" + pageNum + ");'>" + pageNum + "</a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
        if (data.hasNextPage === true) {
            var pageNum = data.pageNum + 1;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:" + type + "(" + pageNum + ");'><span aria-hidden=\"true\">&raquo;</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        } else {
            $li = $("<li class='disabled'></li>");
            $btn = $("<a aria-label='Next'><span aria-hidden=\"true\">&raquo;</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }

        if (data.isLastPage !== true) {
            var pageNum = data.pages;
            $li = $("<li></li>");
            $btn = $("<a aria-label='Next' href='javascript:" + type + "(" + pageNum + ");'><span aria-hidden=\"true\">尾页</span></a>");
            $li.append($btn);
            $(".pagination").append($li);
        }
    } else {
        $(".pagination").empty();
    }
}

function checkRegisterForm() {
    $('#registerForm').bootstrapValidator({
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
                    threshold: 2,//有2字符以上才发送ajax请求
                    remote: {//ajax验证。server result:{"valid",true or false}
                        url: "/user/checkAccount",
                        message: '用户名已存在,请重新输入！',
                        delay: 1000,//ajax刷新的时间是1秒一次
                        type: 'POST',
                        //自定义提交数据，默认值提交当前input value
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '用户名只能包含大写、小写、数字和下划线！'
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
            },
            repwd: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空！'
                    },
                    stringLength: {
                        max: 18,
                        message: '用户名长度不能超过6位！'
                    },
                    identical: {
                        field: 'pwd',
                        message: '两次输入的密码不相符'
                    }
                }
            },

            phone: {
                validators: {
                    notEmpty: {
                        message: '电话不能为空！'
                    },
                    regexp: {
                        regexp: /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/,
                        message: '电话号码格式错误！'
                    }

                }
            },

            userName: {
                validators: {
                    notEmpty: {
                        message: '姓名不能为空！'
                    },
                    regexp: {
                        regexp: /^[\u4E00-\u9FA5]{1,6}$/,
                        message: '姓名格式错误！'
                    }

                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: '邮箱不能为空'
                    },
                    emailAddress: {
                        message: '邮箱地址格式有误！'
                    },
                    regexp: {
                        regexp: /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/,
                        message: '邮箱格式不正确！'
                    }
                }
            },
            sex: {
                validators: {
                    notEmpty: {
                        message: '请选择性别！'
                    }


                }
            },
            img: {
                validators: {
                    file: {
                        extension: 'png,jpg,jpeg',
                        type: 'image/png,image/jpg,image/jpeg',
                        message: '只能上传格式为image/png,image/jpg,image/jpeg的文件，请重新选择图片!'
                    }
                }
            },
            birthday: {
                validators: {
                    notEmpty: {
                        message: '生日不能为空'
                    },
                    date: {
                        format: 'YYYY/MM/DD',
                        message: '日期格式不正确'
                    }
                }
            }

        }
    })
}

function checkSaveForm() {
    $('#registerForm').bootstrapValidator({
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
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '用户名只能包含大写、小写、数字和下划线！'
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
            },
            repwd: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空！'
                    },
                    stringLength: {
                        max: 18,
                        message: '用户名长度不能超过6位！'
                    },
                    identical: {
                        field: 'pwd',
                        message: '两次输入的密码不相符'
                    }
                }
            },

            phone: {
                validators: {
                    notEmpty: {
                        message: '电话不能为空！'
                    },
                    regexp: {
                        regexp: /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/,
                        message: '电话号码格式错误！'
                    }

                }
            },

            userName: {
                validators: {
                    notEmpty: {
                        message: '姓名不能为空！'
                    },
                    regexp: {
                        regexp: /^[\u4E00-\u9FA5]{1,6}$/,
                        message: '姓名格式错误！'
                    }

                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: '邮箱不能为空'
                    },
                    emailAddress: {
                        message: '邮箱地址格式有误！'
                    },
                    regexp: {
                        regexp: /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/,
                        message: '邮箱格式不正确！'
                    }
                }
            },
            sex: {
                validators: {
                    notEmpty: {
                        message: '请选择性别！'
                    }


                }
            },
            img: {
                validators: {
                    file: {
                        extension: 'png,jpg,jpeg',
                        type: 'image/png,image/jpg,image/jpeg',
                        message: '只能上传格式为image/png,image/jpg,image/jpeg的文件，请重新选择图片!'
                    }
                }
            },
            birthday: {
                validators: {
                    notEmpty: {
                        message: '生日不能为空'
                    },
                    date: {
                        format: 'YYYY/MM/DD',
                        message: '日期格式不正确'
                    }
                }
            }

        }
    })
}

function checkSingerForm() {
    $('#singerForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'green',
            invalid: 'red',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            singerName: {
                validators: {
                    notEmpty: {
                        message: ' 名称不能为空！'
                    },
                    regexp: {
                        regexp: /^[\u4E00-\u9FA5]{1,6}$/,
                        message: '名称格式错误！'
                    }
                }
            },
            sex: {
                validators: {
                    notEmpty: {
                        message: '请选择性别！'
                    }


                }
            },
            img: {
                validators: {
                    file: {
                        extension: 'png,jpg,jpeg',
                        type: 'image/png,image/jpg,image/jpeg',
                        message: '只能上传格式为image/png,image/jpg,image/jpeg的文件，请重新选择图片!'
                    }
                }
            },
            birthday: {
                validators: {
                    notEmpty: {
                        message: '生日不能为空'
                    },
                    date: {
                        format: 'YYYY/MM/DD',
                        message: '日期格式不正确'
                    }
                }
            }

        }
    })
}
