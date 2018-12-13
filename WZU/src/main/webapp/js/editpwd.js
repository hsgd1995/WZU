$(function() {
	editValidate();
	
});

function editValidate() {
	$('form').bootstrapValidator(
			{
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					password : {
						validators : {
							notEmpty : {
								message : '密码不能为空'
							},
							stringLength : {
								min : 6,
								max : 18,
								message : '密码长度必须在6到18位之间'
							},
							regexp : {
								regexp : /^[a-zA-Z0-9_]+$/,
								message : '密码只能包含大写、小写、数字和下划线'
							}
						}
					},
					confirm : {
						validators : {
							notEmpty : {
								message : '确认密码不能为空'
							},
							identical: {// 相同
		                         field: 'password',
		                         message: '两次密码不一致'
		                     }
						}
					},
					code : {
						validators : {
							notEmpty : {
								message : '验证码不能为空'
							},
							regexp : {
								regexp : /^[0-9_]+$/,
								message : '验证码只允许输入数字'
							},
							stringLength : {
								min : 6,
								max : 6,
								message : '验证码长度为6'
							}
						}
					},
				}
			}).on('success.form.bv', function(e) {
		// 阻止默认提交
		e.preventDefault();
		// ajax提交表单
		submitHandler();
	});
}



function submitHandler() {
	$("#edit").unbind().click(function(e) {
		// 阻止默认提交
		e.preventDefault();
		var $formParams = getParams($('form').serializeArray());
		//console.log($formParams);
		$.ajax({
			url : "editPassword",
			method : 'post',
			data : $formParams,
			dataType : 'json',
			success : function(resp) {
				if (resp.status) {
					//console.log(resp.status);
					alert(resp.message);
					window.location.href = "login";
				} else {
					//console.log(resp.message);
					alert(resp.message);
				}
			}
		})
		}
	);
}