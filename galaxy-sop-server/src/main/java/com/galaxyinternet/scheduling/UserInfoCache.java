package com.galaxyinternet.scheduling;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.galaxyinternet.common.dictEnum.RoleEnum;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.user.UserRole;
import com.galaxyinternet.service.UserRoleService;

@Component("userInfoTask")
public class UserInfoCache extends BaseGalaxyTask{
	private final static Logger logger = LoggerFactory.getLogger(UserInfoCache.class);
	@Autowired
	private UserRoleService userRoleService;

	@Override
	protected void executeInteral() throws BusinessException {
		// TODO Auto-generated method stub
		try{
			logger.info("------------初始化角色信息内存开始start-----------");
			PushUsersCache.bindMap.clear();
			List<Long> roleIdList;
			UserRole u = new UserRole();
			for(RoleEnum role : RoleEnum.values()){
				String roleNames = role.getName();
				String roleIds = role.getCode();
				roleIdList = roleList(roleIds);
			    //初始化内存
				u.setRoleIdList(roleIdList);
				List<UserRole> urlist123 = userRoleService.queryList(u);
				List<String> userIdList = new ArrayList<String>();
				if(!StringUtils.isEmpty(urlist123)){
					for(UserRole ur:urlist123){
						userIdList.add(ur.getUserId().toString());
					}
					PushUsersCache.setCache(roleNames, userIdList);
				}
			}
			logger.info("------------初始化角色信息内存结束end-----------");
		}catch(Exception e){
			logger.error("------------初始化角色信息内存错误-----------"+e.getMessage());
		}
		
	}
	
	public List<Long> roleList(String roleids){
		List<Long> roleIdList = new ArrayList<Long>();
		if(!StringUtils.isEmpty(roleids)){
			String []ids = roleids.split(",");
			for(String id:ids){
				roleIdList.add(Long.valueOf(id));
			}
		}
		return roleIdList;
	}
	
}
