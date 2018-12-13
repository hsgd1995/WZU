$(function(){
	//分页
	 GG = {
       "kk":function(mm){
    	   getCollectCourseListData(mm);
       }
   }
	
	 //加载用户收藏课程的列表数据
	 getCollectCourseListData();
});

function getCollectCourseListData(currentPage){
	var pageIndex = 1; 
	if(currentPage){
		pageIndex = currentPage;
	}
	var keyword = $('input[name=search]').val();
	//console.log('收藏keyword:',keyword);
	$.get("collectCourseList/"+ pageIndex +"/getCoursePageModel",{keyword:keyword},function(data){
		if(data.pageIndex){
			$("#page").initPage(data.recordCount,data.pageIndex,GG.kk);
		}
		$('.collection_course_list').html('');
		//console.log(data.list);
		$.each(data.list,function(index,course){
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
			$(".collection_course_list").append(fillCollectCourseData(course,season,teacherId,teacherPic,teacherName,position));
		})
	}) 
} 


//绑定收藏的课程数据
function fillCollectCourseData(course,season,teacherId,teacherPic,teacherName,position){ 
	var $li = $('<li></li>'); 
	var $a = $('<a href="#" class="exit_img" onclick="deleteCollectCourse('+course.id+')"></a>');
	var $div1 = $('<div class="list_content_left"><a href="notice/'+
			course.id+'"><img src="'+ 
			course.coursePic+'" /></a></div>');
	var $div2 = $('<div class="list_content_center"><div class="center_title"><a href="notice/'+course.id+'">'+
			course.courseName+' &nbsp;&nbsp;&nbsp;<span>'+ 
			dateFormat(new Date(course.studyStartTime), 'yyyy')+''+
			season+'</span></a></div><div class="center_people"><div class="people_img"><a target="_blank" href="teacher_details/'+
			teacherId+'"><img src="'+
			teacherPic+
			'" /></div><p>'+
			teacherName+'</p></a><div class="clearfix"></div></div><div class="center_bottom"><p><i class="icon-calendar"></i>'+dateFormat(new Date(course.studyStartTime), 'yyyy-MM-dd')
			+'</p><p><i class="icon-time"></i>'+course.coursePeriod+'周</p><p><i class="icon-ok-circle"></i>'+course.expendTime+'小时/周</p></div></div>');
	var $div3 = $('<div class="list_content_right"><div class="start_time"><p><span class="time_bg">7</span><span class="time_bg">7</span>天<span class="time_bg">1</span><span class="time_bg">7</span>时</p></div><div class="clearfix"></div></div>');
	var $div4 = $('<div class="clearfix"></div>');
	return $li.append($a).append($div1).append($div2).append($div3).append($div4); 
}

//删除收藏的课程 
function deleteCollectCourse(id){
	confirm
	$.get("deleteCollectCourse/"+id, function(data){
		if(confirm("确定删除吗？")){
		  if(data.status){
			  alert("删除成功");
			  location.reload();
		  }else{
			  alert("系统出错");
		  }
		}
	});
}
