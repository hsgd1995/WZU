package com.hxbd.clp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.AuthorityDao;
import com.hxbd.clp.domain.Authority;
import com.hxbd.clp.service.AuthorityService;
import com.hxbd.clp.vo.AuthorityVO;

/**
 * 权限业务实现类
 * @author Administrator
 *
 */
@Service("authorityService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class AuthorityServiceImpl implements AuthorityService{

	@Autowired
	private AuthorityDao authorityDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Authority> getAll() {
		return authorityDao.selectAll();
	}
	
	@Transactional(readOnly = true)
	@Override
	public AuthorityVO getAllAuthority(){
		//此处代码待优化，可考虑递归
		AuthorityVO vo = new AuthorityVO();
		vo.setId("zero");
		vo.setName("权限列表");
		vo.setParentId("0");
		List<AuthorityVO> voList = new ArrayList<>();
		
		//模块权限
		List<Authority> firstAuthority =authorityDao.selectModuleAuthority();
		AuthorityVO vo1 = null;
		List<AuthorityVO> voList1 = null;
		//页面权限
		List<Authority> secondAuthority = null;
		AuthorityVO vo2 = null;
		List<AuthorityVO> voList2 = null;
		//功能权限
		List<Authority> thirdAuthority = null;
		AuthorityVO vo3 = null;
		
		
		if(firstAuthority!=null){
			for (Authority authority : firstAuthority) {
				vo1 = new AuthorityVO();
				vo1.setId(authority.getId());
				vo1.setName(authority.getName());
				vo1.setState(authority.getState());
				vo1.setParentId(authority.getParentId());
				secondAuthority = authorityDao.selectByParentId(authority.getId());
				if(secondAuthority!=null){
					voList1 = new ArrayList<>();
					for (Authority second : secondAuthority) {
						vo2 = new AuthorityVO();
						vo2.setId(second.getId());
						vo2.setName(second.getName());
						vo2.setState(second.getState());
						vo2.setParentId(second.getParentId());
						thirdAuthority = authorityDao.selectByParentId(second.getId());
						voList2 = new ArrayList<>();
						if (thirdAuthority != null) {
							for (Authority third : thirdAuthority) {
								vo3 = new AuthorityVO();
								vo3.setId(third.getId());
								vo3.setName(third.getName());
								vo3.setState(third.getState());
								vo3.setParentId(third.getParentId());
								voList2.add(vo3);
							}
							vo2.setList(voList2);
						}
						voList1.add(vo2);
					}
					vo1.setList(voList1);
				}
				voList.add(vo1);
			}
			vo.setList(voList);
		}
	
		return vo;
	}

}
