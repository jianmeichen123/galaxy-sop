package com.galaxyinternet.mongodb.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.utils.StringEx;
import com.galaxyinternet.hologram.util.RegexUtil;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.mongodb.model.FixedTableModelMG;
import com.galaxyinternet.mongodb.model.InformationDataMG;
import com.galaxyinternet.mongodb.model.InformationFileMG;
import com.galaxyinternet.mongodb.model.InformationFixedTableMG;
import com.galaxyinternet.mongodb.model.InformationListdataMG;
import com.galaxyinternet.mongodb.model.InformationModelMG;
import com.galaxyinternet.mongodb.model.InformationResultMG;
import com.galaxyinternet.mongodb.model.TableModelMG;
import com.galaxyinternet.utils.FileUtils;

@Service("com.galaxyinternet.mongodb.service.InformationMGService")
public class InformationMGServiceImpl implements InformationMGService {
	
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
		saveListData(data);
		saveFixedTable(data);
		saveFiles(data);
	}
	private void saveResult(InformationDataMG data)
	{
		String projectId = data.getProjectId();
		List<InformationModelMG> list = data.getInfoModeList();
		InformationResultMG entity = null;
		List<InformationResultMG> entityList = new ArrayList<>(); // 增加
		List<InformationResultMG> uodateList = new ArrayList<>(); // 修改
		for (InformationModelMG model : list) {
			if (!"true".equals(model.getTochange()) || model.getTitleId() == null)
				continue;
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

			User user = WebUtils.getUserFromSession();
			Long userId = user != null ? user.getId() : null;
			Long now = new Date().getTime();
			if (null == model.getResultId() || model.getResultId().equals("")) {
				entity.setCreatedTime(now);
				entity.setCreateId(userId.toString());
				entityList.add(entity); // 新增
			} else {
				entity.setId(new Long(model.getResultId()));
				entity.setUpdatedTime(now);
				entity.setUpdateId(userId.toString());
				entity.setIsValid("0");
				uodateList.add(entity); // 修改
			}
		}
		// 插入数据
		for (int i=0;i<entityList.size();i++) {
		InformationResultMG result=entityList.get(i);
		  try {
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
				entity.setUpdatedTime(now);
				entity.setId(model.getValueId());
				try {
					informationFixedTableMGService.updateById(model.getValueId().toString(), entity);
				} catch (MongoDBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			entity.setCreatedTime(now);
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
		//删除数据
		if(data.getDeletedRowIds() != null && data.getDeletedRowIds().size() > 0)
		{
			InformationListdataMG query = new InformationListdataMG();
			query.setIds(data.getDeletedRowIds());
			try {
				informationListdataMGService.deleteByCondition(query);
			} catch (MongoDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<TableModelMG> list = data.getInfoTableModelMGList();
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
			entity.setProjectId(Long.valueOf(projectId));
			entity.setTitleId(Long.valueOf(model.getTitleId()));
			entity.setCode(model.getCode());
			entity.setParentId(model.getParentId());
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
			entity.setRelateFileId(model.getRelateFileId());
			User user = WebUtils.getUserFromSession();
			Long userId = user != null ? user.getId() : null;
			Long now = new Date().getTime();
			
			if(model.getId() == null)
			{
				entity.setCreatedTime(now);
				entity.setCreateId(userId);
				entity.setUpdateId(userId);
				entity.setUpdateTime(now);
				Long id = null;
				try {
					
					informationListdataMGService.save(entity);
					InformationListdataMG findOne = informationListdataMGService.findOne(entity);
					
					if(null!=findOne){
						id=findOne.getId();	
					}
				
				} catch (MongoDBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					if(model.getDataList() != null && model.getDataList().size() > 0){
					setDataList(id,entity,model.getDataList(),
							userId,now);
				}
				continue;	
			}
			else
			{
				entity.setId(model.getId());
				if(StringUtils.isNotEmpty(model.getUpdateTimeSign())){
					entity.setUpdateTime(now);
					entity.setUpdateId(userId);
				}
				try {
					informationListdataMGService.updateById(model.getId().toString(), entity);
				} catch (MongoDBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(model.getDataList() != null && model.getDataList().size() > 0){
					setDataList(entity.getId(),entity,model.getDataList(),
							userId,entity.getUpdatedTime());
				}
				
			}
		}
		
	}
	
	public void setDataList(Long id,InformationListdataMG entity,List<InformationListdataMG> list,
			Long userId,Long now){
		if(id > 0 && list != null && list.size() > 0){
			for(InformationListdataMG tm : list){
				InformationListdataMG td;
				try {
					td = (InformationListdataMG) entity.clone();
					td.setParentId(id);
					td.setCode(tm.getCode());
					td.setField1(tm.getField1());
					td.setField2(tm.getField2());
					td.setField3(tm.getField3());
					td.setField4(tm.getField4());
					td.setField5(tm.getField5());
					td.setField6(tm.getField6());
					td.setField7(tm.getField7());
					td.setField8(tm.getField8());
					td.setField9(tm.getField9());
					td.setField10(tm.getField10());
					td.setRelateFileId(tm.getRelateFileId());
					if(tm.getId() == null){
						//新增
						td.setId(null);
						td.setUpdateId(userId);
						td.setUpdateTime(now);
						try {
							informationListdataMGService.save(td);
						} catch (MongoDBException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						//修改
						td.setId(tm.getId());
						td.setUpdateTime(now);
						td.setUpdateId(userId);
						try {
							informationListdataMGService.updateById(tm.getId().toString(), td);
						} catch (MongoDBException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private void saveFiles(InformationDataMG data)
	{
		List<InformationFileMG> infoFiles = data.getInfoFileList();
		try
		{
			//插入新添加的文件
			for(InformationFileMG infoFile : infoFiles)
			{
				if(infoFile.getId() != null)
				{
					continue;
				}
				String fileData = infoFile.getData();
				String suffix = fileData.substring(fileData.indexOf("data:image/")+11,fileData.indexOf(";base64,"));
				String fileName = "image-"+System.currentTimeMillis();
				File tempFile = File.createTempFile(fileName, "."+suffix);
				String fileKey = String.valueOf(IdGenerator.generateId(OSSHelper.class));
				fileData = fileData.substring(fileData.indexOf(";base64,")+8, fileData.length());
				FileUtils.base64ToFile(fileData, tempFile);
				UploadFileResult rtn = OSSHelper.simpleUploadByOSS(tempFile, fileKey, OSSHelper.setRequestHeader(tempFile.getName(), tempFile.length())); //上传至阿里云
				String bucketName = rtn.getBucketName();
				String url = OSSHelper.getUrl(bucketName,fileKey);
				Long now = System.currentTimeMillis();
				
				infoFile.setFileKey(fileKey);
				infoFile.setFileLength(tempFile.length()+"");
				infoFile.setBucketName(bucketName);
				infoFile.setFileSuffix(suffix);
				infoFile.setUpdatedTime(now);
				infoFile.setFileName(fileName);
				infoFile.setFileUrl(url);
				infoFile.setCreatedTime(now);
				try {
					informationFileMGService.save(infoFile);
				} catch (MongoDBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
}
