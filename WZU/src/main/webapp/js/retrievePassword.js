$(function() {
	$code = null;// 全局变量保存验证码
	getCodeFromSession();
	Validate();
});

/**
 * bootstrap表单验证
 */
function Validate() {
	$('form').bootstrapValidator(
					{
						feedbackIcons : {
							valid : 'glyphicon glyphicon-ok',
							invalid : 'glyphicon glyphicon-remove',
							validating : 'glyphicon glyphicon-refresh'
						},
						fields : {
							phone : {
								validators : {
									notEmpty : {
										message : '手机号不能为空'
									},
									stringLength : {
										max : 11,
										message : '手机号格式错误'
									}
								}
							},
							code: {
								validators : {
									callback : {
										message : '验证码不正确',
										callback : function(value, validator) {
											var $inputCode = $(
													'input[name=code]').val();
											return $code
													&& $inputCode
													&& $code
															.toLocaleUpperCase() == $inputCode
															.toLocaleUpperCase();
										}
									}
								}
							}
						}
					}).on('success.form.bv', function(e) {
				// 阻止默认提交
				e.preventDefault();
				// ajax提交表单
				retrieve();
			});
}

function retrieve() {
	$("#sendPhone").unbind().click(function(e) {
		// 阻止默认提交
		e.preventDefault();
		var $formParams = getParams($('form').serializeArray());
		//console.log($formParams);
		$.ajax({
			url : "retrievePassword",
			method : 'post',
			data : $formParams,
			dataType : 'json',
			success : function(resp) {
				if(resp.errorMsg){
					//console.log(resp.errorMsg);
					alert(resp.message);
				}
				else if (resp.status) {
					//console.log(resp.status);
					alert(resp.message);
					window.location.href = "editpwd";
				}else{
					//console.log(resp.status);
					alert(resp.message);
				}
				
			}
		})
		}
	);
}


/**
 * 验证码
 */
function getCodeFromSession() {
	$.ajax({
		url : 'getCode/getSession',
		async : false,
		success : function (data) {
			if(data.code){
				$code = data.code;
				//console.log($code);
			}
		}
	});
}

/**
 * 点击更换验证码
 */
function getCode(that) {
	that.src='getCode?d=' + Math.random();
	getCodeFromSession();
}