package com.galaxyinternet.touhou.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
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


@Service("com.galaxyinternet.touhou.service.DeliveryServiceImpl")
public class DeliveryServiceImpl extends BaseServiceImpl<Delivery> implements DeliveryService {

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
	
	
	@Override
	public Delivery selectDelivery(Long deliveryId) {
		Delivery delivery = deliveryDao.selectById(deliveryId);
		
		if(delivery!=null & delivery.getFileNum()!=null){
			DeliveryFile query = new DeliveryFile();
			query.setDeliveryId(deliveryId);
			List<DeliveryFile> dfilelist = deliveryFileDao.selectList(query); //事项 文件 关联
			
			if(dfilelist!=null && !dfilelist.isEmpty()){
				List<Long> fileidlist = new ArrayList<Long>();
				for(DeliveryFile adf : dfilelist){
					fileidlist.add(adf.getFileId());
				}
				SopFileBo sf = new SopFileBo();
				sf.setIds(fileidlist);
				List<SopFile> files = fileDao.selectList(sf);  //文件
				delivery.setFiles(files);
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
		deliveryDao.deleteById(deliverid);
		
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

