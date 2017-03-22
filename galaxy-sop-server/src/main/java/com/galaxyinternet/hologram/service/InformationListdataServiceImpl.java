package com.galaxyinternet.hologram.service;

import com.galaxyinternet.dao.hologram.InformationListdataDao;
import com.galaxyinternet.dao.hologram.InformationListdataRemarkDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationListdataRemark;
import com.galaxyinternet.service.hologram.InformationListdataRemarkService;
import com.galaxyinternet.service.hologram.InformationListdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
			//根据subcode查询code
			String code  = data.getCode();
			List<String> codes = getSubCodes(code);
			if(codes.size()>0){
				//根据code查询listdata表记录
				InformationListdata querydata = new InformationListdata();
				querydata.setCodes(codes);
				querydata.setProjectId(data.getProjectId());
				List<InformationListdata> dataList = informationListdataDao.selectList(querydata);
				//给list字段赋值
				List<InformationListdata> workList = new ArrayList<InformationListdata>();
				List<InformationListdata> studyList = new ArrayList<InformationListdata>();
				List<InformationListdata> startupList = new ArrayList<InformationListdata>();
				if(null != dataList && dataList.size()>0){
					for(InformationListdata info : dataList){
						if(info.getCode().equals(STUDYEXPERIENCE)){
							studyList.add(info);
						}else if(info.getCode().equals(WORKEXPERIENCE)){
							workList.add(info);
						}else if(info.getCode().equals(STARTUPEXPERIENCE)){
							startupList.add(info);
						}
					}
				}
				data.setWorkList(workList);
				data.setStudyList(studyList);
				data.setStartupList(startupList);
			}else{
				return data;
			}
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
		if(remarkList != null && remarkList.size()>0){
			for(InformationListdataRemark remark :remarkList){
				codes.add(remark.getCode());
			}
		}
		return codes;
	}

}
