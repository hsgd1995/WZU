$(function() {
	$(".details_right ul li .dr_content_title").click(function(){
	    $(this).next().slideToggle();
	    if($(this).children("h1").css("margin-right")=="1px"){
	    	$(this).children("h1").css("margin-right","0px");
	    	$(this).children("i").css("transform","rotate(0deg)");
	    }
	    else{
	    	$(this).children("h1").css("margin-right","1px");
	    	$(this).children("i").css("transform","rotate(180deg)");
	    }
	});
	
	$(".dr_content ul li").hover(function (){
            $(this).find(".show_eye").css("display","none");
            $(this).find(".show_text").css("display","block");  
        },function (){  
            $(this).find(".show_eye").css("display","inline-block");
            $(this).find(".show_text").css("display","none");  
        }); 
});

function test1(courseId){
}