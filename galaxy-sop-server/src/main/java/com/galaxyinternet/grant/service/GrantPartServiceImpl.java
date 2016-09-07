package com.galaxyinternet.grant.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.GrantFileDao;
import com.galaxyinternet.dao.GrantPartDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.file.FileResult;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.thread.GalaxyThreadPool;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.GrantFile;
import com.galaxyinternet.model.GrantPart;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.GrantPartService;

@Service("com.galaxyinternet.grant.GrantPartService")
public class GrantPartServiceImpl extends BaseServiceImpl<GrantPart> implements GrantPartService {
	final Logger logger = LoggerFactory.getLogger(GrantPartServiceImpl.class);
	
	@Autowired
	private GrantPartDao grantPartDao;
	
	@Autowired
	private GrantFileDao grantFileDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private SopFileDao fileDao;
	
	@Override
	protected BaseDao<GrantPart, Long> getBaseDao() {
		return this.grantPartDao;
	}

	@Override
	public double calculateBelongToPartMoney(Long totalId) {
		return grantPartDao.sumBelongToPartMoney(totalId);
	}

	@Override
	public List<GrantPart> selectHasActualMoney(GrantPart part) {
		return grantPartDao.selectHasActualMoney(part);
	}

	@Transactional
	public void insertGrantPart(GrantPart grantPart) {
		grantPart.setFileNum((byte) 0);
		Byte fnum = null;
		Long grantid = null;
		List<GrantFile> dfileIn = new ArrayList<GrantFile>();
		List<SopFile> sopfiles = grantPart.getFiles();
		
		if(sopfiles!=null && !sopfiles.isEmpty()){
			fnum = (byte) sopfiles.size();
			grantPart.setFileNum(fnum);
		}
		grantid = grantPartDao.insert(grantPart); 
		
		if(sopfiles!=null && !sopfiles.isEmpty() && fnum !=null){
			Project project = projectDao.selectById(grantPart.getGrantTotal().getProjectId());
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
				df.setGrantId(grantid);
				df.setFileId(sopfile.getId());
				
				dfileIn.add(df);
			}
			grantFileDao.insertInBatch(dfileIn);
		}
	}

	public GrantPart selectGrantPart(Long partId) {
		GrantPart grantPart = grantPartDao.selectById(partId);
		if(grantPart!=null && grantPart.getFileNum() !=0){
			List<Long> fileidlist =  grantPartFileList(partId); //事项 文件 关联
			if(fileidlist!=null && !fileidlist.isEmpty()){
				List<SopFile> files = getFilesByIds(fileidlist);  //文件
				if(files!=null && !files.isEmpty()){
					grantPart.setFiles(files);
				}
			}
		}
		return grantPart;
	}
	
	/**
	 *	由 id  查询其  文件 ids
	 */
	public List<Long> grantPartFileList(Long partId) {
		List<Long> listFile = new ArrayList<Long>();
		GrantFile grantFile = new GrantFile();
		grantFile.setGrantId(partId);
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

	@Transactional
	public void upateGrantPart(GrantPart grantPart) {
		
		GrantPart oldGrantPart =  grantPartDao.selectById(grantPart.getId());
		if(oldGrantPart == null){
			throw new DaoException(String.format("upateGrantPart 数据错误: " + GSONUtil.toJson(oldGrantPart)));
		}
		List<SopFile> upFiles = grantPart.getFiles();   			//新存入 sopfile
		List<Long> oldHasFileIds = grantPart.getFileIds();       //原fileids 还保留的
		List<Long> toDelfileids = new ArrayList<Long>();
		
		Byte allNum = grantPart.getFileNum();
		Byte oldNum = oldGrantPart.getFileNum() == null ?0:oldGrantPart.getFileNum();
		Byte oldHasNum = (byte) (oldHasFileIds ==null?0:oldHasFileIds.size());
		Byte upNum = (byte) (upFiles == null?0:upFiles.size());
		
		//文件删除
		if(oldNum!=0){
			if(oldHasNum == 0){
				toDelfileids = grantPartFileList(grantPart.getId());
			}else if(oldNum.byteValue() != oldHasNum.byteValue()){
				List<Long> oldfileids = grantPartFileList(grantPart.getId());  //查询中间表， 取原 fileids
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
			grantFileDao.delete(dfQ); // 删除 中间表
		}
		
		//新文件上传
		if(allNum == null || allNum == 0){
			grantPart.setFileNum((byte) 0);
		}else{
			//grantPart.setFileNum(allNum);
			grantPart.setFileNum((byte) (oldHasNum+upNum));
			if(upNum != 0){ 
				List<GrantFile> dfileIn = new ArrayList<GrantFile>();
				Project project =  projectDao.selectById(grantPart.getGrantTotal().getProjectId());
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
					df.setGrantId(grantPart.getId());
					df.setFileId(sopfile.getId());
					dfileIn.add(df);
				}
				grantFileDao.insertInBatch(dfileIn);
			}
			/*else{
				if(StringUtils.isEmpty(toDelfileids)){
					grantPart.setFileNum(oldNum);
				}
			}*/
		}
		
		//更新交割事项
		grantPartDao.updateById(grantPart);
		
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

	@Transactional
	public void deleteGrantPart(Long grantPartId) {
		GrantPart part = grantPartDao.selectById(grantPartId);
		List<Long> fileidlist = new ArrayList<Long>();
		
		if(part!=null && part.getFileNum()!=null){
			
			fileidlist = grantPartFileList(grantPartId);
			
			if(fileidlist!=null && !fileidlist.isEmpty()){
				
				SopFileBo fileQ = new SopFileBo();
				fileQ.setIds(fileidlist);
				fileDao.delete(fileQ);    // 删除 file、表
				
				GrantFile dfQ = new GrantFile();
				dfQ.setFileIds(fileidlist);
				grantFileDao.delete(dfQ); // 删除 中间表
			}
		}
		
		grantPartDao.deleteById(grantPartId);  // 删除 
		
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
	

}


