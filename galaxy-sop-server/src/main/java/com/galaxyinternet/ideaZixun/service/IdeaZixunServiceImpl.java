package com.galaxyinternet.ideaZixun.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.IdeaZixunBo;
import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.dao.idea.IdeaZixunDao;
import com.galaxyinternet.dao.idea.ZixunFinanceDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.idea.IdeaZixun;
import com.galaxyinternet.model.idea.ZixunFinance;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.IdeaZixunService;
import com.galaxyinternet.service.SopTaskService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;


@Service("com.galaxyinternet.service.IdeaZixunService")
public class IdeaZixunServiceImpl extends BaseServiceImpl<IdeaZixun> implements IdeaZixunService{

	@Autowired
	private IdeaZixunDao ideaZixunDao;
	@Autowired
	private ZixunFinanceDao zixunFinanceDao;
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private DepartmentService departmentService;
	
	
	
	@Override
	protected BaseDao<IdeaZixun, Long> getBaseDao() {
		return this.ideaZixunDao;
	}
		
	
	@Override
	public Page<IdeaZixunBo> queryZixunPage(IdeaZixunBo query, PageRequest pageable,Long uid) {
		
		Long total = 0l;
		Page<IdeaZixun> viewPage = null;
		List<IdeaZixun> viewList = null;
		List<IdeaZixunBo> viewBoList = new ArrayList<IdeaZixunBo>();
		
		Map<Long,String> dptIdNameMap = new HashMap<Long,String>();
		Map<Long,String> userIdNameMap = new HashMap<Long,String>();
		
		
		viewPage = ideaZixunDao.selectPageList(query, pageable);
		total = viewPage.getTotal();
		
		viewList = viewPage.getContent();
		
		if(viewList != null){
			for(IdeaZixun zix : viewList){
				if(zix.getDepartmentId() != null){
					dptIdNameMap.put(zix.getDepartmentId(), "");
				}
				if(zix.getUpdatedUid() != null){
					userIdNameMap.put(zix.getUpdatedUid(), "");
				}
			}
			
			if(dptIdNameMap.size() != 0){
				Department dq = new Department();
				dq.setIds(new ArrayList<>(dptIdNameMap.keySet()));
				List<Department> dpts = departmentService.queryList(dq);
				
				for(Department ad : dpts){
					dptIdNameMap.put(ad.getId(),ad.getName());
				}
			}
			
			if(userIdNameMap.size() != 0){
				User uq = new User();
				uq.setIds(new ArrayList<>(userIdNameMap.keySet()));
				List<User> us = userService.queryList(uq);
				
				for(User au : us){
					userIdNameMap.put(au.getId(),au.getRealName());
				}
			}
		}

		for(IdeaZixun zix : viewList){
			
			IdeaZixunBo ab = new IdeaZixunBo();
			
			if(zix.getCreateUid().longValue() != uid.longValue()){
				ab.setCanEdit(1);
			}
			
			ab.setId(zix.getId());
			ab.setCode(zix.getCode());
			ab.setCompanyField(zix.getCompanyField());
			ab.setDepartName(dptIdNameMap.get(zix.getDepartmentId()));
			ab.setUserName(userIdNameMap.get(zix.getUpdatedUid()));
			ab.setUpdatedTime(zix.getUpdatedTime());
			ab.setCompanyName(zix.getCompanyName());
			ab.setCompanyUrl(zix.getCompanyUrl());
			ab.setCompanyAddress(zix.getCompanyAddress());
			ab.setCompanyBtime(zix.getCompanyBtime());
			ab.setRemark(zix.getRemark());
			ab.setDetailInfo(zix.getDetailInfo());
			ab.setCompanyCuser(zix.getCompanyCuser());
			viewBoList.add(ab);
		}
		
		return new Page<IdeaZixunBo>(viewBoList, pageable, total);
	}




	@Transactional
	@Override
	public void insertZixun(IdeaZixun zixun, IdeaZixunBo zixunbo) {
		Long zixunId = ideaZixunDao.insert(zixun);
		if(zixunId != null){
			List<ZixunFinance> rzs  = zixunbo.getFinaceList()==null?new ArrayList<ZixunFinance>():zixunbo.getFinaceList();
			for(ZixunFinance zf : rzs){
				zf.setZixunId(zixunId);
				
				zf.setCreateUid(zixun.getCreateUid());
				zf.setUpdatedUid(zixun.getCreateUid());
				zf.setCreatedTime(System.currentTimeMillis());
				zf.setUpdatedTime(System.currentTimeMillis());
				
				zixunFinanceDao.insert(zf);
			}
		}else{
			throw new DaoException("添加资讯入库返回Id失败");
		}
	}

	
	/**
	1.编辑资讯
	2.添加 、编辑融资
	3.删除融资
	*/
	@Transactional
	@Override
	public void editZixun(IdeaZixun zixun, IdeaZixunBo zixunbo) {
		// 1.编辑资讯
		ideaZixunDao.updateById(zixun);
		
		// 2.添加 、编辑融资
		List<ZixunFinance> rzList = zixunbo.getFinaceList();
		List<ZixunFinance> toSavaList = new ArrayList<ZixunFinance>();
		List<ZixunFinance> toEditList = new ArrayList<ZixunFinance>();
		
		if(rzList != null && !rzList.isEmpty()){
			for(ZixunFinance ac : rzList ){
				ac.setUpdatedUid(zixun.getUpdatedUid());
				ac.setUpdatedTime(System.currentTimeMillis());
				
				if(ac.getId()!=null && ac.getId().longValue() != 0){
					toEditList.add(ac);
				}else{
					ac.setZixunId(zixun.getId());
					ac.setCreateUid(zixun.getUpdatedUid());
					ac.setCreatedTime(System.currentTimeMillis());
					
					toSavaList.add(ac);
				}
			}
		}
		
		if(toSavaList != null && !toSavaList.isEmpty()){
			zixunFinanceDao.insertInBatch(toSavaList);
		}
		if(toEditList != null && !toEditList.isEmpty()){
			zixunFinanceDao.updateInBatch(toEditList);
		}

		
		// 3.删除融资
		List<Long> rzDelIdList = zixunbo.getRzIdList();
		if(rzDelIdList != null && !rzDelIdList.isEmpty()){
			zixunFinanceDao.deleteByIdInBatch(rzDelIdList);
		}
		
	}




	@Transactional
	@Override
	public void deleteZixun(IdeaZixun zx) {
		ideaZixunDao.updateById(zx);
		
		ZixunFinance zixunFinance = new ZixunFinance();
		zixunFinance.setZixunId(zx.getId());
		zixunFinance.setStatus((byte) 1);
		zixunFinance.setUpdatedUid(zx.getUpdatedUid());
		zixunFinanceDao.updateByIdSelective(zixunFinance);
		
	}


	
	
}