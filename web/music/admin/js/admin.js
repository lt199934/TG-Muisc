function delUser(userid) {
    alert(userid)
    $.ajax({
        "url": "/delUser/"+userid,
        method: "post",
        success: function (data) {
            console.log(data);
            if (data==1){
                alert("删除成功！");
                getUsers();
            }
        }
    });
}

