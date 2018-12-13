$(function() {	
	$('input[name="check-box"]').wrap('<div class="check-box"><i></i></div>');
	$.fn.toggleCheckbox = function () {
	    this.attr('checked', !this.attr('checked'));
	}
	$('.check-box').on('click', function () {
	    $(this).find(':checkbox').toggleCheckbox();
	    $(this).toggleClass('checkedBox');
	});
	
	//分页
	 GG = {
			 "kk":function(mm){
				 //console.log(mm);
				 getCourseData(mm);
			 }
	 }
	
	var pageIndex = 1;
	 //加载课程数据
	 getCourseData(1);
	 
	 
	 
	 
	 //加载课程类型数据
	 getCourseTypeData();
	 
	 //加载课程分类后的数量
	 getCourseSort();
	 
	 $(":radio").click(function(){
		  getCourseData();
		  });
	 //字幕复选框事件
	 $(".checkbox_course").click(function(){
		 getCourseData();
	 });
	 
});

//加载课程数据
function getCourseData(currentPage){
	if(currentPage){
		//console.log("修改pageIndex:",currentPage);
		pageIndex = currentPage;
	}
	
	 var keyword = $("input[name = search]").val();
	 var orfree = $("input[type='radio']:checked").val();
	 var courseTypeId = $("input[name='courseTypeSelect']:checked").val();
	 if(courseTypeId<=0){
		 courseTypeId =null;
	 }
	 var language = new Array();
	 
	 
	 $(":checkbox:checked").each(function(i){
		 language.push($(this).val());
	 });
	 
	 $.ajax({
		 type:"get",
		 url:"course/"+ pageIndex +"/getCoursePageModel",
		 data:{language:language,keyword:keyword,orfree:orfree,courseTypeId:courseTypeId},
		 dataType:"json",
		 success:function(data){
			 
			 $('#courseUl').html('');
			 if(data.pageIndex){
				 pageIndex = data.pageIndex;
			 }
			 $("#page").initPage(data.recordCount,pageIndex,GG.kk);
			 
				$.each(data.list,function(index,course){
					/*var et = course.courseDetails.expendTime.slice(-1);
					var cp = course.courseDetails.coursePeriod;*/
					var et = course.expendTime.slice(-1);
					var cp = course.coursePeriod;
					var season;
					var time = dateFormat(new Date(course.studyStartTime), 'MM');
					var sum = et*cp;
					
					//教师
					var teacherId = 0;
					var teacherPic = '未定义';
					var teacherName = '未定义';
					var position = '未定义';
					
					if(time>=1 && time<=3){	//这里修改课程页面的春夏秋冬季节
						season = '春';
					}else if(time<=6){
						season = '夏';
					}else if(time<=9){
						season = '秋';
					}else if(time<=12){
						season = '冬';
					};
					sum_left = sum.toString().slice(0,1);
					sum_right = sum.toString().slice(-1);
		/*			console.log(time+'+++'+season);
					console.log("左边："+sum_left); 
					console.log("右边:"+sum_right); */
					if(course.teacher != ""){
						teacherId = course.teacher[0].id;
						teacherPic = course.teacher[0].teacherPic;
						teacherName = course.teacher[0].teacherName;
						position = course.teacher[0].position;
					}
					$("#courseUl").append(fillCourseData(course,sum,season,teacherId,teacherPic,teacherName,position));
				})
		 }
	 });
	 
} 

//绑定数据
function fillCourseData(course,sum,season,teacherId,teacherPic,teacherName,position){
	var $li = $('<li></li>'); 
	var $div1 = $('<div class="list_content_left"><a href="curriculum/'
			+ course.id +'"><img src="'+ course.coursePic +'" /></a></div>');
	var $div2 = $('<div class="list_content_center"><div class="center_title"><a href="curriculum/'
			+ course.id +'">'
			+ course.courseName +' &nbsp;&nbsp;&nbsp;<span>' 
			+ dateFormat(new Date(course.studyStartTime), 'yyyy')
			+''+season+'</span></a></div><div class="center_time"><span>共</span>'+spanOfClassTime(sum)+'<span>课时，</span><span>学习时间</span><span class="time_bg">'+course.coursePeriod+'</span><span>周。</span><div class="clearfix"></div></div><div class="center_bottom"><p><i class="icon-calendar"></i>'
			+ dateFormat(new Date(course.studyStartTime), 'yyyy-MM-dd')
			+'开课</p></div></div>');
	var $div3 = $('<div class="list_content_right"><a target="_blank" href="teacher_details/'
			+ teacherId +'"><div class="right_img"><img src="'+ teacherPic +'" /></div><p>'
			+ teacherName +'</p><p>'
			+ position +'</p></a></div>');
	var $div4 = $('<div class="clearfix"></div>');
	return $li.append($div1).append($div2).append($div3).append($div4); 
}

/**
 * 计算课时，并返回span标签
 * @param time
 */
function spanOfClassTime(time){
	var span = '';
	var i = 0;
	time = time.toString();
	while(time.length>i){
		span = span + '<span class="time_bg">'+time.substring(i,i+1)+'</span>';
		i++;
	}
	return span;
}

//加载课程类型数据
function getCourseTypeData(){
	$.get("course/getCourseType",function(data){
		$('#courseTypeUl').html('');
		var courseTypeUl = $("#courseTypeUl");
		courseTypeUl = courseTypeUl.append($('<li><div style="margin-top: 14px;" class="radio_course courseTypeSelect"><input type="radio" name="courseTypeSelect" value="-1"/></div><p>全部</p><span>..</span></li>'));
		$.each(data,function(index,courseType){
			
			courseTypeUl.append(fillCourseTypeData(courseType,index));
		})
		aaa();
	}) 
} 

//绑定数据
function fillCourseTypeData(courseType,index){
	var $li = $('<li></li>'); 
	var $div1;
	/*if(index==0){
		$div1 = $('<div class="radio_course courseTypeSelect"><input type="radio" checked name="courseTypeSelect" value="'+ courseType.id +'"/></div><p>'+courseType.typeName+'</p><span>..</span>');
	}else{
		$div1 = $('<div class="radio_course courseTypeSelect"><input type="radio" name="courseTypeSelect" value="'+ courseType.id +'"/></div><p>'+courseType.typeName+'</p><span>..</span>');
	}*/
	
	$div1 = $('<div style="margin-top: 14px;" class="radio_course courseTypeSelect"><input type="radio" name="courseTypeSelect" value="'+ courseType.id +'"/></div><p>'+courseType.typeName+'</p><span>..</span>');

	return $li.append($div1); 
}

//获取分类的课程的数量显示在页面上
function getCourseSort(){
	$.get("course/getCourseSort",function(data){
		$(".count0").text(data.count0);
		$(".count1").text(data.count1);
		$(".count2").text(data.count2);
	}) 
	
}

 
/*//课程分类的选择事件     checkbox
function aaa(){
	 $(".courseTypeSelect :checkbox").click(function(){
		 var ids = [];
		 $("input[name='courseTypeSelect']:checked").each(function(i) {
		        if(i==0) {
		            ids = $(this).val();
		        } else {
		            ids += ("," + $(this).val());
		        }
		    });
		 getCourseData(ids);
		 console.log(ids);
		    alert(ids);    
		  alert($("input[name='courseTypeSelect']:checked").val());
		  });
}*/


//课程分类的选择事件     radio
function aaa(){
	 $(".courseTypeSelect :radio").click(function(){
		 //var courseTypeId = $("input[name='courseTypeSelect']:checked").val();
		 getCourseData();
		  });
}