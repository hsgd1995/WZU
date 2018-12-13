$(function() {
	//banner图切换
	$("#circleContent").carousel({
		interval: 3000
	}); //每隔3秒自动轮播 
	
	$("#myCarousel").carousel({
		interval: 5000
	}); //每隔3秒自动轮播 
	
	//超出部分显示省略号
    $(".broadcast_text p").each(function() {
		var maxwidth = 260;
		if($(this).text().length > maxwidth) {
			$(this).text($(this).text().substring(0, maxwidth));
			$(this).html($(this).html() +'...');
		}
	});
	
	//底部无缝滚动
	var id = function(el) {
		return document.getElementById(el);
	},
	c = id('photo-list');
	if(c) {
		var ul = id('scroll'),
			lis = ul.getElementsByTagName('li'),
			itemCount = lis.length,
//  		width = lis[0].offsetWidth + 5, //获得每个img容器的宽度
			width = 300 + 5, //获得每个img容器的宽度
			marquee = function() {
				c.scrollLeft += 2;
				if(c.scrollLeft % width <= 1) { //当 c.scrollLeft 和 width 相等时，把第一个img追加到最后面
					ul.appendChild(ul.getElementsByTagName('li')[0]);
					c.scrollLeft = 0;
				};
			},
			speed = 50; //数值越大越慢
		ul.style.width = width * itemCount + 'px'; //加载完后设置容器长度        
		var timer = setInterval(marquee, speed);
		c.onmouseover = function() {
			clearInterval(timer);
		};
		c.onmouseout = function() {
			timer = setInterval(marquee, speed);
		};
	};
	
	$("#ShowArrowDown_01").click(function(){
        $('body,html').animate({
            scrollTop: $('#ArrowDown_01').offset().top
        }, 1000);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
    });
    
    $("#ShowArrowDown_02").click(function(){
        $('body,html').animate({
            scrollTop: $('#ArrowDown_02').offset().top
        }, 1000);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
    });

    $(".appointment").click(function(){
    	$.ajax({
    		url : "live/appointment",
    		method : 'get',
    		success : function(data) {
    			if (data.status == 200) {
    				alert(data.message);
/*                    $.Toast("test", "this is a test message.", "success", {
                        has_icon:true,
                        has_close_btn:true,
                        fullscreen:false,
                        timeout:0,
                        sticky:false,
                        has_progress:true,
                        rtl:false,
                    });*/
    			}
    			if(data.status == 300){
    				alert(data.message);
    			}
    			if(data.status == 400){
    				alert(data.message);
/*                    $.Toast("提示", "请先登录！", "success", {
                        has_icon:true,
                        has_close_btn:true,
                        fullscreen:false,
                        timeout:0,
                        sticky:false,
                        has_progress:true,
                        rtl:false,
                    });*/
    			}
    		}
    	});
    });
    getCourseLimitEight();
    getTeacherLimitfour();
});
function getCourseLimitEight(){
	$.get("findCourseLimitEight",function(data){
		$('.getCourseLimitfour').html('');
		$('.two_top').html('');
		$.each(data.slice(0,4),function(index,course){
			//教师
			var teacherId = 0;
			var teacherPic = '未定义';
			var teacherName = '未定义';
			var position = '未定义';
			if(course.teacher != ""){
				teacherId = course.teacher[0].id;
				teacherPic = course.teacher[0].teacherPic;
				teacherName = course.teacher[0].teacherName;
				position = course.teacher[0].position;
			}
			$(".getCourseLimitfour").append(fillCourseData(course,teacherId,teacherPic,teacherName,position));
	})
		$.each(data.slice(4,8),function(index,course){
			//教师
			var teacherId = 0;
			var teacherPic = '未定义';
			var teacherName = '未定义';
			var position = '未定义';
			if(course.teacher != ""){
				teacherId = course.teacher[0].id;
				teacherPic = course.teacher[0].teacherPic;
				teacherName = course.teacher[0].teacherName;
				position = course.teacher[0].position;
			}
			$(".two_top").append(fillCourseData(course,teacherId,teacherPic,teacherName,position));
	})
	$('.getCourseLimitfour').append('<div class="clearfix"></div>');
	$('.two_top').append('<div class="clearfix"></div>');
	})
}

function getTeacherLimitfour(){
	$.get("findTeacherLimitfour",function(data){
		//console.log(data[0]);
		$('.getTeacherLimitfour').html('');
		$.each(data,function(index,teacher){
			$(".getTeacherLimitfour").append(fillTeacherData(teacher));
	})
	})
}

//绑定主页课程数据
function fillCourseData(course,teacherId,teacherPic,teacherName,position){
	var $li = $("<li></li>");
	var $div1 = $('<div class="curriculum_img"><a href="curriculum/'+course.id+'"><img src="'+course.coursePic+'" /></a></div>');
	var $p = $('<p>'+course.courseName+'&nbsp;2018春</p>');
	var $div2 = $('<div class="school"><a target="_blank" class="school_left" href="teacher_details/'+teacherId+'"><span><img src="'+teacherPic +'" /></span>&nbsp;'+teacherName+'</a><a class="school_right">'+position+'</a><div class="clearfix"></div></div>');
	return $li.append($div1).append($p).append($div2);
	
}

//绑定主页教师数据 
function fillTeacherData(teacher){
	var $li = $('<li class="teacher_img_left"></li>');
	var $div1 = $('<div class="box"><img style="height:190px;width:290px;" src="'+teacher.teacherPic+'" /><div class="box-content"><h3 class="title">'+teacher.teacherName+'</h3></div></div>');
	return $li.append($div1);
	
}