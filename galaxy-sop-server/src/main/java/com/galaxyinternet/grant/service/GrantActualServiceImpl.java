package com.galaxyinternet.grant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.GrantActualDao;
import com.galaxyinternet.dao.GrantFileDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.file.FileResult;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.thread.GalaxyThreadPool;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.GrantActual;
import com.galaxyinternet.model.GrantFile;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopDownLoad;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.service.GrantActualService;

@Service("com.galaxyinternet.grant.GrantActualService")
public class GrantActualServiceImpl extends BaseServiceImpl<GrantActual> implements GrantActualService{
	
	final Logger logger = LoggerFactory.getLogger(GrantPartServiceImpl.class);
	
	@Autowired
	private GrantActualDao grantActualDao;
	
	@Autowired
	private GrantFileDao grantFileDao;
	
	@Autowired
	private SopFileDao fileDao;
	
	
	@Override
	protected BaseDao<GrantActual, Long> getBaseDao() {
		return this.grantActualDao;
	}

	@Override
	public Map<String, Object> lookActualDetail(Long actualId) {
		return grantActualDao.lookActualDetail(actualId);
	}

	@Override
	public double calculateBelongToActualMoney(Long partId) {
		return grantActualDao.sumBelongToActualMoney(partId);
	}

	
	
	
	
	
	
	
	
	/**
	 *	由实际拨款 id  查询其  文件 ids
	 */
	public List<Long> grantActualFileList(Long partId) {
		List<Long> listFile = new ArrayList<Long>();
		GrantFile grantFile = new GrantFile();
//		grantFile.setGrantId(partId);
		grantFile.setTid(partId);
		grantFile.setTarget(Constants.ACTUAL_TARGET);
		List<GrantFile> dfList = grantFileDao.selectList(grantFile);
		if(dfList != null && dfList.size() > 0){
			for(GrantFile df:dfList){
				listFile.add(df.getFileId());
			}
		}
		return listFile;
	}
	
	/**
	 * 由 文件 ids 查询 文件数据
	 */
	public List<SopFile> getFilesByIds(List<Long> ids){
		SopFileBo sf = new SopFileBo();
		sf.setIds(ids);
		List<SopFile> files = fileDao.selectList(sf);  //文件
		return files;
	}
	
	/**
	 * 由 文件 ids 删除 aliyun 文件
	 */
	public void delAliyunFiles(List<Long> ids){
		List<SopFile> files = getFilesByIds(ids);  //文件
		if(files!=null){
			for(SopFile file : files){
				try {
					FileResult fileResult = OSSHelper.deleteFile(file.getBucketName(), file.getFileKey());
					if(fileResult.getResult().getStatus().equals(Status.ERROR)){
						logger.error("delAliyunFiles 删除 aliyun 文件失败");
					}
				} catch (Exception e) {
					logger.error("delAliyunFiles 删除 aliyun 文件失败 "+ GSONUtil.toJson(file),e);
				}
				
			}
		}
	}
	
	
	/**
	 * 多文件事项查询
	 */
	@Override
	public GrantActual selectGrantActual(Long ActualId) {
		GrantActual grantActual = grantActualDao.selectById(ActualId);
		if(grantActual!=null){
			List<Long> fileidlist =  grantActualFileList(ActualId); //事项 文件 关联
			if(fileidlist!=null && !fileidlist.isEmpty()){
				List<SopFile> files = getFilesByIds(fileidlist);  //文件
				if(files!=null && !files.isEmpty()){
					grantActual.setFiles(files);
				}
			}
		}
		return grantActual;
	}
	
	
	/**
	 * 多文件事项新增
	 * 1.新增事项                   GrantActual表
	 * 2.新增   新加附件       file表、GrantFile中间表
	 */
	@Override
	@Transactional
	public void insertGrantActual(GrantActual grantActual,Project project) {
		grantActual.setFileNum((byte) 0);
		Byte fnum = null;
		Long grantid = null;
		List<GrantFile> dfileIn = new ArrayList<GrantFile>();
		List<SopFile> sopfiles = grantActual.getFiles();
		
		if(sopfiles!=null && !sopfiles.isEmpty()){
			fnum = (byte) sopfiles.size();
			grantActual.setFileNum(fnum);
		}
		grantid = grantActualDao.insert(grantActual); 
		
		if(sopfiles!=null && !sopfiles.isEmpty() && fnum !=null){
			for(SopFile sopfile:sopfiles){
				sopfile.setProjectId(project.getId());
				sopfile.setProjectProgress(project.getProjectProgress());
				sopfile.setCareerLine(project.getProjectDepartid());
				sopfile.setFileStatus(DictEnum.fileStatus.已上传.getCode());
				sopfile.setFileUid(project.getCreateUid());
			}
			fileDao.insertInBatch(sopfiles);
			
			for(SopFile sopfile:sopfiles){
				GrantFile df = new GrantFile();
				df.setFileId(sopfile.getId());
				df.setTid(grantid);
				df.setTarget(Constants.ACTUAL_TARGET);
				
				dfileIn.add(df);
			}
			grantFileDao.insertInBatch(dfileIn);
		}
	}

	
	
	
	/**
	 * 多文件事项更新
	 * 1.删除   已删除附件   file表、GrantFile中间表
	 * 2.新增   新加附件       file表、GrantFile中间表
	 * 3.更新事项                   GrantActual表
	 */
	@Override
	@Transactional
	public void upateGrantActual(GrantActual grantActual,Project project) {
		List<SopFile> upFiles = grantActual.getFiles();   		//新存入 sopfile
		List<Long> oldHasFileIds = grantActual.getFileIds();      //原fileids 还保留的
		List<Long> toDelfileids = new ArrayList<Long>();
		
		Byte allNum = grantActual.getFileNum();
		//Byte oldNum = oldGrantActual.getFileNum() == null ?0:oldGrantActual.getFileNum();
		GrantFile grantFile = new GrantFile();
		grantFile.setTid(grantActual.getId());
		grantFile.setTarget(Constants.ACTUAL_TARGET);
		List<GrantFile> dfList = grantFileDao.selectList(grantFile);
		Byte oldNum = dfList == null ? 0 : (byte)dfList.size();
		
		Byte oldHasNum = (byte) (oldHasFileIds ==null?0:oldHasFileIds.size());
		Byte upNum = (byte) (upFiles == null?0:upFiles.size());
		
		//文件删除
		if(oldNum!=0){
			if(oldHasNum == 0){
				toDelfileids = grantActualFileList(grantActual.getId());  
			}else if(oldNum.byteValue() != oldHasNum.byteValue()){
				List<Long> oldfileids = grantActualFileList(grantActual.getId());  //查询中间表， 取原 fileids
				for(Long oldId : oldfileids){
					if(!oldHasFileIds.contains(oldId)){
						toDelfileids.add(oldId);
					}
				}
			}
		}
		if(toDelfileids!=null && !toDelfileids.isEmpty()){
			SopFileBo fileQ = new SopFileBo();
			fileQ.setIds(toDelfileids);
			fileDao.delete(fileQ);    // 删除 file、表
			
			GrantFile dfQ = new GrantFile();
			dfQ.setFileIds(toDelfileids);
			//dfQ.setTarget(Constants.ACTUAL_TARGET);
			//dfQ.setTids(toDelfileids);
			grantFileDao.delete(dfQ); // 删除 中间表
		}
		
		//新文件上传
		// 插入  文件表   sopfil
		// 插入  拨款文件中间表  GrantFile
		if(allNum == null || allNum == 0){
			grantActual.setFileNum((byte) 0);
		}else{
			grantActual.setFileNum((byte) (oldHasNum+upNum));
			
			if(upNum != 0){ 
				List<GrantFile> dfileIn = new ArrayList<GrantFile>();
				
				for(SopFile sopfile:upFiles){
					sopfile.setProjectId(project.getId());
					sopfile.setProjectProgress(project.getProjectProgress());
					sopfile.setCareerLine(project.getProjectDepartid());
					sopfile.setFileStatus(DictEnum.fileStatus.已上传.getCode());
					sopfile.setFileUid(project.getCreateUid());
				}
				fileDao.insertInBatch(upFiles);
				
				for(SopFile sopfile:upFiles){
					GrantFile df = new GrantFile();
					df.setTid(grantActual.getId());
					df.setTarget(Constants.ACTUAL_TARGET);
					df.setFileId(sopfile.getId());
					dfileIn.add(df);
				}
				grantFileDao.insertInBatch(dfileIn);
			}
		}
		
		//更新拨款
		grantActualDao.updateById(grantActual);
		
		
		// 删除 阿里云 文件
		if(toDelfileids!=null && !toDelfileids.isEmpty()){
			final List<Long> alifileidlist = toDelfileids;
			if(alifileidlist!=null && !alifileidlist.isEmpty()){ 
				GalaxyThreadPool.getExecutorService().execute(new Runnable() {
					@Override
					public void run() {
						delAliyunFiles(alifileidlist);
					}
				});
			}
		}
		
	}
	
	
	/**
	 * 实际拨款删除
	 * 1.删除   附件       file表、GrantFile中间表
	 * 2.删除事项                   GrantActual表
	 */
	@Override
	@Transactional
	public void deleteGrantActual(Long grantActualId) {
		GrantActual Actual = grantActualDao.selectById(grantActualId);
		List<Long> fileidlist = new ArrayList<Long>();
		
		if(Actual!=null && Actual.getFileNum()!=null){
			
			fileidlist = grantActualFileList(grantActualId);
			if(fileidlist!=null && !fileidlist.isEmpty()){
				SopFileBo fileQ = new SopFileBo();
				fileQ.setIds(fileidlist);
				fileDao.delete(fileQ);    // 删除 file、表
				
				GrantFile dfQ = new GrantFile();
				//dfQ.setTarget(Constants.ACTUAL_TARGET);
				//dfQ.setTid(grantActualId);
				dfQ.setFileIds(fileidlist);
				grantFileDao.delete(dfQ); // 删除 中间表
			}
		}
		
		grantActualDao.deleteById(grantActualId);  // 删除 实际拨款
		
		final List<Long> alifileidlist = fileidlist;
		if(alifileidlist!=null && !alifileidlist.isEmpty()){  // 删除 阿里云 文件
			GalaxyThreadPool.getExecutorService().execute(new Runnable() {
				@Override
				public void run() {
					delAliyunFiles(alifileidlist);
				}
			});
		}
	}

	@Override
	public List<SopDownLoad> queryActualDownFiles(Long id) {
		List<SopDownLoad> sopDownLoadList = new ArrayList<SopDownLoad>();
		List<SopFile> files = new ArrayList<SopFile>();
		
		List<Long> fileids = grantActualFileList(id);
		if(fileids != null && !fileids.isEmpty()){
			files = getFilesByIds(fileids);
		}

		if(files != null && files.size() > 0){
			for(SopFile file:files){
				SopDownLoad downloadEntity = new SopDownLoad();
				downloadEntity.setFileName(file.getFileName());
				downloadEntity.setFileSuffix("." + file.getFileSuffix());
				downloadEntity.setFileSize(file.getFileLength());
				downloadEntity.setFileKey(file.getFileKey());
				sopDownLoadList.add(downloadEntity);
			}
		}
		return sopDownLoadList;
	}

	@Override
	public List<GrantActual> selectSumActualByPid(Long projctId) {
		
		// TODO Auto-generated method stub
		return grantActualDao.selectSumActualByPid(projctId);
	}
	
	
	
	
	
	
}
