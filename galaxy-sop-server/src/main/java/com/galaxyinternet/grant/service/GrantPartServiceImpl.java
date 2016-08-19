package com.galaxyinternet.grant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.GrantFileDao;
import com.galaxyinternet.dao.GrantPartDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.GrantFile;
import com.galaxyinternet.model.GrantPart;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.model.touhou.DeliveryFile;
import com.galaxyinternet.service.GrantPartService;
import com.galaxyinternet.service.UserService;

@Service("com.galaxyinternet.grant.GrantPartService")
public class GrantPartServiceImpl extends BaseServiceImpl<GrantPart> implements GrantPartService {
	
	@Autowired
	private GrantPartDao grantPartDao;
	
	@Autowired
	private GrantFileDao grantFileDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private SopFileDao fileDao;
	
	@Autowired
	private UserService userService;
	
	@Override
	protected BaseDao<GrantPart, Long> getBaseDao() {
		return this.grantPartDao;
	}

	@Override
	public double calculateBelongToActualMoney(Long partId) {
		return grantPartDao.sumBelongToActualMoney(partId);
	}

	@Override
	public List<GrantPart> selectHasActualMoney(GrantPart part) {
		return grantPartDao.selectHasActualMoney(part);
	}

	@Transactional
	public void insertGrantPart(GrantPart grantPart) {
		// TODO Auto-generated method stub
		grantPart.setFileNum((byte) 0);
		Byte fnum = null;
		Long grantid = null;
		List<GrantFile> dfileIn = new ArrayList<GrantFile>();
		List<SopFile> sopfiles = grantPart.getFiles();
		
		if(sopfiles!=null && !sopfiles.isEmpty()){
			fnum = (byte) sopfiles.size();
			grantPart.setFileNum(fnum);
		}
		grantid = grantPartDao.insert(grantPart);   //拨款信息
		
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

}
