/**
 * 申请进驻
 */

$(function() {
	var index;
	var keyword;
	// 绑定管理员事件
	managerHandler();
	// 请求管理员数据事件
	requestManager();
	
	// 搜索事件
	managerSeach(index);
	//全局变量 展开/折叠标志
	showAndHideFlag = true; 
	$('#showAndHideBtn').unbind().click(function(e){
		if(showAndHideFlag){
			$('#demo').collapse('show');
			$('#shouAndHide').html('折叠');
			$('#shouAndHideIcon').attr('class','glyphicon glyphicon-minus-sign');
			showAndHideFlag = false;
		}else{
			$('#demo').collapse('hide');
			$('#shouAndHide').html('展开');
			$('#shouAndHideIcon').attr('class','glyphicon glyphicon-plus-sign');
			showAndHideFlag = true; 
		}
	});
	
	
});


function managerHandler() {
	// 保存管理员事件
	saveManagerFunc();
	// 更新管理员事件
	delManagerFunc();
}

/**
 * 保存管理员事件
 */
function saveManagerFunc() {
	$('#saveManager').unbind().click(function() {
		var formData = new FormData();
		formData.append("id", $("#managerForm [name=id]").val());
		formData.append("name", $("#managerForm [name=name]").val());
		formData.append("projectSetupTime", $("#managerForm [name=projectSetupTime]").val());
		formData.append("projectEnteringTime",$("#managerForm [name=projectEnteringTime]").val());
		formData.append("isBusinessRegistration",$("#managerForm [name=isBusinessRegistration]").val());
		formData.append("registeredTime",$("#managerForm [name=registeredTime]").val());
		formData.append("registrationCapital",$("#managerForm [name=registrationCapital]").val());
		formData.append("projectIndustry",$("#managerForm [name=projectIndustry]").val());
		formData.append("principalName",$("#managerForm [name=principalName]").val());
		formData.append("principalSex",$("#managerForm [name=principalSex]").val());
		formData.append("principalCorporatePosition",$("#managerForm [name=principalCorporatePosition]").val());
		formData.append("principalPoliticalStatus",$("#managerForm [name=principalPoliticalStatus]").val());
		formData.append("principalSno",$("#managerForm [name=principalSno]").val());
		formData.append("principalSecondaryCollegeId",$("#managerForm [name=principalSecondaryCollegeId]").val());
		formData.append("principalGradeId",$("#managerForm [name=principalGradeId]").val());
		formData.append("principalClass",$("#managerForm [name=principalClass]").val());
		formData.append("principalDormitory",$("#managerForm [name=principalDormitory]").val());
		formData.append("principalPhone",$("#managerForm [name=principalPhone]").val());
		formData.append("principalQq",$("#managerForm [name=principalQq]").val());
		formData.append("principalEmail",$("#managerForm [name=principalEmail]").val());
		formData.append("principalPosition",$("#managerForm [name=principalPosition]").val());
		formData.append("principalCertificate",$("#managerForm [name=principalCertificate]").val());
		formData.append("principalScoreDescription",$("#managerForm [name=principalScoreDescription]").val());
		formData.append("legalName",$("#managerForm [name=legalName]").val());
		formData.append("legalSex",$("#managerForm [name=legalSex]").val());
		formData.append("legalPoliticalStatus",$("#managerForm [name=legalPoliticalStatus]").val());
		formData.append("legalBirthplace",$("#managerForm [name=legalBirthplace]").val());
		formData.append("legalGrade",$("#managerForm [name=legalGrade]").val());
		formData.append("legalClazz",$("#managerForm [name=legalClazz]").val());
		formData.append("legalPosition",$("#managerForm [name=legalPosition]").val());
		formData.append("legalPhone",$("#managerForm [name=legalPhone]").val());
		formData.append("legalEmail",$("#managerForm [name=legalEmail]").val());
		
		formData.append("projectType",$("#managerForm [name=projectType]").val());
		formData.append("initialFunds",$("#managerForm [name=initialFunds]").val());
		formData.append("workStudyNum",$("#managerForm [name=workStudyNum]").val());
		formData.append("recentGraduatesNum",$("#managerForm [name=recentGraduatesNum]").val());
		formData.append("previousGraduatesNum",$("#managerForm [name=previousGraduatesNum]").val());
		formData.append("totalEmployment",$("#managerForm [name=totalEmployment]").val());
		formData.append("totalPractice",$("#managerForm [name=totalPractice]").val());
		formData.append("yearEmploymentNum",$("#managerForm [name=yearEmploymentNum]").val());
		formData.append("yearPracticeNum",$("#managerForm [name=yearPracticeNum]").val());
		formData.append("isEffective",$("#managerForm [name=isEffective]").val());
		formData.append("isBase",$("#managerForm [name=isBase]").val());
		formData.append("status",$("#managerForm [name=status]").val());
		formData.append("manageContent",$("#managerForm [name=manageContent]").val());
		
		var teacherArray=new Array();
		//指导教师
		$.each($('[name=myModalAllTeacherTr]'),function(index,obj){
			var teacher = new Object();
			console.log('obj-',obj);
			console.log($(obj).attr('id'));
			var string='#'+$(obj).attr('id')+' ';
			var str1=string+'[name=teacherName]';
			var str2=string+'[name=teacherSex]';
			var str3=string+'[name=teacherClass]';
			var str4=string+'[name=teacherPosition]';
			var str5=string+'[name=teacherPositionCall]';
			var str6=string+'[name=teacherPhoto]';
			teacher.name=$(str1).val();
			teacher.sex=$(str2).val();
			teacher.SecondaryCollege=$(str3).val();
			teacher.position=$(str4).val();
			teacher.job=$(str5).val();
			teacher.phone=$(str6).val();
			teacherArray.push(teacher);
			
			console.log('指导教师');
			console.log($(str1).val());
			console.log($(str2).val());
			console.log($(str3).val());
			console.log($(str4).val());
			console.log($(str5).val());
			console.log($(str6).val());
		});
		formData.append("teacherArray",JSON.stringify(teacherArray));
		var personalArray = new Array();
		//企业项目员工信息
		$.each($('[name=myModalPersonalTr]'),function(index,obj){
			var person=new Object();
			console.log('obj-',obj);
			console.log($(obj).attr('id'));
			var string='#'+$(obj).attr('id')+' ';
			var str1=string+'[name=personnelType]';
			var str2=string+'[name=personnelName]';
			var str3=string+'[name=personnelSex]';
			var str4=string+'[name=personnelClass]';
			var str5=string+'[name=personnelPosition]';
			var str6=string+'[name=personnelPhoto]';
			
			person.type=$(str1).val();
			person.name=$(str2).val();
			person.sex=$(str3).val();
			person.clazz=$(str4).val();
			person.position=$(str5).val();
			person.phone=$(str6).val();
			personalArray.push(person);
			console.log('企业项目员工信息');
			console.log($(str1).val());
			console.log($(str2).val());
			console.log($(str3).val());
			console.log($(str4).val());
			console.log($(str5).val());
			console.log($(str6).val());
		});
		formData.append("personalArray",JSON.stringify(personalArray));
		var projectSubsidyArray = new Array();
		//资金收入
		$.each($('[name=myModalProjectSubsidyTr]'),function(index,obj){
			var projectSubsidyObj = new Object();
			console.log('obj-',obj);
			console.log($(obj).attr('id'));
			var string='#'+$(obj).attr('id')+' ';
			var str1=string+'[name=projectSubsidyType]';
			var str2=string+'[name=projectSubsidyTime]';
			var str3=string+'[name=projectSubsidyForm]';
			var str4=string+'[name=projectSubsidyMoney]';
			projectSubsidyObj.type=$(str1).val();
			projectSubsidyObj.ttime=$(str2).val();
			projectSubsidyObj.fromm=$(str3).val();
			projectSubsidyObj.money=$(str4).val();
			projectSubsidyArray.push(projectSubsidyObj);
			console.log('资金收入');
			console.log($(str1).val());
			console.log($(str2).val());
			console.log($(str3).val());
			console.log($(str4).val());
		});
		formData.append("projectSubsidyArray",JSON.stringify(projectSubsidyArray));
		var projectAwards=new Array();
		//项目获奖
		$.each($('[name=myModalProjectAwardsTr]'),function(index,obj){
			var projectAwardsObj = new Object();
			console.log('obj-',obj);
			console.log($(obj).attr('id'));
			var string='#'+$(obj).attr('id')+' ';
			var str1=string+'[name=projectAwardsTime]';
			var str2=string+'[name=projectAwardsName]';
			var str3=string+'[name=projectAwardsAwardLevel]';
			var str4=string+'[name=projectAwardsLevel]';
			projectAwardsObj.awardTime=$(str1).val();
			projectAwardsObj.awardName=$(str2).val();
			projectAwardsObj.awardLevel=$(str3).val();
			projectAwardsObj.level=$(str4).val();
			projectAwards.push(projectAwardsObj);
			console.log('项目获奖');
			console.log($(str1).val());
			console.log($(str2).val());
			console.log($(str3).val());
			console.log($(str4).val());
		});
		formData.append("projectAwards",JSON.stringify(projectAwards));
		
		var captionFile = document.getElementById("annex").files;
		for(var i = 0;i<captionFile.length;i++){
			formData.append("annex", captionFile[i]);
		}
		if ($('#managerForm .hide input[name=id]').val()) {
			if(confirm("确认更新吗")){
				updateManager(formData);
			} 
		} else {
			if(confirm("确认增加吗")){
				saveManager(formData);
			}
		}
	});
}

/**
 * 提交数据，并请求数据填充到页面
 * 
 * @param formParams
 */
function saveManager(formParams) {
	$.ajax({
		url : "project/add",
		method : 'post',
		data : formParams,
		dataType : 'json',
		// 告诉jQuery不要去处理发送的数据
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data.status) {
				alert('保存成功');
				requestManager();
			}
		},
		error:function(){
			alert('保存失败，请填写完整信息！');
		}
	});
}

/**
 * 请求管理员数据事件
 */
function requestManager(index,keyword) {
	if(index == null || index == 0){
		index = 1;
	}
	var word = $('#keyword').val();
	if(keyword){
		word = keyword ;
	}
	$.ajax({
		url : 'project/list',
		method : 'get',
		data : {
			pageSize:8,
			pageIndex:index,
			keyword : word
		},
		dataType : 'json',
		success : function(data) {
			fillManagerList(data);
		}
	});
}

function updateManager(formData) {
	var $UserId = $('#managerForm .hide input[name=id]').val();
	$.ajax({
		url : "project/update",
		method : 'post',
		data : formData,
		// 告诉jQuery不要去处理发送的数据,用来序列化data
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data) {
				alert('更新成功');
				requestManager();
			}
		},
		error:function(){
			alert('服务器异常，保存失败');
		}
	});
}

function delManagerFunc() {
	$('#delManager').unbind().click(function () {
		if(isChoosedAny()){
			if (confirm('确认删除吗?')){
				delManagerHandler();
			}
		} else {
			alert("请选择要删除的记录");
		}
	});
}

function delManagerHandler() {
	var $idArr = [];
	
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).attr('no'));
		}
	});
	//console.log($idArr);
	$.ajax({
		url : "project/del",
		method : 'post',
		data : {
			ids : $idArr
		},
		dataType : 'json',
		success : function(data) {
			if (data) {
				alert('删除成功!');
				// 重新加载新闻列表
				requestManager();
			}
		},
		error : function(){
			alert('服务器异常，请联系管理员');
		}
	});
}

/**
 * 填充管理员列表事件
 * 
 * @param ManagerData
 */
function fillManagerList(pageModel) {
	var $managerData = pageModel.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each($managerData,function(index,manager){
		$('.fill tbody').append(fillManagerData(manager));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(pageModel);
	// 分页栏点击事件
	onclickPage(pageModel);
	// 3.清空列表事件
	clearManagerFormFunc();
	// 4.编辑管理员事件
	editManagerForm();
	// 5.复选框事件
	// 5-1.标题复选框事件
	theadChecboxFunc();
	// 5-2.列表复选框事件
	tbodyChecboxFunc();
	//将全选按钮设为非选中状态
	cleanSelectAll();
}

/**
 * 将全选按钮设为非选中状态
 */
function cleanSelectAll(){
	$('#allSelect').prop('checked',false);
}

function editManagerForm() {
	//查看项目详情
	$('[name=lookProject]').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		loadTrManagerForm($(this));
		//展开详细信息栏
		$('#demo').collapse('show');
		$('#shouAndHide').html('折叠');
		$('#shouAndHideIcon').attr('class','glyphicon glyphicon-minus-sign');
		showAndHideFlag = false;
	});
	//打开审批模态框
	$('[name=reviewProject]').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		var id = $(this).attr('thisId');
		$('#modalProjectName').val('');
		$('#modalPrincipalName').val('');
		$('#modalOpinion').val('');
			$.ajax({
				url:"project/lookReviewModal",
				data:{id:id},
				method : 'get',
				dataType:"json",
				success: function(data){
					
					//移除第二个tr以后的tr
					$("#lookReviewTable tr:gt(1)").remove();
					//赋值项目名和负责人姓名
					$('#modalProjectName').val(data.name);
					$('#modalPrincipalName').val(data.principalName);
					console.log('查看审批',data);
					debugger;
					var projectIntoList = data.projectIntoList;
					var string = '';
					var isagree='不同意';
					$.each( projectIntoList, function(i, n){
						debugger;
						var $table = $('#lookReviewTable');
						switch (n.jobId) {
						case 'gw002':
							string = '教师审批';
							break;
						case 'gw003':
							string = '管理员审批';
							break;
						case 'gw004':
							string = '评委审批';
							break;
						default:
							 return ;
							break;
						}
						
						if(1==n.isAgree){
							isagree = '同意';
						}
						  var $tr1 = $('<tr></tr>');
						  var $td1 = $('<td rowspan="2">'+string+'</td>');
						  var $td2 = $('<td>'+isagree+'</td>');
						  var $tr2 = $('<tr></tr>');
						  var $td4 = $('<td><textarea readonly="readonly" style="resize:none;height:60px;" class="form-control">'+n.opinion+'</textarea></td>');
						  var keyword = $('#keyword').val();
						  if(i==projectIntoList.length-1&&isRequestReview(n.jobId,keyword)){
							  console.log('i',i);
							  var $tr3 = $('<tr></tr>');
							  var $td6 = $('<td rowspan="2">'+string+'</td>');
							  var $td7 = $('<td>同意：<input id="" name="isagree" class="btn-primary btn-lg" type="radio" value="1">&emsp;&emsp;不同意：<input id="" name="isagree" class="btn-primary btn-lg" type="radio" value="2"></td>');
							  var $tr4 = $('<tr></tr>');
							  var $td9 = $('<td><textarea  style="resize:none;height:100px;" class="form-control" id="modalOpinion" value=""></textarea></td>');
							  $table.append($tr1.append($td1).append($td2)).append($tr2.append($td4)).append($tr3.append($td6).append($td7)).append($tr4.append($td9));
							  document.getElementById("submitReview").style.display = 'inline';
						  }else{
							  document.getElementById("submitReview").style.display = 'none';
							  $table.append($tr1.append($td1).append($td2)).append($tr2.append($td4));
						  }
						  
					});
				}
			});
			//打开审批模态框
			$('#lookReviewModal').modal('show');
			dealWithReview(id);
			return;
		
	});
}

/**
 * 打开审批模态框（该方法未使用）
 */
function openReviewModal(){
	var id = $(this).attr('thisId');
	$('#modalProjectName').val('');
	$('#modalPrincipalName').val('');
	$('#modalOpinion').val('');
		$.ajax({
			url:"project/lookReviewModal",
			data:{id:id},
			method : 'get',
			dataType:"json",
			success: function(data){
				
				//移除第二个tr以后的tr
				$("#lookReviewTable tr:gt(1)").remove();
				//赋值项目名和负责人姓名
				$('#modalProjectName').val(data.name);
				$('#modalPrincipalName').val(data.principalName);
				console.log('查看审批',data);
				debugger;
				var projectIntoList = data.projectIntoList;
				var string = '';
				var isagree='不同意';
				$.each( projectIntoList, function(i, n){
					debugger;
					var $table = $('#lookReviewTable');
					switch (n.jobId) {
					case 'gw002':
						string = '教师审批';
						break;
					case 'gw003':
						string = '管理员审批';
						break;
					case 'gw004':
						string = '评委审批';
						break;
					default:
						 return ;
						break;
					}
					
					if(1==n.isAgree){
						isagree = '同意';
					}
					  var $tr1 = $('<tr></tr>');
					  var $td1 = $('<td rowspan="2">'+string+'</td>');
					  var $td2 = $('<td>'+isagree+'</td>');
					  var $tr2 = $('<tr></tr>');
					  var $td4 = $('<td><textarea readonly="readonly" style="resize:none;height:60px;" class="form-control">'+n.opinion+'</textarea></td>');
					  var keyword = $('#keyword').val();
					  if(i==projectIntoList.length-1&&isRequestReview(n.jobId,keyword)){
						  console.log('i',i);
						  var $tr3 = $('<tr></tr>');
						  var $td6 = $('<td rowspan="2">'+string+'</td>');
						  var $td7 = $('<td>同意：<input id="" name="isagree" class="btn-primary btn-lg" type="radio" value="1">&emsp;&emsp;不同意：<input id="" name="isagree" class="btn-primary btn-lg" type="radio" value="2"></td>');
						  var $tr4 = $('<tr></tr>');
						  var $td9 = $('<td><textarea  style="resize:none;height:100px;" class="form-control" id="modalOpinion" value=""></textarea></td>');
						  $table.append($tr1.append($td1).append($td2)).append($tr2.append($td4)).append($tr3.append($td6).append($td7)).append($tr4.append($td9));
						  document.getElementById("submitReview").style.display = 'inline';
					  }else{
						  document.getElementById("submitReview").style.display = 'none';
						  $table.append($tr1.append($td1).append($td2)).append($tr2.append($td4));
					  }
					  
				});
			}
		});
		//打开审批模态框
		$('#lookReviewModal').modal('show');
		dealWithReview(id);
		return;
}


/**
 * 当前岗是否需要审批
 * @param preJobId 上一岗jobId
 * @param curJobId 当前岗jobId
 */
function isRequestReview(preJobId,curJobId){
	debugger;
	if(curJobId=='gw001'){
		return false;
	}
	//preJobId => gw001
	//curJobId => gw002
	var lastChat = curJobId.toString().charAt(curJobId.length-1)// 2
	var nowString = parseInt(lastChat)-1;// 1
	var nowString2 = curJobId.substring(0,curJobId.length-1)+nowString;//gw001
	if(preJobId == nowString2){
		return true;
	}
	return false;
}

function dealWithReview(projectid){
	$('#submitReview').unbind().click(function(e){
		
		var isAgree = $("input[name=isagree]:checked").val();
		var modalOpinion = $('#modalOpinion').val();
		var jobId=$('#keyword').val();
		$.ajax({
			url:"project/review",
			data:{projectId:projectid,
				jobId:jobId,
				isAgree:isAgree,
				opinion:modalOpinion
			},
			method : 'post',
			dataType:"json",
			success: function(data){
				alert('提交审核成功！！！');
				$('#lookReviewModal').modal('hide');
			}
		});
	});
}

function loadTrManagerForm(that) {
	var id = that.attr('thisId');
	$.ajax({
		url:"project/findById",
		data:{id:id},
		method : 'get',
		dataType:"json",
		success: function(data){
			$('#managerForm [name = id]').val(data.id);
			$('#managerForm [name = name]').val(data.name);
			$("#managerForm [name=projectSetupTime]").val(dateFormat(new Date(data.projectSetupTime),'yyyy-MM-dd'));
			$("#managerForm [name=projectEnteringTime]").val(dateFormat(new Date(data.projectEnteringTime),'yyyy-MM-dd'));
			$("#managerForm [name=isBusinessRegistration]").val(data.isBusinessRegistration);
			$("#managerForm [name=registeredTime]").val(dateFormat(new Date(data.registeredTime),'yyyy-MM-dd'));
			$("#managerForm [name=registrationCapital]").val(data.registrationCapital);
			$("#managerForm [name=projectIndustry]").val(data.projectIndustry);
			$("#managerForm [name=principalName]").val(data.principalName);
			$("#managerForm [name=principalSex]").val(data.principalSex);
			$("#managerForm [name=principalCorporatePosition]").val(data.principalCorporatePosition);
			$("#managerForm [name=principalPoliticalStatus]").val(data.principalPoliticalStatus);
			$("#managerForm [name=principalSno]").val(data.principalSno);
			$("#managerForm [name=principalSecondaryCollegeId]").val(data.principalSecondaryCollegeId);
			$("#managerForm [name=principalGradeId]").val(data.principalGradeId);
			$("#managerForm [name=principalClass]").val(data.principalClass);
			$("#managerForm [name=principalDormitory]").val(data.principalDormitory);
			$("#managerForm [name=principalPhone]").val(data.principalPhone);
			$("#managerForm [name=principalQq]").val(data.principalQq);
			$("#managerForm [name=principalEmail]").val(data.principalEmail);
			$("#managerForm [name=principalPosition]").val(data.principalPosition);
			$("#managerForm [name=principalCertificate]").val(data.principalCertificate);
			$("#managerForm [name=principalScoreDescription]").val(data.principalScoreDescription);
			$("#managerForm [name=legalName]").val(data.legalName);
			$("#managerForm [name=legalSex]").val(data.legalSex);
			$("#managerForm [name=legalPoliticalStatus]").val(data.legalPoliticalStatus);
			$("#managerForm [name=legalBirthplace]").val(data.legalBirthplace);
			$("#managerForm [name=legalGrade]").val(data.legalGrade);
			$("#managerForm [name=legalClazz]").val(data.legalClazz);
			$("#managerForm [name=legalPosition]").val(data.legalPosition);
			$("#managerForm [name=legalPhone]").val(data.legalPhone);
			$("#managerForm [name=legalEmail]").val(data.legalEmail);
			$("#managerForm [name=projectType]").val(data.projectType);
			$("#managerForm [name=initialFunds]").val(data.initialFunds);
			$("#managerForm [name=workStudyNum]").val(data.workStudyNum);
			$("#managerForm [name=recentGraduatesNum]").val(data.recentGraduatesNum);
			$("#managerForm [name=previousGraduatesNum]").val(data.previousGraduatesNum);
			$("#managerForm [name=totalEmployment]").val(data.totalEmployment);
			$("#managerForm [name=totalPractice]").val(data.totalPractice);
			$("#managerForm [name=yearEmploymentNum]").val(data.yearEmploymentNum);
			$("#managerForm [name=yearPracticeNum]").val(data.yearPracticeNum);
			$("#managerForm [name=isEffective]").val(data.isEffective);
			$("#managerForm [name=isBase]").val(data.isBase);
			$("#managerForm [name=status]").val(data.status);
			$("#managerForm [name=manageContent]").val(data.manageContent);
		}
	});
	$('#downannex').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		location.href = "project/downLoad?id="+id;
	});
}

/**
 * 列表填充
 * 
 * @param Manager
 * @returns
 */
function fillManagerData(manager) {
	
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input no="'+manager.id+'" type="checkbox" /></td>');
	var $td2 = $('<td>' + manager.id + '</td>');
	var $td3 = $('<td>' + manager.name + '</td>');
	var $td7 = $('<td>' + manager.principalName + '</td>');
	var $td8 = $('<td>' + dateFormat(new Date(manager.projectSetupTime),'yyyy-MM-dd') + '</td>');
	var $td9 = $('<td>' + dateFormat(new Date(manager.projectEnteringTime),'yyyy-MM-dd') + '</td>');
	
	var $td4 = $('<td><a name="lookProject" thisId="'+manager.id+'" href="#">查看详情</a></td>');
	
	var $td5 = $('<td class="hide" name = "id">' + manager.id + '</td>');
	var string = '审批'
	if($('#keyword').val()=='gw001'){
		string='查看审批';
	}
	var $td6 = $('<td><a name="reviewProject" thisId="'+manager.id+'" href="#">'+string+'</a></td>');
	return $tr.append($td1).append($td3).append($td7).append($td8).append($td9).append($td4).append($td6).append($td5);
}


/**
 * 日期格式化
 * 
 * @param date
 * @param pattern
 * @returns {String}
 */
function dateFormat(date, pattern) {
	var $dateString;
	var $monthStr;
	if (date.getMonth() + 1 < 10) {
		$monthStr = '0' + (date.getMonth() + 1);
	} else {
		$monthStr = date.getMonth() + 1;
	}
	var $dateStr;
	if (date.getDate() < 10) {
		$dateStr = '0' + date.getDate();
	} else {
		$dateStr = date.getDate();
	}
	var $hour;
	if(pattern == "yyyy-MM-dd HH:mm"){
		$dateString = '' + date.getFullYear() + '-' + $monthStr + '-'
		+ $dateStr + " " + date.getHours() + ":" + date.getMinutes();
	}
	
	if (pattern == "yyyy-MM-dd") {
		$dateString = '' + date.getFullYear() + '-' + $monthStr + '-'
				+ $dateStr;
	} else if (pattern == "yyyyMMdd") {
		$dateString = '' + date.getFullYear() + $monthStr + $dateStr;
	} 
	if (pattern == "yyyy") {
		$dateString = '' + date.getFullYear();
	} else if (pattern == "yyyy") {
		$dateString = '' + date.getFullYear();
	}
	if (pattern == "MM") {
		$dateString = '' + $monthStr;
	} else if (pattern == "MM") {
		$dateString = '' + $monthStr;
	}
	return $dateString;
}
/**
 * 清空表单
 */
function clearManagerFormFunc() {
	$('#managerForm [name = id]').val('');
	$('#managerForm [name = name]').val('');
	$("#managerForm [name=projectSetupTime]").val('');
	$("#managerForm [name=projectEnteringTime]").val('');
	$("#managerForm [name=isBusinessRegistration]").val('');
	$("#managerForm [name=registeredTime]").val('');
	$("#managerForm [name=registrationCapital]").val('');
	$("#managerForm [name=projectIndustry]").val('');
	$("#managerForm [name=principalName]").val('');
	$("#managerForm [name=principalSex]").val(1);
	$("#managerForm [name=principalCorporatePosition]").val('');
	$("#managerForm [name=principalPoliticalStatus]").val('');
	$("#managerForm [name=principalSno]").val('');
	$("#managerForm [name=principalSecondaryCollegeId]").val('');
	$("#managerForm [name=principalGradeId]").val('');
	$("#managerForm [name=principalClass]").val('');
	$("#managerForm [name=principalDormitory]").val('');
	$("#managerForm [name=principalPhone]").val('');
	$("#managerForm [name=principalQq]").val('');
	$("#managerForm [name=principalEmail]").val('');
	$("#managerForm [name=principalPosition]").val('');
	$("#managerForm [name=principalCertificate]").val('');
	$("#managerForm [name=principalScoreDescription]").val('');
	$("#managerForm [name=legalName]").val('');
	$("#managerForm [name=legalSex]").val(1);
	$("#managerForm [name=legalPoliticalStatus]").val('');
	$("#managerForm [name=legalBirthplace]").val('');
	$("#managerForm [name=legalGrade]").val('');
	$("#managerForm [name=legalClazz]").val('');
	$("#managerForm [name=legalPosition]").val('');
	$("#managerForm [name=legalPhone]").val('');
	$("#managerForm [name=legalEmail]").val('');
	$("#managerForm [name=projectType]").val('');
	$("#managerForm [name=initialFunds]").val('');
	$("#managerForm [name=workStudyNum]").val('');
	$("#managerForm [name=recentGraduatesNum]").val('');
	$("#managerForm [name=previousGraduatesNum]").val('');
	$("#managerForm [name=totalEmployment]").val('');
	$("#managerForm [name=totalPractice]").val('');
	$("#managerForm [name=yearEmploymentNum]").val('');
	$("#managerForm [name=yearPracticeNum]").val('');
	$("#managerForm [name=isEffective]").val('');
	$("#managerForm [name=isBase]").val('');
	$("#managerForm [name=status]").val('');
	$("#managerForm [name=manageContent]").val('');
}

/**
 * 关键字查询事件
 * @param index
 */
function managerSeach(index) {
	$('.btn-primary').unbind().click(function () {
		keyword = $('input[name=keyword]').val();
		requestManager(index,keyword);
	});
}

function pageSeach(index) {
	keyword = $('input[name=keyword]').val();
	if(keyword == null){
		keyword = null;
	}
	requestManager(index,keyword);
}

function onclickPage(pageModel) {
	// 当前页
	var $pageIndex = pageModel.pageIndex;
	// 总页数
	var $totalSize = pageModel.totalSize;
	// 上页
	var $preIndex = $pageIndex - 1 > 0 ? ($pageIndex - 1) : 1;
	// 下页
	var $nextIndex = $pageIndex + 1 < $totalSize ? ($pageIndex + 1)
			: $totalSize;
	$('#pagination ul li:eq(0) a').attr("onClick","pageSeach(1)");
	$('#pagination ul li:eq(1) a').attr("onClick","pageSeach(" + $preIndex+")");
	$('#pagination ul li:eq(3) a').attr("onClick","pageSeach(" + $nextIndex+")");
	$('#pagination ul li:eq(4) a').attr("onClick","pageSeach(" + $totalSize+")");
}
