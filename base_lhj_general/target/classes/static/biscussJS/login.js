$(function (){

});

const Toast = Swal.mixin({
    toast: true,
    position: 'container',
    showConfirmButton: false,
    timer: 1000
});

$("#loginBtn").click(function () {
    var data = "accountName=" + $('#accountName').val() + "&accountPassword=" + $('#accountPassword').val();
    console.log(data);
    $.ajax({
        type : "POST",
        url : "sys/login",
        data : data,
        dataType : "json",
        success : function(result) {
            console.info(result);
            if(result.code == 0){
                Toast.fire({
                    type: 'success',
                    title: result.msg
                })
                //toastr.success(result.msg)
                console.info(result);
                localStorage.setItem("accountId", result.accountId);
                localStorage.setItem("accountName", $('#accountName').val());
                localStorage.setItem("token", result.token);
                window.location.href = "index.html";
            }else{
                Toast.fire({
                    type: 'warning',
                    title: result.msg
                })
            }
        },
        error : function (result) {
            console.log(result);
        },
        fail:function(e){
            alert("fail");
        }
    })
})