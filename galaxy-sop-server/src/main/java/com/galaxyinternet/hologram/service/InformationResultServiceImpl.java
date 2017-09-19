package com.galaxyinternet.hologram.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.dao.hologram.InformationResultDao;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.model.hologram.InformationTitleRelate;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.hologram.InformationDictionaryService;
import com.galaxyinternet.service.hologram.InformationResultService;
import com.galaxyinternet.service.hologram.InformationTitleRelateService;

@Service("informationResultService")
public class InformationResultServiceImpl extends BaseServiceImpl<InformationResult> implements InformationResultService{

	@Autowired
	private InformationResultDao informationResultDao;
	@Autowired
	private InformationDictionaryService informationDictionaryService;
	
/*	@Autowired
	private InformationTitleRelateService informationTitleRelateService;
	@Autowired
	private Cache cache;*/
	@Override
	protected BaseDao<InformationResult, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.informationResultDao;
	}

	@Override
	public int upResultByMeeting(MeetingRecord meetingRecord) {
		// TODO Auto-generated method stub
		//缓存获取数据字典对应值
		//Map<String,Dict> parentDictMap = new HashMap<String,Dict>();
		//添加会议类型不同，影响报告里面的相应的结果值
		//事业部意见 titleId：1111
		//立项委员会意见 titleId：1113
		//投决会意见 titleId：1114
	/*	InformationTitleRelate informationTitleRelate=new InformationTitleRelate();
		informationTitleRelate.setParentId((long)7208);
		informationTitleRelate.setReportType(4);
		List<InformationTitleRelate> queryList = informationTitleRelateService.queryList(informationTitleRelate);
		for(InformationTitleRelate info:queryList){
			info
		}*/
		InformationResult result=new InformationResult();
		InformationResult selectById=new InformationResult();
		selectById.setProjectId(meetingRecord.getProjectId().toString());
		String contentChoose="";
		switch(meetingRecord.getMeetingType()){
	       case "meetingType:1":
	    	   if(meetingRecord.getMeetingResult().equals("meeting1Result:1")){
	    		   contentChoose="1142";
	    	   }else if(meetingRecord.getMeetingResult().equals("meeting1Result:2")){
	    		   contentChoose="1143";
	    	   }else if(meetingRecord.getMeetingResult().equals("meeting1Result:3")){
	    		   contentChoose="1144";
	    	   }else if(meetingRecord.getMeetingResult().equals("meeting1Result:4")){
	    		   contentChoose="1145";
	    	   }
	    	   selectById.setTitleId("1111");
	    	   result.setContentChoose(contentChoose);
	    	    break;
	       case "meetingType:3":
	    	   if(meetingRecord.getMeetingResult().equals("meeting3Result:2")){
	    		   contentChoose="1162";
	    	   }else if(meetingRecord.getMeetingResult().equals("meeting3Result:3")){
	    		   contentChoose="1163";
	    	   }else if(meetingRecord.getMeetingResult().equals("meeting3Result:1")){
	    		   contentChoose="1164";
	    	   }else if(meetingRecord.getMeetingResult().equals("meeting3Result:4")){
	    		   contentChoose="1165";
	    	   }else if(meetingRecord.getMeetingResult().equals("meeting3Result:5")){
	    		   contentChoose="1166";
	    	   }else if(meetingRecord.getMeetingResult().equals("meeting3Result:6")){
	    		   contentChoose="1167";
	    	   }
	    	   selectById.setTitleId("1113");
	    	   result.setContentChoose(contentChoose);
	    	    break;
	       case "meetingType:4":
	    	   if(meetingRecord.getMeetingResult().equals("meeting4Result:1")){
	    		   contentChoose="1173";
	    	   }else if(meetingRecord.getMeetingResult().equals("meeting4Result:2")){
	    		   contentChoose="1174";
	    	   }else if(meetingRecord.getMeetingResult().equals("meeting4Result:3")){
	    		   contentChoose="1177";
	    	   }
	    	   result.setContentChoose(contentChoose);
	    	   selectById.setTitleId("1114");
	    	    break;
	    	default :
	    		 break;
	}
		this.updateOrInsert(selectById,contentChoose);
		
		return 0;
	}
	@Override
	public void updateOrInsert(InformationResult selectById,String contentChoose ){
		User user = WebUtils.getUserFromSession();
		Long userId = user != null ? user.getId() : null;
		Long now = new Date().getTime();
		List<InformationResult> reList=new ArrayList<InformationResult>();
		InformationResult re=new InformationResult();
		if(!"".equals(contentChoose)){
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

}
