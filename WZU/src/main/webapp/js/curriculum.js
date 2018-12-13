$(function() {
	
	peopleCount();//学习人数	
	showCourseStatus(); //显示加入课程状态
	showIcon();//显示收藏图标	
	
	
	//点击加入课程
	$(".join").click(function(){
		var $id = $(".courseId").attr("value");
		$.get("../joinCourse/"+$id, function(data){
			  if(data.status){
				  window.location.href="../notice/"+$id;
			  }else{
				  alert("请先登录");
			  }
			});
	});
	
	//点击收藏课程
	$(".collect").click(function(){
		var $id = $(".courseId").attr("value");
		$.get("../collectCourse/"+$id, function(data){
			  if(data.status){
				  $(".collect").removeClass("icon-heart-empty");   
			  }else{
				  alert("请先登录再收藏");
			  }
			});
	});

});

//学习人数
function peopleCount(){
	var $id = $(".courseId").attr("value"); 
	$.get("../alreadyStudy/"+$id,function(count){
		//console.log("学习人数:"+count);
		$(".count").html('');
		
		$(".count").html(count+"人已学习");
	})
	
}

//显示收藏图标状态
function showIcon(){
	var $id = $(".courseId").attr("value");
	$.get("../collectStatus/"+$id,function(data){
		  if(data.status){
			  $(".collect").addClass("icon-heart");  
			  $(".collect").removeClass("icon-heart-empty");
		  }
	})
	
}

//更改收藏图标状态
function changeIconStatus(){
	var $id = $(".courseId").attr("value");
	$.get("../changeCollectStatus/"+$id,function(data){
		  if(data.status == 0){
			  $(".collect").addClass("icon-heart-empty");
			  $(".collect").removeClass("icon-heart");
		  }
		  if(data.status == 1){
			  $(".collect").addClass("icon-heart");
			  $(".collect").removeClass("icon-heart-empty");
		  }
	})
	
}

//显示加入课程状态
function showCourseStatus(){
	//console.log("显示加入课程状态");
	$(".join").html('');
	var $id = $(".courseId").attr("value"); 
	$.get("../joinCourseStatus/"+$id, function(data){
		  if(data.status){
			  $(".join").html("进入学习");
		  }else{
			  $(".join").html("加入课程");
		  }
		});
}