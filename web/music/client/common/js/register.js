$(function(){
    checkRegisterForm();
});


//校验注册信息
function checkRegisterForm(){
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
                                 url: "/checkAccount",
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
                            min:6,
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
                    date : {
                        format : 'YYYY/MM/DD',
                        message : '日期格式不正确'
                }
                    }
                }

            }
        });
}

//提交注册信息
$("#regBtn").click(function(){
    // $("#myRegisterModal").modal("show");
    var date = new Date();
    var form=$("#registerForm")[0];
    var time=date.getFullYear()+"-"+date.getMonth()+"-"+date.getDate();
    console.log(time);
    var formData =new FormData(form);
    formData.append("time",time);
    console.log(formData);
     $('#registerForm').data('bootstrapValidator').validate();//手动开启验证
         var flag = $('#registerForm').data('bootstrapValidator').isValid();//
         console.log(flag);
    if(flag==true){
        var $btn = $(this).button('loading');
        $.ajax({
            "url": "/userRegister",
            method: "post",
            data: formData,
            async:false,
            contentType:false,
            processData:false,
            success: function (data) {
                console.log(data);
                if (data == -1) {
                    toastr.warning('用户已存在');
                    $("#registerForm")[0].reset();
                    $btn.button('reset');
                } else if (data == 1) {
                    $("#registerForm")[0].reset();
                    toastr.options.onHidden = function () {
                        window.location.href = "/login";
                    }
                    toastr.success('注册成功');
                } else {
                    toastr.error('注册失败');
                    $("#registerForm")[0].reset();
                    $("#registerForm").data('bootstrapValidator').resetForm();
                }
            }
        });
    }
});

