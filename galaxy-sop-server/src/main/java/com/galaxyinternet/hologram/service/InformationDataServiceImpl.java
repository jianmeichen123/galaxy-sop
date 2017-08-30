package com.galaxyinternet.hologram.service;

import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.dao.hologram.InformationFileDao;
import com.galaxyinternet.dao.hologram.InformationFixedTableDao;
import com.galaxyinternet.dao.hologram.InformationListdataDao;
import com.galaxyinternet.dao.hologram.InformationResultDao;
import com.galaxyinternet.dao.hologram.ScoreInfoDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.thread.GalaxyThreadPool;
import com.galaxyinternet.framework.core.utils.StringEx;
import com.galaxyinternet.hologram.util.RegexUtil;
import com.galaxyinternet.model.hologram.FixedTableModel;
import com.galaxyinternet.model.hologram.InformationData;
import com.galaxyinternet.model.hologram.InformationFile;
import com.galaxyinternet.model.hologram.InformationFixedTable;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationModel;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.model.hologram.InformationScore;
import com.galaxyinternet.model.hologram.TableModel;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.hologram.InformationDataService;
import com.galaxyinternet.utils.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

@Service
public class InformationDataServiceImpl extends BaseServiceImpl<InformationData>implements InformationDataService
{

	@Autowired
	private InformationResultDao resultDao;
	@Autowired
	private InformationFixedTableDao fixdTableDao;
	@Autowired
	private InformationListdataDao listdataDao;
	@Autowired
	private ScoreInfoDao scoreInfoDao;
	@Autowired
	private InformationFileDao infoFileDao;

	@Override
	public void save(InformationData data)
	{
		saveResult(data);
		saveListData(data);
		saveFixedTable(data);
		saveScore(data);
		saveFiles(data);
	}
	private void saveResult(InformationData data)
	{
		String projectId = data.getProjectId();
		List<InformationModel> list = data.getInfoModeList();
		String investment = "";
		if (projectId == null || ((list == null || list.size() == 0)
				&& (data.getDeletedResultTids() == null || data.getDeletedResultTids().size() == 0))) {
			return;
		}

		InformationResult entity = null;
		List<InformationResult> entityList = new ArrayList<>(); // 增加
		List<InformationResult> uodateList = new ArrayList<>(); // 修改

		Set<String> titleIds = new HashSet<>(); // 多条结果集 删除 （projectid titleid）
		if (data.getDeletedResultTids() != null && !data.getDeletedResultTids().isEmpty()) {
			titleIds = data.getDeletedResultTids();
		}

		for (InformationModel model : list) {
			if (!"true".equals(model.getTochange()) || model.getTitleId() == null)
				continue;
			// 判断是否为决策报告里面，处理投资金额字段
			if (null != model.getReportType() && model.getReportType() == 3 && model.getType().equals("19")) {
				if (model.getTitleId().equals("3004")) {
					investment = model.getRemark1();
				}
			}
			if (model.getType() != null
					&& (model.getType().equals("3") || model.getType().equals("6") || model.getType().equals("13"))) {
				titleIds.add(model.getTitleId());
				model.setResultId(null);
			}

			entity = new InformationResult();

			entity.setProjectId(projectId);
			entity.setTitleId(model.getTitleId());
			if (!StringEx.isNullOrEmpty(model.getValue()) && StringUtils.isNumeric(model.getValue())) {
				entity.setContentChoose(model.getValue());
			}
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

		if (titleIds.size() > 0) {
			InformationResult query = new InformationResult();
			query.setProjectId(projectId);
			query.setTitleIds(titleIds);
			resultDao.delete(query);
		}
		if (uodateList.size() > 0) {
			// ===== 待 1.7 rc环境时 删除
			List<InformationResult> toVaild1_list = new ArrayList<>();
			InformationResult toVaild1;
			for (InformationResult tempUp : uodateList) {
				toVaild1 = new InformationResult();
				toVaild1.setProjectId(tempUp.getProjectId());
				toVaild1.setTitleId(tempUp.getTitleId());
				toVaild1.setIsValid("1");
				toVaild1_list.add(toVaild1);
			}
			resultDao.updateInBatch(toVaild1_list);
			// ==== end
			resultDao.updateInBatch(uodateList);
		}
		// 插入数据
		if (entityList.size() > 0) {
			resultDao.insertInBatch(entityList);
		}
		InformationResult resultUpdate = new InformationResult();
		// 决策报告编辑估值安排时候计算项目投资额
		if (null != investment && !"".equals(investment)) {
			InformationResult result = new InformationResult();
			result.setProjectId(projectId);
			Set<String> titleids = new HashSet<String>();
			titleids.add("3012");
			titleids.add("3010");
			result.setTitleIds(titleids);
			result.setIsValid("0");
			User user = WebUtils.getUserFromSession();
			Long userId = user != null ? user.getId() : null;
			Long now = new Date().getTime();
			List<InformationResult> selectList = resultDao.selectList(result);
			Double v = null;
			int count=0;
			for (int i = 0; i < selectList.size(); i++) {
					InformationResult resultNew = selectList.get(i);
					if (resultNew.getTitleId().equals("3010") && null != resultNew.getContentDescribe1()) {
						v = Double.parseDouble(investment) / Double.parseDouble(resultNew.getContentDescribe1())*100;
					}else if (null != v&&resultNew.getTitleId().equals("3012")) {
						count=count+1;
						resultUpdate=resultNew;
				}
			}
			if(null!=v){
				if(count>0){
					result = resultUpdate;
					result.setContentDescribe1(v.toString());
					result.setUpdatedTime(now);
					result.setUpdateId(userId.toString());
					resultDao.updateById(result);
				}else {
					result.setContentDescribe1(v.toString());
					result.setCreatedTime(now);
					result.setCreateId(userId.toString());
					resultDao.insert(result);
				}
			}
			
		}
	}			
			
		
		

	private void saveFixedTable(InformationData data)
	{
		String projectId = data.getProjectId();
		List<FixedTableModel> list = data.getInfoFixedTableList();
		if(projectId == null || list == null || list.size() ==0)
		{
			return;
		}
		InformationFixedTable entity = null;
		List<InformationFixedTable> insertEntityList = new ArrayList<>();
		User user = WebUtils.getUserFromSession();
		Long userId = user != null ? user.getId() : null;
		Long now = new Date().getTime();
		for(FixedTableModel model : list)
		{
			entity = new InformationFixedTable();
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
				fixdTableDao.updateById(entity);
				continue;
			}
			entity.setCreatedTime(now);
			entity.setCreateId(userId+"");
			insertEntityList.add(entity);
		}
		//插入数据
		if(insertEntityList.size() > 0)
		{
			fixdTableDao.insertInBatch(insertEntityList);
		}
		
	}
	private void saveListData(InformationData data)
	{
		String projectId = data.getProjectId();
		if(projectId == null)
		{
			return;
		}
		//删除数据
		if(data.getDeletedRowIds() != null && data.getDeletedRowIds().size() > 0)
		{
			InformationListdata query = new InformationListdata();
			query.setIds(data.getDeletedRowIds());
			listdataDao.delete(query);
		}
		List<TableModel> list = data.getInfoTableModelList();
		if(list == null || list.size() ==0)
		{
			return;
		}
		InformationListdata entity = null;
		Set<String> titleIds = new HashSet<>();
		
		for(TableModel model : list)
		{
			titleIds.add(model.getTitleId()+"");
			entity = new InformationListdata();
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
			
			User user = WebUtils.getUserFromSession();
			Long userId = user != null ? user.getId() : null;
			Long now = new Date().getTime();
			if(model.getId() == null)
			{
				entity.setCreatedTime(now);
				entity.setCreateId(userId);
				entity.setUpdateId(userId);
				entity.setUpdateTime(now);
				Long id = listdataDao.insert(entity);
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
				}
				entity.setUpdateId(userId);
				listdataDao.updateById(entity);
				if(model.getDataList() != null && model.getDataList().size() > 0){
					setDataList(entity.getId(),entity,model.getDataList(),
							userId,entity.getUpdatedTime());
				}
				
			}
		}
		
	}
	
	
	public void saveScore(InformationData data)
	{
		if(data == null || data.getProjectId() == null || data.getScoreList()==null)
		{
			return;
		}
		final Long projectId = Long.valueOf(data.getProjectId());
		final List<InformationScore> scoreList = data.getScoreList();
		Set<Long> relateIds = new HashSet<>();
		for(InformationScore item : scoreList)
		{
			relateIds.add(item.getRelateId());
		}
		if(relateIds.size() >0)
		{
			InformationScore query = new InformationScore();
			query.setRelateIds(relateIds);
			query.setProjectId(projectId);
			scoreInfoDao.deleteScoreBatch(query);
			scoreInfoDao.insertScoreBatch(scoreList);
			
			ExecutorService pool = GalaxyThreadPool.getExecutorService();
			pool.submit(new Runnable(){
				@Override
				public void run()
				{
					copyScore(scoreList, projectId);
				}
			});
		}
	}
	/**
	 * 评测报告与初评报告分数保持一致
	 * @param data
	 */
	public void copyScore(List<InformationScore> scoreList, Long projectId)
	{
		Set<Long> relateIds = new HashSet<>();
		for(InformationScore item : scoreList)
		{
			Long fromId = item.getRelateId();
			Long targetId = fromId;
			if(fromId.intValue() > 8000)
			{
				targetId = fromId-8000L;
			}
			else
			{
				return;
			}
			item.setRelateId(targetId);
			relateIds.add(targetId);
		}
		if(relateIds.size() >0)
		{
			InformationScore query = new InformationScore();
			query.setRelateIds(relateIds);
			query.setProjectId(projectId);
			scoreInfoDao.deleteScoreBatch(query);
			scoreInfoDao.insertScoreBatch(scoreList);
		}
	}
	
	public void setDataList(Long id,InformationListdata entity,List<InformationListdata> list,
			Long userId,Long now){
		if(id > 0 && list != null && list.size() > 0){
			for(InformationListdata tm : list){
				InformationListdata td;
				try {
					td = (InformationListdata) entity.clone();
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
					if(tm.getId() == null){
						//新增
						td.setId(null);
						entity.setUpdateId(userId);
						entity.setUpdateTime(now);
						listdataDao.insert(td);
					}else{
						//修改
						td.setId(tm.getId());
						td.setUpdateTime(now);
						td.setUpdateId(userId);
						listdataDao.updateById(td);
					}
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private void saveFiles(InformationData data)
	{
		Set<Long> delIds = data.getDeleteFileIds();
		if(delIds != null && delIds.size()>0)
		{
			for(Long id : delIds)
			{
				infoFileDao.deleteById(id);
			}
		}
		List<InformationFile> infoFiles = data.getInfoFileList();
		if(infoFiles == null || infoFiles.size() == 0)
		{
			return;
		}
		
		try
		{
			//插入新添加的文件
			for(InformationFile infoFile : infoFiles)
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
				infoFileDao.insert(infoFile);
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected BaseDao<InformationData, Long> getBaseDao()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
