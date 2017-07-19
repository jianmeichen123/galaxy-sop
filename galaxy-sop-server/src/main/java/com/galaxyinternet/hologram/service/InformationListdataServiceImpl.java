package com.galaxyinternet.hologram.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.hologram.InformationListdataDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationListdataRemark;
import com.galaxyinternet.service.hologram.InformationListdataRemarkService;
import com.galaxyinternet.service.hologram.InformationListdataService;

@Service("com.galaxyinternet.service.hologram.InformationListdataService")
public class InformationListdataServiceImpl extends BaseServiceImpl<InformationListdata> implements InformationListdataService {

	@Autowired
	private InformationListdataDao informationListdataDao;

	@Autowired
	private InformationListdataRemarkService informationListdataRemarkService;
	
	@Override
	protected BaseDao<InformationListdata, Long> getBaseDao() {
		return this.informationListdataDao;
	}

	private final String STUDYEXPERIENCE ="study-experience";

	private final String WORKEXPERIENCE ="work-experience";

	private final String STARTUPEXPERIENCE="entrepreneurial-experience";

	/**
	 * 先根据 id 查 成员,再根据成员查学习经历
	 * @param id
	 * @return
	 */
	@Override
	public InformationListdata queryMemberById(Long id) {
		InformationListdata data = queryById(id);
		if(data != null){
			InformationListdata query = new InformationListdata();
			query.setParentId(data.getId());
			//按毕业时间倒序查询学习经历
			query.setCode(STUDYEXPERIENCE);
			query.setProperty("field_1");
			query.setDirection("desc");
			List<InformationListdata> studyList = informationListdataDao.selectList(query);
			//按结束时间倒序查询创业经历
			query.setCode(WORKEXPERIENCE);
			query.setProperty("field_2");
			query.setDirection("desc");
			List<InformationListdata> workList = informationListdataDao.selectList(query);
			//按结束时间倒序查询创业经历
			query.setCode(STARTUPEXPERIENCE);
			query.setProperty("field_2");
			query.setDirection("desc");
			List<InformationListdata> startupList = informationListdataDao.selectList(query);
			data.setWorkList(workList);
			data.setStudyList(studyList);
			data.setStartupList(startupList);
		}
		return data;
	}

	@Override
	public void deleteOneMember(Long id) {
		InformationListdata data = queryById(id);
		if(data != null) {
			//根据subcode查询code
			String code = data.getCode();
			List<String> codes = getSubCodes(code);
			if(codes.size()>0){
				//删除子级
				InformationListdata querydata = new InformationListdata();
				querydata.setCodes(codes);
				querydata.setProjectId(data.getProjectId());
				informationListdataDao.delete(querydata);
			}
			informationListdataDao.deleteById(id);
		}
	}

	/**
	 *
	 * @param code list_data 表父级code
	 * @return List<String> 子级code
	 */
	private List<String>  getSubCodes(String code){
		InformationListdataRemark query = new InformationListdataRemark();
		query.setSubCode(code);
		List<InformationListdataRemark> remarkList = informationListdataRemarkService.queryList(query);
		List<String> codes = new ArrayList<>();
		if(remarkList != null && remarkList.size()>0)
		for(InformationListdataRemark remark :remarkList){
			codes.add(remark.getCode());
		}
		return codes;
	}

	@Override
	public void save(InformationListdata entity)
	{
		if(entity != null)
		{
			if(entity.getId() != null)
			{
				updateById(entity);
			}
			else
			{
				insert(entity);
			}
		}
	}

	@Override
	public void saveBatch(List<InformationListdata> list)
	{
		if(list== null || list.size() == 0)
		{
			return;
		}
		for(InformationListdata item : list)
		{
			save(item);
		}
	}
	
	
}
