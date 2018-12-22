package com.hxbd.hxms.dao;


import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hxbd.clp.dao.AuthorityDao;
import com.hxbd.clp.dao.RoleAuthorityDao;
import com.hxbd.clp.dao.RoleManagerDao;
import com.hxbd.clp.domain.RoleManager;
import com.hxbd.clp.service.AuthorityService;
import com.hxbd.clp.vo.AuthorityVO;

@ContextConfiguration({"classpath:/WEB-INF/configs/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthorityTest {

	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private AuthorityDao authorityDao;
	
	@Autowired
	private RoleManagerDao roleManagerDao;
	
	@Autowired
	private RoleAuthorityDao roleAuthorityDao;
	
	@Test
	public void testGetAllAuthority(){
		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		int i=0;
		AuthorityVO vo = authorityService.getAllAuthority();
		for (AuthorityVO vo1 : vo.getList()) {
			i++;
			System.out.println("模块"+i+"："+vo1.getId()+"-"+vo1.getName());
			sum1++;
			int j=0;
			for (AuthorityVO vo2 : vo1.getList()) {
				j++;
				System.out.println("	页面"+j+"："+vo2.getId()+"-"+vo2.getName());
				sum2++;
				int k=0;
				for (AuthorityVO vo3 : vo2.getList()) {
					k++;
					System.out.println("		功能"+k+"："+vo3.getId()+"-"+vo3.getName());
					sum3++;
					
				}
			}
		}
		System.out.println("总数："+(sum1+sum2+sum3));
	}
	
	@Test
	public void testSelectByParentId(){
		System.out.println(authorityDao.selectByParentId("exam_manage"));
	}
	
	@Test
	public void testMap(){
		Map<String, String> map = new HashMap<>();
		map.put("news_manage", "news_manage");
		System.out.println(map.get("news_manage"));
	}
	
	@Test
	public void testSelectByManagerId(){
		RoleManager rm = roleManagerDao.selectByManagerId(2);
		System.out.println(rm);
	}
	
	@Test
	public void test22(){
		System.out.println(authorityDao.selectById("role_authority_manage"));
		
		System.out.println(roleAuthorityDao.selectByRoleId(15));
	}
	
}
