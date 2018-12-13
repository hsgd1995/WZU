$(function() {
	
	 $(document).ready(function () {
            $('.dropdown-toggle').dropdown();
        });
	
//超出部分显示省略号
    $(".forum_right table tbody tr td p,.forum_right tbody a").each(function() {
		var maxwidth = 25;
		if($(this).text().length > maxwidth) {
			$(this).text($(this).text().substring(0, maxwidth));
			$(this).html($(this).html() +'...');
		}
	});
	
	$(".time_sort").click(function(){
	    if($(this).children("i").attr("class")=="icon-caret-down"){
	    	$(this).children("i").attr("class","icon-caret-up");
	    }
	    else{
	    	$(this).children("i").attr("class","icon-caret-down");
	    }
	});
	
	$(".dropdown_wrap li").click(function(){
		var lic = $(this).text();
		$(this).addClass("bg_red").siblings().removeClass("bg_red");
		$(this).parent().parent().prev().children("span").text(lic);
	});
	
	
	//分页
	 var GG = {
        "kk":function(mm){
        }
    }
    $("#page").initPage(35,1,GG.kk);
    
    $("#NewDiscussion").click(function(){
        $('body,html').animate({
            scrollTop: $('#ShowNewDiscussion').offset().top
        }, 1000);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
    });
});