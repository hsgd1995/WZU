$(function() {
	login();
});

function login() {
	$("#login").click(function name() {
		
		var $formParams = getParams($('#loginForm').serializeArray());
		$.ajax({
			url : "backManagerLogin",
			method : 'post',
			data : $formParams,
			dataType : 'json',
			success : function(resp) {
				if (resp.message) {
					window.location.href = "main";
					//console.log(resp.message);
				} else {
					$('#prompt').removeClass('hide');
				}
			},
			error : function(resp){
				alert('该账号没有权限登录本系统！');
			}
		})
		}
	);
}