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
	
	public void test1(){
		/**
		 *               ii.                                         ;9ABH,          
		 *              SA391,                                    .r9GG35&G          
		 *              &#ii13Gh;                               i3X31i;:,rB1         
		 *              iMs,:,i5895,                         .5G91:,:;:s1:8A         
		 *               33::::,,;5G5,                     ,58Si,,:::,sHX;iH1        
		 *                Sr.,:;rs13BBX35hh11511h5Shhh5S3GAXS:.,,::,,1AG3i,GG        
		 *                .G51S511sr;;iiiishS8G89Shsrrsh59S;.,,,,,..5A85Si,h8        
		 *               :SB9s:,............................,,,.,,,SASh53h,1G.       
		 *            .r18S;..,,,,,,,,,,,,,,,,,,,,,,,,,,,,,....,,.1H315199,rX,       
		 *          ;S89s,..,,,,,,,,,,,,,,,,,,,,,,,....,,.......,,,;r1ShS8,;Xi       
		 *        i55s:.........,,,,,,,,,,,,,,,,.,,,......,.....,,....r9&5.:X1       
		 *       59;.....,.     .,,,,,,,,,,,...        .............,..:1;.:&s       
		 *      s8,..;53S5S3s.   .,,,,,,,.,..      i15S5h1:.........,,,..,,:99       
		 *      93.:39s:rSGB@A;  ..,,,,.....    .SG3hhh9G&BGi..,,,,,,,,,,,,.,83      
		 *      G5.G8  9#@@@@@X. .,,,,,,.....  iA9,.S&B###@@Mr...,,,,,,,,..,.;Xh     
		 *      Gs.X8 S@@@@@@@B:..,,,,,,,,,,. rA1 ,A@@@@@@@@@H:........,,,,,,.iX:    
		 *     ;9. ,8A#@@@@@@#5,.,,,,,,,,,... 9A. 8@@@@@@@@@@M;    ....,,,,,,,,S8    
		 *     X3    iS8XAHH8s.,,,,,,,,,,...,..58hH@@@@@@@@@Hs       ...,,,,,,,:Gs   
		 *    r8,        ,,,...,,,,,,,,,,.....  ,h8XABMMHX3r.          .,,,,,,,.rX:  
		 *   :9, .    .:,..,:;;;::,.,,,,,..          .,,.               ..,,,,,,.59  
		 *  .Si      ,:.i8HBMMMMMB&5,....                    .            .,,,,,.sMr
		 *  SS       :: h@@@@@@@@@@#; .                     ...  .         ..,,,,iM5
		 *  91  .    ;:.,1&@@@@@@MXs.                            .          .,,:,:&S
		 *  hS ....  .:;,,,i3MMS1;..,..... .  .     ...                     ..,:,.99
		 *  ,8; ..... .,:,..,8Ms:;,,,...                                     .,::.83
		 *   s&: ....  .sS553B@@HX3s;,.    .,;13h.                            .:::&1
		 *    SXr  .  ...;s3G99XA&X88Shss11155hi.                             ,;:h&,
		 *     iH8:  . ..   ,;iiii;,::,,,,,.                                 .;irHA  
		 *      ,8X5;   .     .......                                       ,;iihS8Gi
		 *         1831,                                                 .,;irrrrrs&@
		 *           ;5A8r.                                            .:;iiiiirrss1H
		 *             :X@H3s.......                                .,:;iii;iiiiirsrh
		 *              r#h:;,...,,.. .,,:;;;;;:::,...              .:;;;;;;iiiirrss1
		 *             ,M8 ..,....,.....,,::::::,,...         .     .,;;;iiiiiirss11h
		 *             8B;.,,,,,,,.,.....          .           ..   .:;;;;iirrsss111h
		 *            i@5,:::,,,,,,,,.... .                   . .:::;;;;;irrrss111111
		 *            9Bi,:,,,,......                        ..r91;;;;;iirrsss1ss1111
		 **/
	}
	
	public void test2(){
		/**                                                                    
		 *            .,,       .,:;;iiiiiiiii;;:,,.     .,,                   
		 *          rGB##HS,.;iirrrrriiiiiiiiiirrrrri;,s&##MAS,                
		 *         r5s;:r3AH5iiiii;;;;;;;;;;;;;;;;iiirXHGSsiih1,               
		 *            .;i;;s91;;;;;;::::::::::::;;;;iS5;;;ii:                  
		 *          :rsriii;;r::::::::::::::::::::::;;,;;iiirsi,               
		 *       .,iri;;::::;;;;;;::,,,,,,,,,,,,,..,,;;;;;;;;iiri,,.           
		 *    ,9BM&,            .,:;;:,,,,,,,,,,,hXA8:            ..,,,.       
		 *   ,;&@@#r:;;;;;::::,,.   ,r,,,,,,,,,,iA@@@s,,:::;;;::,,.   .;.      
		 *    :ih1iii;;;;;::::;;;;;;;:,,,,,,,,,,;i55r;;;;;;;;;iiirrrr,..       
		 *   .ir;;iiiiiiiiii;;;;::::::,,,,,,,:::::,,:;;;iiiiiiiiiiiiri         
		 *   iriiiiiiiiiiiiiiii;;;::::::::::::::::;;;iiiiiiiiiiiiiiiir;        
		 *  ,riii;;;;;;;;;;;;;:::::::::::::::::::::::;;;;;;;;;;;;;;iiir.       
		 *  iri;;;::::,,,,,,,,,,:::::::::::::::::::::::::,::,,::::;;iir:       
		 * .rii;;::::,,,,,,,,,,,,:::::::::::::::::,,,,,,,,,,,,,::::;;iri       
		 * ,rii;;;::,,,,,,,,,,,,,:::::::::::,:::::,,,,,,,,,,,,,:::;;;iir.      
		 * ,rii;;i::,,,,,,,,,,,,,:::::::::::::::::,,,,,,,,,,,,,,::i;;iir.      
		 * ,rii;;r::,,,,,,,,,,,,,:,:::::,:,:::::::,,,,,,,,,,,,,::;r;;iir.      
		 * .rii;;rr,:,,,,,,,,,,,,,,:::::::::::::::,,,,,,,,,,,,,:,si;;iri       
		 *  ;rii;:1i,,,,,,,,,,,,,,,,,,:::::::::,,,,,,,,,,,,,,,:,ss:;iir:       
		 *  .rii;;;5r,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,sh:;;iri        
		 *   ;rii;:;51,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,.:hh:;;iir,        
		 *    irii;::hSr,.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,.,sSs:;;iir:         
		 *     irii;;:iSSs:.,,,,,,,,,,,,,,,,,,,,,,,,,,,..:135;:;;iir:          
		 *      ;rii;;:,r535r:...,,,,,,,,,,,,,,,,,,..,;sS35i,;;iirr:           
		 *       :rrii;;:,;1S3Shs;:,............,:is533Ss:,;;;iiri,            
		 *        .;rrii;;;:,;rhS393S55hh11hh5S3393Shr:,:;;;iirr:              
		 *          .;rriii;;;::,:;is1h555555h1si;:,::;;;iirri:.               
		 *            .:irrrii;;;;;:::,,,,,,,,:::;;;;iiirrr;,                  
		 *               .:irrrriiiiii;;;;;;;;iiiiiirrrr;,.                    
		 *                  .,:;iirrrrrrrrrrrrrrrrri;:.                        
		 *                        ..,:::;;;;:::,,.                             
		 */                                
	}
}
