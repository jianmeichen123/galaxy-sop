package com.galaxyinternet.hologram.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.dictEnum.DictEnum.LXHResult;
import com.galaxyinternet.common.dictEnum.DictEnum.NBPSResult;
import com.galaxyinternet.common.dictEnum.DictEnum.TJHResult;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.dictEnum.DictEnum.titleIdResult;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.dao.hologram.InformationResultDao;
import com.galaxyinternet.dao.project.MeetingRecordDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.common.Node;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.hologram.InformationResultService;

@Service("informationResultService")
public class InformationResultServiceImpl extends BaseServiceImpl<InformationResult> implements InformationResultService{

	@Autowired
	private InformationResultDao informationResultDao;
	@Autowired
	private MeetingRecordDao meetingRecordDao;
	
/*	@Autowired
	private InformationTitleRelateService informationTitleRelateService;
	@Autowired
	private Cache cache;*/
	@Override
	protected BaseDao<InformationResult, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.informationResultDao;
	}
	/**
	 * @author jianemichen 
	 * 新增会议根据不同会议类型将会议结果不同数据库
	 * @serialData 2017-09-21 
	 */
	@Override
	public int upResultByMeeting(MeetingRecord meetingRecord) {
		// TODO Auto-generated method stub
		InformationResult result=new InformationResult();
		InformationResult selectById=new InformationResult();
		selectById.setProjectId(meetingRecord.getProjectId().toString());
		String meetingResult = this.meetingResult(meetingRecord.getProjectId(), null,meetingRecord.getMeetingType());
		if(!"".equals(meetingResult)){
			meetingRecord.setMeetingResult(meetingResult);
		}
		String contentChoose="";
		switch(meetingRecord.getMeetingType()){
	       case "meetingType:1":
	    	   if(meetingRecord.getMeetingResult().equals(NBPSResult.ST.getCode())){
	    		   contentChoose=NBPSResult.ST.getConnect();
	    	   }else if(meetingRecord.getMeetingResult().equals(NBPSResult.TZ.getCode())){
	    		   contentChoose=NBPSResult.TZ.getConnect();
	    	   }else if(meetingRecord.getMeetingResult().equals(NBPSResult.GW.getCode())){
	    		   contentChoose=NBPSResult.GW.getConnect();
	    	   }else if(meetingRecord.getMeetingResult().equals(NBPSResult.FJ.getCode())){
	    		   contentChoose=NBPSResult.FJ.getConnect();
	    	   }
	    	   selectById.setTitleId(titleIdResult.NP.getCode());
	    	   result.setContentChoose(contentChoose);
	    	    break;
	       case "meetingType:3":
	    	   if(meetingRecord.getMeetingResult().equals(LXHResult.ST.getCode())){
	    		   contentChoose=LXHResult.ST.getConnect();
	    	   }else if(meetingRecord.getMeetingResult().equals(LXHResult.TZ.getCode())){
	    		   contentChoose=LXHResult.TZ.getConnect();
	    	   }else if(meetingRecord.getMeetingResult().equals(LXHResult.BCCL.getCode())){
	    		   contentChoose=LXHResult.BCCL.getConnect();
	    	   }else if(meetingRecord.getMeetingResult().equals(LXHResult.GW.getCode())){
	    		   contentChoose=LXHResult.GW.getConnect();
	    	   }else if(meetingRecord.getMeetingResult().equals(LXHResult.ZX.getCode())){
	    		   contentChoose=LXHResult.ZX.getConnect();
	    	   }else if(meetingRecord.getMeetingResult().equals(LXHResult.FJ.getCode())){
	    		   contentChoose=LXHResult.FJ.getConnect();
	    	   }
	    	   selectById.setTitleId(titleIdResult.LX.getCode());
	    	   result.setContentChoose(contentChoose);
	    	    break;
	       case "meetingType:4":
	    	   if(meetingRecord.getMeetingResult().equals(TJHResult.TZ.getCode())){
	    		   contentChoose=TJHResult.TZ.getConnect();
	    	   }else if(meetingRecord.getMeetingResult().equals(TJHResult.BCCL.getCode())){
	    		   contentChoose=TJHResult.BCCL.getConnect();
	    	   }else if(meetingRecord.getMeetingResult().equals(TJHResult.FJ.getCode())){
	    		   contentChoose=TJHResult.FJ.getConnect();
	    	   }
	    	   result.setContentChoose(contentChoose);
	    	   selectById.setTitleId(titleIdResult.TJ.getCode());
	    	    break;
	    	default :
	    		 break;
	}
		this.updateOrInsert(selectById,contentChoose);
		
		return 1;
	}
	
	/***
	 * @author jianemichen
	 * 点击“否决项目”按钮 结果同步到报告相应的字段
	 * @serialData 2017-09-21
	 */
	@Override
	public void updateRejestResut(Project project ){
		String progress=project.getProjectProgress();
		String titleId="";
		String contentChoose="";
		if(null!=progress){
			if(progress.equals(projectProgress.内部评审.getCode())){
				titleId=titleIdResult.NP.getCode();
				contentChoose=NBPSResult.FJ.getConnect();
			}else if(progress.equals(projectProgress.立项会.getCode())){
				titleId=titleIdResult.LX.getCode();
				contentChoose=LXHResult.FJ.getConnect();
			}else if(progress.equals(projectProgress.投资决策会.getCode())){
				titleId=titleIdResult.TJ.getCode();
				contentChoose=TJHResult.FJ.getConnect();
			}
		}
		InformationResult selectById=new InformationResult();
		selectById.setProjectId(project.getId().toString());
		selectById.setTitleId(titleId);
		//数据库处理新增或者修改处理
		this.updateOrInsert(selectById,contentChoose);
	}
	/**
	 * @author jianemichen
	 * 同步数据编辑新增处理
	 * @serialData 2017-09-21
	 */
	@Override
	public void updateOrInsert(InformationResult selectById,String contentChoose ){
		User user = WebUtils.getUserFromSession();
		Long userId = user != null ? user.getId() : null;
		Long now = new Date().getTime();
		List<InformationResult> reList=new ArrayList<InformationResult>();
		InformationResult re=new InformationResult();
		if(!"".equals(contentChoose)){
			selectById.setNotNull(true);
			reList = informationResultDao.selectList(selectById);
			if(null!=reList&&!reList.isEmpty()){
				re=reList.get(0);
				re.setUpdatedTime(now);
				re.setUpdateId(userId.toString());
				re.setContentChoose(contentChoose);
				re.setIsValid("0");
				informationResultDao.updateById(re);
			}else{
				selectById.setContentChoose(contentChoose);
				selectById.setCreatedTime(now);
				selectById.setCreateId(userId.toString());
				informationResultDao.insert(selectById);
			}
		}
	}
	
	/**
	 * 项目流程会议添加时候判断同步报告相应字段的值
	 * @param projectId
	 * @param passResult
	 * @author jianmeichen
	 * @serialData 
	 *  return
	 */

	public String meetingResult(Long projectId,String passResult,String meetingType){
		
		MeetingRecordBo meetingBo=new MeetingRecordBo();
		
		MeetingRecord meeting=new MeetingRecord();
		
		meetingBo.setProjectId(projectId);
		
		String result="";
		
		if(null!=passResult&&!"".equals(passResult)){
			
			if(passResult.equals("NP")){
				
				meetingBo.setPassNPFlag("yes");
				
			}else if(passResult.equals("LX")){
				
				meetingBo.setPassLXFlag("yes");
				
			}else{
				meetingBo.setMeetingResult(passResult);
			}
		}
		meetingBo.setProperty("meeting_date");
		meetingBo.setDirection("desc");
		meetingBo.setMeetingType(meetingType);
		List<MeetingRecord> selectList = meetingRecordDao.selectList(meetingBo);
		
		if(null!=selectList&&!selectList.isEmpty()){
			
			meeting=selectList.get(0);
			
			result=meeting.getMeetingResult();
		}
		return result;
	}
	
	
	@Override
	public List<Node> selectResults(InformationResult ir) {
		// TODO Auto-generated method stub
		List<InformationResult> list = informationResultDao.selectResultByRelate(ir);
		List<Node> nodeList = new ArrayList<Node>();
		if(list != null && list.size() > 0){
			for(InformationResult entity : list){
				String value = "";
				if(entity.getType() != null){
					value = entity.getType() == 19 ? entity.getContentDescribe1() : entity.getContentChoose();
				}
				Node node = new Node(entity.getId(),entity.getResultId() == null ? null : entity.getResultId()
						,entity.getTitleId() == null ? null : Long.valueOf(entity.getTitleId()),
						entity.getValueName(),value,Long.valueOf(entity.getParentId()),entity.getType());
				nodeList.add(node);
			}
		}
		return nodeList;
	}
	
	
	
}
