function delUser(userid) {
    alert(userid)
    $.ajax({
        "url": "http://localhost:8080/musicwebsite/delUser/1"+userid,
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

