package com.galaxyinternet.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.project.PersonPoolBo;
import com.galaxyinternet.dao.project.PersonPoolDao;
import com.galaxyinternet.dao.project.ProjectPersonDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.project.ProjectPerson;
import com.galaxyinternet.service.PersonPoolService;

@Service("com.galaxyinternet.service.PersonPoolService")
public class PersonPoolServiceImpl extends BaseServiceImpl<PersonPool> implements PersonPoolService{
	
	@Autowired
	private PersonPoolDao personPoolDao;
	@Autowired
	private ProjectPersonDao projectPersonDao;
	
	@Override
	protected BaseDao<PersonPool, Long> getBaseDao() {
		return this.personPoolDao;
	}
	

	@Override
	public Page<PersonPool> queryPageListByPid(PersonPoolBo query, Pageable pageable) {
		ProjectPerson projectPerson=new ProjectPerson ();
		projectPerson.setProjectId(query.getProjectId());
		List<ProjectPerson> selectList = projectPersonDao.selectList(projectPerson);
		List<String> ids=new ArrayList<String>() ;
		if(selectList!=null){
			
			for( int i=0;i<selectList.size();i++){
				ProjectPerson p=selectList.get(i);
				ids.add(p.getPersonId().toString());
			}
		}
		Page<PersonPool> selectByPid=new Page<>(null, pageable, null);
		if(!ids.isEmpty()){
			query.setIds(ids);
		     selectByPid = personPoolDao.selectByPid(query, pageable);	
		}else{
			List<PersonPool> list=new ArrayList<PersonPool>();
			selectByPid.setContent(list);
			selectByPid.setTotal((long)0);
		}
		return selectByPid;
	}


	@Override
	public List<PersonPool> selectNoToTask(Map<String,Object> params) {
		return personPoolDao.selectNoToTask(params);
	}

	
	@Override
	@Transactional
	public Long addProjectPerson(PersonPoolBo pool) {
		Long id = personPoolDao.insert(pool);
		ProjectPerson pp = new ProjectPerson();
		pp.setPersonId(id);
		pp.setProjectId(pool.getProjectId());
		pp.setCreatedTime(System.currentTimeMillis());
		projectPersonDao.insert(pp);
		return id;
	}


	@Override
	public List<PersonPool> selectPersonPoolByPID(Long pid) throws DaoException {
		return personPoolDao.selectPersonPoolByPID(pid);
	}

}
