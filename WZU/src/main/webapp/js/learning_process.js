var start;
$(function(){
	//分页
	 GG = {
       "kk":function(mm){
    	   getLearningCourseListData(mm);
       }
   }
	
	 //加载用户学习课程的列表数据
	 start = 0; //一开始默认加载正在学习的课程list
	 getLearningCourseListData();
	 userId = $('#userId').val();
	 
});

function getLearningCourseListData(currentPage){
	var pageIndex = 1; 
	if(currentPage){
		pageIndex = currentPage;
	}
	
	var keyword = $('input[name=search]').val();
	var result = {};
	$.get("learningCourseList/"+ pageIndex +"/getCoursePageModel",{keyword:keyword,start:start},function(data){
		$('.learning_process-list').html('');
		if(data.pageIndex){
			$("#page").initPage(data.recordCount,data.pageIndex,GG.kk);
		}
		$.each(data.list,function(index,course){
			
			result = getStudyProcess(userId,course.id);
			
			var season;
			var time = dateFormat(new Date(course.studyStartTime), 'MM');
			
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
			if(course.teacher != ""){
				teacherId = course.teacher[0].id;
				teacherPic = course.teacher[0].teacherPic;
				teacherName = course.teacher[0].teacherName;
				position = course.teacher[0].position;
			}
			$(".learning_process-list").append(fillCourseData(course,season,teacherId,teacherPic,teacherName,position,result.process*100,result.cv));
		})
	}) 
} 

//绑定数据
function fillCourseData(course,season,teacherId,teacherPic,teacherName,position,studyProcess,cv){
	var $li = $('<li></li>'); 
	var $div1 = $('<div class="list_content_left"><a href="notice/'+course.id+'"><img src="'+course.coursePic
			+'" /></a></div>');
	var $div2 = $('<div class="list_content_center"><div class="center_title"><a href="notice/'+course.id+'">'+course.courseName
			+' &nbsp;&nbsp;&nbsp;<span>'+ dateFormat(new Date(course.studyStartTime), 'yyyy')+''+season+'</span></a></div><div class="center_people"><div class="people_img"><a href="teacher_details/'+teacherId+'"><img src="'+teacherPic
			+'" /></div><p>'+teacherName
			+'</p></a><div class="clearfix"></div><p>上次学习：<a href="'+cv.href+'">'+cv.name+'</a></p><div class="clearfix"></div></div><div class="center_bottom"><p><i class="icon-calendar"></i>'+course.coursePeriod+'周</p></div></div>');
	var $div3 = $('<div class="list_content_right"><div class="process_img_01"><img src="img/A_icon.jpg" /></div><div class="clearfix"></div><p class="process_p">学习进度（'+parseInt(studyProcess)+'%）</p><div class="progress"><div class="progress-bar" aria-valuemin="0" aria-valuemax="100" style="width: '+studyProcess+'%;"></div></div><div class="process_text"><a href="curriculum/'+course.id+'">课程详情</a></div><div class="clearfix"></div></div>');
	var $div4 = $('<div class="clearfix"></div>');
	return $li.append($div1).append($div2).append($div3).append($div4); 
}

/**
 * 学习进度条
 * @param userId
 * @param courseId
 * @returns {Number}
 */
function getStudyProcess(userId,courseId){
	
	$.ajaxSettings.async = false;
	$.get('learningCourseList/getStudyProcess',{userId:userId,courseId:courseId},function(data){
		if(data){
			result = {};
			result.process = 0;
			result.cv = {};
			result.cv.href = '';
			result.cv.name = '';
			if(data.process){
				result.process = data.process;
			}
			if(data.cv){
				result.cv.href = 'video_details/'+data.cv.id;
				result.cv.name = data.cv.name;
			}else{
				result.cv.href = '#';
				result.cv.name = '尚未开始对该课程的学习！';
			}
				
		}
	},'json');
	$.ajaxSettings.async = true;
	return result;
}

function getListBystudyStatus(st){
	if(start != st){
		if(st == 0){
			$("#end").removeClass('lpt_bg');
			$("#learning").addClass('lpt_bg');
		} else{
			$("#learning").removeClass('lpt_bg');
			$("#end").addClass('lpt_bg');
		}
		start = st;
		getLearningCourseListData();
	}
}

function PersonalSettings(){
	
}
