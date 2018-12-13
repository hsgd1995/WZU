$(function() {

    //点击更换样式 
    $(".dl_content_top").click(function(){
         $(".dl_content_top").removeClass("bg_red");   
         $(this).addClass("bg_red");
    });
});
aboutUs();

//关于我们
function aboutUs(){
		$("title").html("关于我们");
		$('.catalog_content').html('');
		var $h1 = $('<h1><i class="icon-exclamation-sign"></i>&nbsp;&nbsp;关于我们</h1>');
		var $div1 = $('<div class="catalog_bottom"></div>');
		var $p1 = $('<p>汉语言学习平台</p>');
		var $p2 = $('<p>建设目标：建设中国高水平大规模在线教育平台；实现中国高水平大学之间的教学资源共享及学分互认； 向中国其它大学提供优质课程，提高中国高等教育质量； 向社会公众提供在线课程教学服务，提升公民的科学素养和文化素养； 向全球华人和相关需求者开放，传播与弘扬优秀中华文化。</p></div>');
		var $h2 = $div1.append($p1).append($p2);
		$('.catalog_content').append($h1).append($h2);
} 

//联系我们
function contactUs(){
		$("title").html("联系我们");
		$('.catalog_content').html('');
		var $h1 = $('<h1><i class="icon-exclamation-sign"></i>&nbsp;&nbsp;联系我们</h1>');
		var $div1 = $('<div class="catalog_bottom"></div>');
		var $p1 = $('<p>公司地址（总部）：南宁市高新区科园大道31号财智时代13层B座</p>');
		var $p2 = $('<p>咨询热线：400-0771912、0771-3218199</p>');
		var $p3 = $('<p>官方微博：http://weibo.com/bodawenhua</p>');
		var $p4 = $('<p>官方微信：hxbdjy、bodawx</p>');
		var $h2 = $div1.append($p1).append($p2).append($p3).append($p4);
		$('.catalog_content').append($h1).append($h2);
} 

//服务条款 Terms of Service
function TermsOfService(){
		$("title").html("服务条款");
		$('.catalog_content').html('');
		var $h1 = $('<h1><i class="icon-exclamation-sign"></i>&nbsp;&nbsp;服务条款</h1>');
		var $div1 = $('<div class="catalog_bottom"></div>');
		var $p1 = $('<p>1、 总则：您通过用户注册页面进入本网站即表示您已经同意自己与本网订立本协议。本网站可随时执行全权决定更改“条款”。如“条款”有任何变更，本网将在本网站上刊载公告作为通知。经修订的“条款”一经在本网站上公布后，立即自动生效。</p>');
		var $p2 = $('<p>2、 关于版权及言论问题：用户在使用本站系统过程中，不得发布侵犯党和国家利益的言论，不得发布泄露国家机密的言论，不得发布有损害他们声誉的言论，不得发布侵犯他人版权的言论等。用户在论坛、留言板以及个人网站上所发布、转载的文章所引起的版权问题以及因文章内包含毁谤、诋毁、攻击他人的信息等一切纠纷或后果由用户自行承担，本网概不负责。</p>'); 
		var $p3 = $('<p>本网站尊重他人的任何权利，同时也要求我们的使用者也尊重他人之权利。本网站在适当情况下，有权自行决定删除侵犯他人权利的文章终止侵害或违反他人权利之使用者的帐号。</p>');
		var $p4 = $('<p>3、 关于经营活动：本网支持用户在使用站系统过程中，进行合法的经营，但因为用户过失所造成的后果由用户自行承担，一切未经过本网官方认可的经营活动均与本官方站点无关。</p>');
		var $p5 = $('<p>4、 关于用户资料：“您的资料”包括您在注册、使用本站服务过程中、在任何公开信息场合或通过任何电子邮件形式，向本站或其他用户提供的任何资料。您应对“您的资料”负全部责任，而本站仅作为您在网上发布和刊登"您的资料"的被动渠道。</p>');
		var $p6 = $('<p>如果您在本网站注册，您同意：</p>');
		var $p7 = $('<p>根据本网站所刊载的会员资料表格的要求，提供关于您或贵公司的真实、准确、完整和反映当前情况的资料；</p>');
		var $p8 = $('<p>本网站维持并及时更新会员资料，使其保持真实、准确、完整和反映当前情况。倘若您提供任何不真实、不准确、不完整或不能反映当前情况的资料，或本网有合理理由怀疑该资料不真实、不准确、不完整或不能反映当前情况，本网站有权暂停或终止您在本网站的注册身份及资料。</p>');
		var $p9 = $('<p>5、 关于帐号的安全性：在登记过程中，您将选择会员注册名和密码。您须自行负责对您的会员注册名和密码保密，且须对您在会员注册名和密码下发生的所有活动承担责任。</p>');
		var $p10 = $('<p>6、 系统的版权：本网对所有开发的或合作开发的服务的知识产权拥有所有权或使用权，这些服务受到适用的知识产权、版权、商标、服务商标、专利或其他法律的保护。</p>');
		var $p11 = $('<p>7、 用户利益保障：本站将定期对用户数据进行备份工作，如因为操作失误、黑客攻击以及服务器故障引起的数据损失，本网将尽可能地恢复，但这种情况下所导致的损失为不可抗拒因素，本站具有免责权利。但用户有权要求本网根据服务停止时间采取延期使用的补救措施。但如果因为本站经营不善而导致本站服务无法继续，用户有权要求本网将用户数据返还用户或者寻找其他途径以妥善解决，以保障用户合法权利。</p>');
		var $h2 = $div1.append($p1).append($p2).append($p3).append($p4).append($p5).append($p6).append($p7).append($p8).append($p9).append($p10).append($p11);
		$('.catalog_content').append($h1).append($h2);
} 