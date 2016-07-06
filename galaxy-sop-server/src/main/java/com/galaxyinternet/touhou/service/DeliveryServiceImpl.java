package com.galaxyinternet.touhou.service;

import java.io.File;
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

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.bo.touhou.DeliveryBo;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.touhou.DeliveryDao;
import com.galaxyinternet.dao.touhou.DeliveryFileDao;
import com.galaxyinternet.framework.core.constants.SqlId;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.file.FileResult;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.model.touhou.DeliveryFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DeliveryService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.touhou.controller.DeliveryController;


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
	@Override
	public Delivery selectDelivery(Long deliveryId) {
		Delivery delivery = deliveryDao.selectById(deliveryId);
		if(delivery!=null & delivery.getFileNum()!=null){
			List<DeliveryFile> dfilelist = getDfileById(deliveryId); //事项 文件 关联
			
			if(dfilelist!=null && !dfilelist.isEmpty()){
				List<Long> fileidlist = new ArrayList<Long>();
				for(DeliveryFile adf : dfilelist){
					fileidlist.add(adf.getFileId());
				}
				List<SopFile> files = getFilesByIds(fileidlist);  //文件
				if(files!=null && !files.isEmpty()){
					delivery.setFiles(files);
				}
			}
		}
		return delivery;
	}

	
	@Override
	public Long insertDelivery(Delivery delivery) {
		Long id = deliveryDao.insert(delivery);
		return id;
	}

	
	@Override
	public Long updateDelivery(Delivery delivery) {
		int num = deliveryDao.updateById(delivery);
		if(num > 0){
			return delivery.getId();
		}else{
			throw new DaoException(String.format("updateDelivery 一条记录出错: " + GSONUtil.toJson(delivery)));
		}
		
	}


	@Override
	public void delDeliveryById(Long deliverid) {
		Delivery delivery = deliveryDao.selectById(deliverid);
		List<Long> fileidlist = new ArrayList<Long>();
		
		if(delivery!=null & delivery.getFileNum()!=null){
			List<DeliveryFile> dfilelist = getDfileById(deliverid); //事项 文件 关联
			
			if(dfilelist!=null && !dfilelist.isEmpty()){
				
				for(DeliveryFile adf : dfilelist){
					fileidlist.add(adf.getFileId());
				}
				SopFileBo fileQ = new SopFileBo();
				fileQ.setIds(fileidlist);
				fileDao.delete(fileQ);    // 删除 file、表
				
				DeliveryFile dfQ = new DeliveryFile();
				dfQ.setFileIds(fileidlist);
				deliveryFileDao.delete(dfQ); // 删除 中间表
			}
			
			deliveryDao.deleteById(deliverid);  // 删除  事项
		}
		
		if(fileidlist!=null && !fileidlist.isEmpty()){  // 删除 阿里云 文件
			delAliyunFiles(fileidlist);
		}
	}

	@Override
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

