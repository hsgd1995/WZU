package com.hxbd.hxms.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hxbd.clp.dao.AuthorityDao;
import com.hxbd.clp.dao.CaptionDao;
import com.hxbd.clp.dao.NoticeDao;
import com.hxbd.clp.domain.Authority;
import com.hxbd.clp.domain.CourseAndUser;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.Notice;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.AuthorityService;
import com.hxbd.clp.service.UserService;

@ContextConfiguration({"classpath:/WEB-INF/configs/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class PicDaoTest {

	
	
	@Autowired
	private NoticeDao noticeDao;
	

	
	@Autowired
	private CaptionDao captionDao;
	
	@Autowired
	private AuthorityDao authorityDao;
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService authorityService;
	
	
	@Test
	public void test4(){
		
		List<Notice> notice = noticeDao.findNoticeByCourseId(3);
		System.err.println(notice);
		
	}

	
/*	public List<CourseVideoVO> findChildrenList(long parentId,List<CourseVideo> list){
		List<CourseVideo> courseVideoList = list.stream().filter(d->d.getParent()==parentId).collect(Collectors.toList());
		List<CourseVideoVO> volist = new ArrayList<CourseVideoVO>();
		CourseVideoVO vo = new CourseVideoVO();
		for(int i=0;i<courseVideoList.size();i++){
			CourseVideo cvo = courseVideoList.get(i);
			vo.setId(cvo.getId());
			vo.setName(cvo.getName());
			vo.setParent(cvo.getParent());
			vo.setChildrenList(findChildrenList(cvo.getId(), list));
			volist.add(vo);
		} 
		return volist;
	}*/

	
	
	
	
	
	
	
	@Test
	public void test12(){
		List<Integer> list = new ArrayList<>();
		for(int i=0;i<9;i++){
			list.add(i);
		}
		
		for(int index=0;index<list.size();index++){
			if((list.size()%3==0&&(index==0||index%3==0))||((index==0||index%3==0)&&index<list.size()-list.size()%3)){
				System.out.println("<div class=\"d-flex justify-content-between authors\">");
			}
			if((list.size()<3&&index==0)||(list.size()%3!=0&&list.size()%3==(list.size()-index))){
				System.out.println("<div class=\"d-flex justify-content-center authors\">");
			}
			
			System.out.print(list.get(index));
			
			if(list.size()-1==index || (index+1)%3==0){
				System.out.println("</div>");
			}
		}
	}
	
	@Test
	public void testCaptionDao(){
		System.out.println(captionDao.selectByVideoId(12));
		//课程列表：[CourseVideo [id=12, name=java第二讲第二部分, url=http://www.hengmu-edu.com/HXCLVideo/courseVideo/courseVideo184268685687458.mp4, parent=4, courseId=19, remark=, childrenList=[], captionList=[Caption [id=1, captionName=zimu.vtt, videoId=12], Caption [id=9, captionName=zimu.vtt, videoId=12]]], 
		//        CourseVideo [id=11, name=java第二讲第一部分, url=http://www.hengmu-edu.com/HXCLVideo/courseVideo/courseVideo923439141193836.mp4, parent=4, courseId=19, remark=, childrenList=[], captionList=[Caption [id=8, captionName=zimu.vtt, videoId=11]]], 
		//        CourseVideo [id=10, name=java第一讲第二部分, url=http://www.hengmu-edu.com/HXCLVideo/courseVideo/courseVideo184268685687458.mp4, parent=3, courseId=19, remark=, childrenList=[], captionList=[Caption [id=7, captionName=zimu.vtt, videoId=10]]], 
		//        CourseVideo [id=9, name=javal第一讲第一部分, url=http://www.hengmu-edu.com/HXCLVideo/courseVideo/courseVideo923439141193836.mp4, parent=3, courseId=19, remark=, childrenList=[], captionList=[Caption [id=6, captionName=zimu.vtt, videoId=9]]], 
		//        CourseVideo [id=8, name=mysql第二讲第二部分, url=http://www.hengmu-edu.com/HXCLVideo/courseVideo/courseVideo184268685687458.mp4, parent=2, courseId=20, remark=, childrenList=[], captionList=[Caption [id=5, captionName=zimu.vtt, videoId=8]]], 
		//        CourseVideo [id=7, name=mysql第二讲第一部分, url=http://www.hengmu-edu.com/HXCLVideo/courseVideo/courseVideo923439141193836.mp4, parent=2, courseId=20, remark=, childrenList=[], captionList=[Caption [id=4, captionName=zimu.vtt, videoId=7]]], 
		//        CourseVideo [id=6, name=mysql第一讲第二部分, url=http://www.hengmu-edu.com/HXCLVideo/courseVideo/courseVideo184268685687458.mp4, parent=1, courseId=20, remark=null, childrenList=[], captionList=[Caption [id=3, captionName=zimu.vtt, videoId=6]]], 
		//        CourseVideo [id=5, name=mysql第一讲第一部分, url=http://www.hengmu-edu.com/HXCLVideo/courseVideo/courseVideo923439141193836.mp4, parent=1, courseId=20, remark=null, childrenList=[], captionList=[Caption [id=2, captionName=zimu.vtt, videoId=5]]]]

	}
	
	@Test
	public void test14(){
		List<Integer> list = new ArrayList<>();
		for(int i=0;i<100;i++){
			list.add(i);
		}
		System.out.println("<table>");
		for(int i=0;i<list.size();i++){
			if(i==0){
				System.out.println("<tr>");
			}
			
			if(i!=0&&(i==list.size()||i%10==0)){
				System.out.println("</tr>");
			}
			if(i!=0&&i%10==0){
				System.out.println("<tr>");
			}
			
			System.out.print("<td>");
			System.out.print(test15(i));
			System.out.print("</td>");
			
			
			
		}
		System.out.println("</table>");
	}
	
	public String test15(int i){
		return "<"+i+">";
		
	}
	
	@Test
	public void test15(){
		User user = null;
		int no = 0;
		Calendar c = null;
		for(int i=0;i<50;i++){
			user = new User();
			user.setLoginName("userNo"+no);
			user.setUsername("userNo"+no);
			user.setPassword("73b2911a6169c6866638c3b4b8664b5c");
			user.setPhoneNumber("987654321"+no);
			user.setEmail("666666");
			user.setCardId("987654321987654321"+no);
			user.setArea("中国");
			user.setUserPic("http://www.hengmu-edu.com/HXCLImages/userPic/userPic478763234796148.jpg");
			user.setRemark("1");
			c = Calendar.getInstance();
			
			user.setLoginTime(new Date(c.getTimeInMillis()));
			userService.saveUser(user);
			no++;
		}
	}
	
	@Test
	public void test16(){
		List<String> list = new ArrayList<>();
		list.add("1111111111");
		list.add("123456");
		
		userService.batchUpdate(list);
		
	}
	
	@Test
	public void test18(){
		System.out.println(authorityService.getAll());
	}
	
	@Test
	public void testSelectModuleAuthority(){
		for (Authority authority : authorityDao.selectModuleAuthority()) {
			System.out.println(authority);
		}
		
	}
	
	
}
