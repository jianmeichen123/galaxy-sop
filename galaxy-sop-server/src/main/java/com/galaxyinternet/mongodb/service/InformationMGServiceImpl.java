package com.galaxyinternet.mongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.StringEx;
import com.galaxyinternet.hologram.util.RegexUtil;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.mongodb.model.FixedTableModelMG;
import com.galaxyinternet.mongodb.model.InformationDataMG;
import com.galaxyinternet.mongodb.model.InformationFixedTableMG;
import com.galaxyinternet.mongodb.model.InformationListdataMG;
import com.galaxyinternet.mongodb.model.InformationModelMG;
import com.galaxyinternet.mongodb.model.InformationResultMG;
import com.galaxyinternet.mongodb.model.TableModelMG;

@Service("com.galaxyinternet.mongodb.service.InformationMGService")
public class InformationMGServiceImpl extends BaseServiceImpl<InformationDataMG>implements InformationMGService {
	
	@Autowired
	private InformationFileMGService informationFileMGService;
	
	@Autowired
	private InformationFixedTableMGService informationFixedTableMGService;
	@Autowired
	private InformationListdataMGService informationListdataMGService;
	
	@Autowired
	private InformationResultMGService informationResultMGService;
	

	@Override
	public void save(InformationDataMG data) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		saveResult(data);
	//	saveListData(data);
		//saveFixedTable(data);
	//	saveFiles(data);
	}
	private void saveResult(InformationDataMG data)
	{
		String projectId = data.getProjectId();
		List<InformationModelMG> list = data.getInfoModeList();
		InformationResultMG entity = null;
		List<InformationResultMG> entityList = new ArrayList<>(); // 增加
		for (InformationModelMG model : list) {
			entity = new InformationResultMG();
			entity.setProjectId(projectId);
			entity.setTitleId(model.getTitleId());
			if (!StringEx.isNullOrEmpty(model.getRemark1())) {
				if (model.getType().equals("5") || model.getType().equals("6") || model.getType().equals("8")
						|| model.getType().equals("15")) {
					entity.setContentDescribe1(RegexUtil.getTextFromHtml(model.getRemark1()));
				} else {
					entity.setContentDescribe1(model.getRemark1());
				}
			}
			if (!StringEx.isNullOrEmpty(model.getRemark2())) {
				if (model.getType().equals("15")) {
					entity.setContentDescribe2(RegexUtil.getTextFromHtml(model.getRemark2()));
				} else {
					entity.setContentDescribe2(model.getRemark2());
				}
			}
			entity.setContentChoose(model.getValue());
			User user = WebUtils.getUserFromSession();
			Long userId = user != null ? user.getId() : null;
			Long now = new Date().getTime();
			entity.setCreateId(userId.toString());
			entityList.add(entity); // 新增
		}
		 //判断并且删除已经存在的模块答案
		  judgeAndDeleteModel(data);
		// 插入数据
		for (int i=0;i<entityList.size();i++) {
			InformationResultMG result=entityList.get(i);
			  try {
			    result.setParentId(data.getParentId());
				informationResultMGService.save(result);
			} catch (MongoDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		
	}			
			
		
		

	private void saveFixedTable(InformationDataMG data)
	{
		String projectId = data.getProjectId();
		List<FixedTableModelMG> list = data.getInfoFixedTableList();
		if(projectId == null || list == null || list.size() ==0)
		{
			return;
		}
		InformationFixedTableMG entity = null;
		List<InformationFixedTableMG> insertEntityList = new ArrayList<>();
		User user = WebUtils.getUserFromSession();
		Long userId = user != null ? user.getId() : null;
		Long now = new Date().getTime();
		for(FixedTableModelMG model : list)
		{
			entity = new InformationFixedTableMG();
			entity.setProjectId(projectId);
			entity.setTitleId(model.getTitleId());
			entity.setRowNo(model.getRowNo());
			entity.setColNo(model.getColNo());
			entity.setType(model.getType());
			entity.setContent(model.getValue());
			if(model.getValueId() != null)
			{
				entity.setUpdateId(userId+"");
				//entity.setUpdatedTime(now);
				entity.setId(model.getValueId());
				try {
					informationFixedTableMGService.updateById(model.getValueId().toString(), entity);
				} catch (MongoDBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
		//	entity.setCreatedTime(now);
			entity.setCreateId(userId+"");
			insertEntityList.add(entity);
		}
		//插入数据
		if(insertEntityList.size() > 0)
		{
			for(int i=0;i<insertEntityList.size();i++){
				InformationFixedTableMG result=insertEntityList.get(i);

				try {
					informationFixedTableMGService.save(result);
				} catch (MongoDBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	private void saveListData(InformationDataMG data)
	{
		String projectId = data.getProjectId();
		if(projectId == null)
		{
			return;
		}
		List<TableModelMG> list = data.getInfoTableModelList();
		if(list == null || list.size() ==0)
		{
			return;
		}
		InformationListdataMG entity = null;
		Set<String> titleIds = new HashSet<>();
		for(TableModelMG model : list)
		{
			titleIds.add(model.getTitleId()+"");
			entity = new InformationListdataMG();
			entity.setParentId(data.getParentId());
			entity.setProjectId(projectId);
			entity.setTitleId(model.getTitleId().toString());
			entity.setCode(model.getCode());
			entity.setField1(model.getField1());
			entity.setField2(model.getField2());
			entity.setField3(model.getField3());
			entity.setField4(model.getField4());
			entity.setField5(model.getField5());
			entity.setField6(model.getField6());
			entity.setField7(model.getField7());
			entity.setField8(model.getField8());
			entity.setField9(model.getField9());
			entity.setField10(model.getField10());
			entity.setField11(model.getField11());
			entity.setField12(model.getField12());
			entity.setField13(model.getField13());
			entity.setField14(model.getField14());
			entity.setField15(model.getField15());
			entity.setField16(model.getField16());
			entity.setRelateFileId(model.getRelateFileId());
			User user = WebUtils.getUserFromSession();
			Long userId = user != null ? user.getId() : null;
			Long now = new Date().getTime();
			entity.setCreateId(userId);
			entity.setCreateTime(now);
			judgeAndDeleteModel(data);
				try {
					informationListdataMGService.save(entity);
				} catch (MongoDBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	/**/
	@Override
	protected BaseDao<InformationDataMG, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void judgeAndDeleteModel(InformationDataMG data){
		
		List<InformationResultMG> findInfoModeList=new ArrayList<InformationResultMG>();
		List<InformationListdataMG> findInfoTableModelList=new ArrayList<InformationListdataMG>();
		try {
			if(null!=data.getInfoTableModelList()&&data.getInfoTableModelList().size()>0){
				InformationListdataMG param=new InformationListdataMG();
				param.setParentId(data.getParentId());
				param.setProjectId(data.getProjectId());
				param.setTitleId(data.getInfoTableModelList().get(0).getTitleId().toString());
				findInfoTableModelList=informationListdataMGService.find(param);
				if(null!=findInfoTableModelList&&findInfoTableModelList.size()>0){
					informationListdataMGService.deleteByCondition(param);
				}
			}else if(null!=data.getInfoModeList()&&data.getInfoModeList().size()>0){
				InformationResultMG param=new InformationResultMG();
				param.setParentId(data.getParentId());
				param.setProjectId(data.getProjectId());
				findInfoModeList = informationResultMGService.find(param);
				if(null!=findInfoModeList&&findInfoModeList.size()>0){
					try {
						informationResultMGService.deleteByCondition(param);
					} catch (MongoDBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		   
		} catch (MongoDBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}
