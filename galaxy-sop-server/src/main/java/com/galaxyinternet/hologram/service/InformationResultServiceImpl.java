package com.galaxyinternet.hologram.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.common.dictEnum.DictEnum.LXHResult;
import com.galaxyinternet.common.dictEnum.DictEnum.NBPSResult;
import com.galaxyinternet.common.dictEnum.DictEnum.SWTPResult;
import com.galaxyinternet.common.dictEnum.DictEnum.TJHResult;
import com.galaxyinternet.common.dictEnum.DictEnum.titleIdResult;
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
		InformationResult result=new InformationResult();
		InformationResult selectById=new InformationResult();
		selectById.setProjectId(meetingRecord.getProjectId().toString());
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
