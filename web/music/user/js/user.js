$(function(){
    checkRegisterForm();
    checkLoginForm();
    $("#myRegisterModal").modal("hide");
});


//校验登录信息
function checkLoginForm(){
        $('#loginForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
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
                }
        
            }
        });
}


//提交登录信息
$("#subBtn").click(function(){
    // $("#myLoginModal").modal("show");
    var user={"account":$("#account").val(),
        "pwd":$("#pwd").val()
    }
    $('#loginForm').data('bootstrapValidator').validate();//手动开启验证
         var flag = $('#loginForm').data('bootstrapValidator').isValid();//
         console.log(flag);
         if(flag==true){
        $.ajax({
            "url": "/userLogin",
            method: "post",
            data: user,
            success: function (data) {
                console.log(data);
                var user =data;
                if(null!=data&&""!=data){
                    alert("登录成功");
                    console.log("登录成功");
                    sessionStorage.setItem("user",JSON.stringify(data));
                    window.location.href="/";
                }else{
                    alert("登录失败!该用户不存在");
                    console.log("登录失败");


                }
            }

        });

    }

});

//校验注册信息
function checkRegisterForm(){
    $('#registerForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
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
                if(data==-1){
                    alert("用户已存在！");
                    $("#registerForm")[0].reset();
                    $btn.button('reset');
                }else if(data==1){
                    alert("注册成功！");
                    $("#registerForm")[0].reset();
                    $("#myRegisterModal").modal("hide");
                    $("#myLoginModal").modal("show");
                    $btn.button('reset');
                }else {
                    alert("注册失败！");
                    $("#registerForm")[0].reset();
                    $btn.button('reset');
                }

            }
        });
    }
});
//情况modal里的验证
$("#myLoginModal").on("hide.bs.modal",function () {
    $("#loginForm")[0].reset();
    $("#loginForm").data('bootstrapValidator').resetForm();
});
$("#myRegisterModal").on("hide.bs.modal",function () {
    $("#regBtn").button('reset');
    $("#registerForm")[0].reset();
    $("#registerForm").data('bootstrapValidator').resetForm();
});
