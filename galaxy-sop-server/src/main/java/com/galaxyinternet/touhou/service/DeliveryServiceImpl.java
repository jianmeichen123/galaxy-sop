package com.galaxyinternet.touhou.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.bo.touhou.DeliveryBo;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.touhou.DeliveryDao;
import com.galaxyinternet.dao.touhou.DeliveryFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.file.FileResult;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.thread.GalaxyThreadPool;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.model.touhou.DeliveryFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DeliveryService;
import com.galaxyinternet.service.UserService;


@Service("com.galaxyinternet.touhou.service.DeliveryServiceImpl")
public class DeliveryServiceImpl extends BaseServiceImpl<Delivery> implements DeliveryService {
	final Logger logger = LoggerFactory.getLogger(DeliveryServiceImpl.class);
	@Autowired
	private DeliveryDao deliveryDao;
	
	@Autowired
	private DeliveryFileDao deliveryFileDao;
	
	@Autowired
	private SopFileDao fileDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	protected BaseDao<Delivery, Long> getBaseDao() {
		return this.deliveryDao;
	}
	
	
	
	/**
	 * 由 事项 id 查询中间表数据
	 */
	public List<DeliveryFile> getDfileById(Long deliveryId){
		DeliveryFile query = new DeliveryFile();
		query.setDeliveryId(deliveryId);
		List<DeliveryFile> dfilelist = deliveryFileDao.selectList(query); //事项 文件 关联
		return dfilelist;
	}
	
	/**
	 *	由 事项id  查询其  文件 ids
	 */
	@Override
	public List<Long> deliveryFileList(Long deliverid) {
		List<Long> listFile = new ArrayList<Long>();
		DeliveryFile deliveryFile = new DeliveryFile();
		deliveryFile.setDeliveryId(deliverid);
		List<DeliveryFile> dfList = deliveryFileDao.selectList(deliveryFile);
		if(dfList != null && dfList.size() > 0){
			for(DeliveryFile df:dfList){
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
	 * 事项查询    查询中间表  - fileids - file
	 */
	@Transactional
	public Delivery selectDelivery(Long deliveryId) {
		Delivery delivery = deliveryDao.selectById(deliveryId);
		
		if(delivery!=null && delivery.getFileNum()!=null){
			
			List<Long> fileidlist =  deliveryFileList(deliveryId); //事项 文件 关联
			
			if(fileidlist!=null && !fileidlist.isEmpty()){
				List<SopFile> files = getFilesByIds(fileidlist);  //文件
				if(files!=null && !files.isEmpty()){
					delivery.setFiles(files);
				}
			}
		}
		return delivery;
	}

	/**
	 * 添加事项     file - 中间表  - 交割事项
	 */
	@Transactional
	public Long insertDelivery(Delivery delivery) {
		delivery.setFileNum((byte) 0);
		
		Byte fnum = null;
		Long delid = null;
		List<DeliveryFile> dfileIn = new ArrayList<DeliveryFile>();
		List<SopFile> sopfiles = delivery.getFiles();
		
		if(sopfiles!=null && !sopfiles.isEmpty()){
			fnum = (byte) sopfiles.size();
			delivery.setFileNum(fnum);
		}
		delid = deliveryDao.insert(delivery);   //交割事项
		
		if(sopfiles!=null && !sopfiles.isEmpty() && fnum !=null){
			Project project = projectDao.selectById(delivery.getProjectId());
			for(SopFile sopfile:sopfiles){
				sopfile.setProjectId(project.getId());
				sopfile.setProjectProgress(project.getProjectProgress());
				sopfile.setCareerLine(project.getProjectDepartid());
				sopfile.setFileStatus(DictEnum.fileStatus.已上传.getCode());
				sopfile.setFileUid(project.getCreateUid());
				/*file.setFileLength(sopfile.getFileLength());
				file.setFileKey(sopfile.getFileKey());
				file.setBucketName(sopfile.getBucketName());
				file.setFileName(sopfile.getFileName());
				file.setFileSuffix(sopfile.getFileSuffix());*/
			}
			fileDao.insertInBatch(sopfiles);
			
			for(SopFile sopfile:sopfiles){
				DeliveryFile df = new DeliveryFile();
				df.setDeliveryId(delid);
				df.setFileId(sopfile.getId());
				
				dfileIn.add(df);
			}
			deliveryFileDao.insertInBatch(dfileIn);
		}
		
		return delid;
	}

	
	/**
	 * 编辑事项     file - 中间表  - 交割事项
	 */
	@Transactional
	public Long updateDelivery(Delivery delivery) {
		
		Delivery oldDelivery =  deliveryDao.selectById(delivery.getId());
		if(oldDelivery == null){
			throw new DaoException(String.format("updateDelivery 数据错误: " + GSONUtil.toJson(delivery)));
		}
		
		List<SopFile> upFiles = delivery.getFiles();   			//新存入 sopfile
		List<Long> oldHasFileIds = delivery.getFileIds();       //原fileids 还保留的
		List<Long> toDelfileids = new ArrayList<Long>();
		
		Byte allNum = delivery.getFileNum();
		Byte oldNum = oldDelivery.getFileNum()==null?0:oldDelivery.getFileNum();
		Byte oldHasNum = (byte) (oldHasFileIds ==null?0:oldHasFileIds.size());
		Byte upNum = (byte) (upFiles == null?0:upFiles.size());
		
		//文件删除
		if(oldNum!=0){
			if(oldHasNum == 0){
				toDelfileids = deliveryFileList(delivery.getId());
			}else if(oldNum.byteValue() != oldHasNum.byteValue()){
				List<Long> oldfileids = deliveryFileList(delivery.getId());  //查询中间表， 取原 fileids
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
			
			DeliveryFile dfQ = new DeliveryFile();
			dfQ.setFileIds(toDelfileids);
			deliveryFileDao.delete(dfQ); // 删除 中间表
		}
		
		//新文件上传
		if(allNum == null || allNum == 0){
			//delivery.setFileNum(null);
			delivery.setFileNum((byte) 0);
		}else{
			delivery.setFileNum((byte) (oldHasNum+upNum));

			if(upNum != 0){ 
				List<DeliveryFile> dfileIn = new ArrayList<DeliveryFile>();
				
				Project project = projectDao.selectById(delivery.getProjectId());
				for(SopFile sopfile:upFiles){
					sopfile.setProjectId(project.getId());
					sopfile.setProjectProgress(project.getProjectProgress());
					sopfile.setCareerLine(project.getProjectDepartid());
					sopfile.setFileStatus(DictEnum.fileStatus.已上传.getCode());
					sopfile.setFileUid(project.getCreateUid());
				}
				fileDao.insertInBatch(upFiles);
				
				for(SopFile sopfile:upFiles){
					DeliveryFile df = new DeliveryFile();
					df.setDeliveryId(delivery.getId());
					df.setFileId(sopfile.getId());
					
					dfileIn.add(df);
				}
				deliveryFileDao.insertInBatch(dfileIn);
			}
			
			/*else{
				if(StringUtils.isEmpty(toDelfileids)){
					delivery.setFileNum(oldNum);
				}
			}*/
		}
		
		//更新交割事项
		int num = deliveryDao.updateById(delivery);
		
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
		
		if(num > 0){
			return delivery.getId();
		}else{
			throw new DaoException(String.format("updateDelivery 一条记录出错: " + GSONUtil.toJson(delivery)));
		}
		
		
	}


	@Transactional
	public void delDeliveryById(Long deliverid) {
		Delivery delivery = deliveryDao.selectById(deliverid);
		List<Long> fileidlist = new ArrayList<Long>();
		
		if(delivery!=null && delivery.getFileNum()!=null){
			
			fileidlist = deliveryFileList(deliverid);
			
			if(fileidlist!=null && !fileidlist.isEmpty()){
				
				SopFileBo fileQ = new SopFileBo();
				fileQ.setIds(fileidlist);
				fileDao.delete(fileQ);    // 删除 file、表
				
				DeliveryFile dfQ = new DeliveryFile();
				dfQ.setFileIds(fileidlist);
				deliveryFileDao.delete(dfQ); // 删除 中间表
			}
		}
		
		deliveryDao.deleteById(deliverid);  // 删除  事项
		
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

	
	@Transactional
	public Page<DeliveryBo> queryDeliveryPageList(DeliveryBo query, PageRequest pageRequest) {
		Page<DeliveryBo> page = deliveryDao.selectDeliveryPageList(query, pageRequest);
		List<DeliveryBo> list = null;
		Map<Long,String> idUnameMap = new HashMap<Long,String>();
		
		if(page!=null && page.getContent()!=null && !page.getContent().isEmpty()){
			list = page.getContent();
			
			Set<Long> uidset = new HashSet<Long>();
			for(DeliveryBo dbo : list){
				uidset.add(dbo.getUpdatedUid()==null?dbo.getCreatedUid():dbo.getUpdatedUid());
			}
			if(uidset!=null && !uidset.isEmpty()){
				User user = new User();
				user.setIds(new ArrayList<Long>(uidset));
				List<User> userlist = userService.queryList(user);
				if(userlist!=null && !userlist.isEmpty()){
					for(User u : userlist){
						idUnameMap.put(u.getId(), u.getRealName());
					}
				}
			}
			
			for(DeliveryBo dbo : list){
				dbo.setEndByUname(idUnameMap.get(dbo.getUpdatedUid()==null?dbo.getCreatedUid():dbo.getUpdatedUid()));
				if(dbo.getUpdatedTime()==null){
					dbo.setUpdatedTime(dbo.getCreatedTime());
				}
			}
		}else{
			page = new Page<DeliveryBo>(new ArrayList<DeliveryBo>() , pageRequest, 0l);
		}
		
		return page;
	}


	




}

