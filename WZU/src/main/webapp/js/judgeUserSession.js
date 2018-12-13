$(function() {

	judgeUserSession();
});


function judgeUserSession() {
	$.ajax({
		url : "judgeUserSession",
		async: false,
		success : function(data) { 
			if (data.status) { 
				//放行 
			}else{
				alert("请登录");
				window.location.href = 'login';
			}
		} 
	});
}