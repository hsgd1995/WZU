$(function() {
	
	$('#submit').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		var keyword = $('#search').val();
		var courseId = $('#courseId').val();
		if(keyword!=null&&keyword!=undefined){
			window.location.href = "../onlineLearning-search/"+courseId+"?keyword="+keyword;
		}else{
			alert('请输入视频名称...');
			return;
		}
	});
	
	$(".details_right ul li .dr_content_title").click(function(){
	    $(this).next().slideToggle();
	    if($(this).children("h1").css("text-align")=="left"){
	    	$(this).children("h1").css("text-align","center");
	    	$(this).children("i").css("transform","rotate(180deg)");
	    }
	    else{
	    	$(this).children("h1").css("text-align","left");
	    	$(this).children("i").css("transform","rotate(0deg)");
	    }
	});
	
	$("#open_all").click(function(){
		if($("#open_all").text()=="展开全部公告"){
			$(".dr_content").slideDown();
			$("#open_all").text("收起全部公告");
	    	$(".dr_content_title h1").css("text-align","center");
	    	$(".dr_content_title i").css("transform","rotate(180deg)");
	    }
	    else{
	    	$(".dr_content").slideUp();
	    	$("#open_all").text("展开全部公告");
	    	$(".dr_content_title h1").css("text-align","left");
	    	$(".dr_content_title i").css("transform","rotate(0deg)");
	    }
	});	
});