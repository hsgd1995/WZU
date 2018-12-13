$(function() {
	var pwidth = $(window).width();
	var pheight = $(window).height();
	var myheight = $('#MyNewsBox').height();
	var lwidth = (pwidth-500)/2+'px';
	var lheight = (pheight-myheight)/2+'px';
	var oDiv=document.getElementById('MyNewsBox');
	var lDiv=document.getElementById('NewsBoxTitle');
	var disX=0;
	var disY=0;
	lDiv.onmousedown=function(ev) 
	{
	    var oEvent=ev||event;
	    disX=oEvent.clientX-oDiv.offsetLeft; 
	    disY=oEvent.clientY-oDiv.offsetTop; 
	    document.onmousemove=function(ev) 
	    {
	        var oEvent=ev||event;
	        var oLeft=oEvent.clientX-disX; 
	        var oTop=oEvent.clientY-disY; 
	        if(oLeft<0) 
	        {
	          oLeft=0; 
	        }
	        else if(oLeft>document.documentElement.clientWidth-oDiv.offsetWidth) 
	        {
	          oLeft=document.documentElement.clientWidth-oDiv.offsetWidth; 
	        }
	        if(oTop<0) 
	        {
	          oTop=0; 
	        }
	        else if(oTop>document.documentElement.clientHeight-oDiv.offsetHeight) 
	        {
	          oTop=document.documentElement.clientHeight-oDiv.offsetHeight; 
	        }
	        oDiv.style.left=oLeft+'px'; 
	        oDiv.style.top=oTop+'px'; 
	    };
	    document.onmouseup=function() 
	    {
	        document.onmousemove=null; 
	        document.onmouseup=null; 
	    };
	    return false; 
	};


	$("#MyNewsBox").css({"top":lheight,"left":lwidth});
	
	$(".news_text_right").click(function(a){
		$(".mask_bg").fadeIn();
		$("#MyNewsBox").fadeIn();
		a.stopPropagation();
	});
	
	$('body').click(function(e) {
	    if($(e.target).closest("#MyNewsBox").length == 0) {
        	$(".mask_bg").fadeOut();
            $('#MyNewsBox').fadeOut();
        }
	})
	
	$(".exit_img").click(function(){
		$(".mask_bg").fadeOut();
		$("#MyNewsBox").fadeOut();
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
});