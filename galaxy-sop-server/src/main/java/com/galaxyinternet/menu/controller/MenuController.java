package com.galaxyinternet.menu.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.MenusBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.user.Menus;

@Controller
@RequestMapping("/galaxy/LeftMenu")
public class MenuController extends BaseControllerImpl<Menus, MenusBo> {
	
	final Logger logger = LoggerFactory.getLogger(MenuController.class);
//	@Autowired
//	private UserService userService;

	/*@Autowired
	UserRepository userRepository;*/
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@ResponseBody
	@RequestMapping(value = "/menu/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Menus> menu(
			@PathVariable String key
			){
			ResponseData<Menus> responseBody = new ResponseData<Menus>();			
//			User user = userRepository.get(key);
//			Long id = user.getRoleId();
		
//			Role role = roleService.queryById(1L);
			Long id = 16L;
			List<Menus> str = new ArrayList<Menus>();
			
			if( id == 1L){
				//roleid为一的时候是dsz
				Menus menu = new Menus();
				menu.setUrl("ssss.ss");
				menu.setMenuName("工作桌面");									
				str.add(menu);	
				Menus menu1 = new Menus();
				menu1.setUrl("sssss.ss");
				menu1.setMenuName("消息提醒");				
				str.add(menu1);
				Menus menu2 = new Menus();
				menu2.setUrl("sssss.ss");
				menu2.setMenuName("项目查询");				
				str.add(menu2);
				Menus menu3 = new Menus();
				menu3.setUrl("sssss.ss");
				menu3.setMenuName("数据简报");				
				str.add(menu3);
				Menus menu4 = new Menus();
				menu4.setUrl("sssss.ss");
				menu4.setMenuName("项目分析");
				str.add(menu4);
				Menus menu5 = new Menus();
				menu5.setUrl("sssss.ss");
				menu5.setMenuName("绩效考核");		
				str.add(menu5);
				Menus menu7= new Menus();
				menu7.setUrl("sssss.ss");
				menu7.setMenuName("绩效考核");				
				str.add(menu7);
				System.out.println("dsz");
			}else if ( id == 2L) {	//id=2 CEO						
				Menus menu1 = new Menus();
				menu1.setUrl("sssss.ss");
				menu1.setMenuName("工作桌面");				
				str.add(menu1);
				Menus menu2 = new Menus();
				menu2.setUrl("sssss.ss");
				menu2.setMenuName("消息提醒");				
				str.add(menu2);
				Menus menu3 = new Menus();
				menu3.setUrl("sssss.ss");
				menu3.setMenuName("项目查询");				
				str.add(menu3);
				Menus menu4 = new Menus();
				menu4.setUrl("sssss.ss");
				menu4.setMenuName("数据简报");				
				str.add(menu4);
				Menus menu5 = new Menus();
				menu5.setUrl("sssss.ss");
				menu5.setMenuName("项目分析");				
				str.add(menu5);
				Menus menu6 = new Menus();
				menu6.setUrl("sssss.ss");
				menu6.setMenuName("绩效考核");				
				str.add(menu6);
				Menus menu7= new Menus();
				menu7.setUrl("sssss.ss");
				menu7.setMenuName("绩效考核");				
				str.add(menu7);
				System.out.println("CEO");
				
			}else if( id==3L){//id=3 合伙人
				Menus menu = new Menus();
				menu.setUrl("ssss.ss");
				menu.setMenuName("工作桌面");									
				str.add(menu);	
				Menus menu1 = new Menus();
				menu1.setUrl("sssss.ss");
				menu1.setMenuName("消息提醒");				
				str.add(menu1);
				Menus menu2 = new Menus();
				menu2.setUrl("sssss.ss");
				menu2.setMenuName("项目查询");				
				str.add(menu2);
				Menus menu3 = new Menus();
				menu3.setUrl("sssss.ss");
				menu3.setMenuName("数据简报");				
				str.add(menu3);
				Menus menu4 = new Menus();
				menu4.setUrl("sssss.ss");
				menu4.setMenuName("项目分析");
				str.add(menu4);
				Menus menu5 = new Menus();
				menu5.setUrl("sssss.ss");
				menu5.setMenuName("绩效考核");		
				str.add(menu5);
				Menus menu7= new Menus();
				menu7.setUrl("sssss.ss");
				menu7.setMenuName("绩效考核");				
				str.add(menu7);
				System.out.println("hhr");
			}else if(id == 4L){//投资经理
				Menus menu = new Menus();
				menu.setUrl("ssss.ss");
				menu.setMenuName("工作桌面");									
				str.add(menu);	
				Menus menu1 = new Menus();
				menu1.setUrl("sssss.ss");
				menu1.setMenuName("消息提醒");				
				str.add(menu1);
				Menus menu11= new Menus();
				menu11.setUrl("sssss.ss");
				menu11.setMenuName("添加项目");
				str.add(menu11);
				Menus menu2 = new Menus();
				menu2.setUrl("sssss.ss");
				menu2.setMenuName("我的项目");				
				str.add(menu2);
				Menus menu3 = new Menus();
				menu3.setUrl("sssss.ss");
				menu3.setMenuName("访谈跟进");				
				str.add(menu3);
				Menus menu4 = new Menus();
				menu4.setUrl("sssss.ss");
				menu4.setMenuName("会议纪要");
				str.add(menu4);
				Menus menu5 = new Menus();
				menu5.setUrl("sssss.ss");
				menu5.setMenuName("档案管理");		
				str.add(menu5);
				Menus menu7= new Menus();
				menu7.setUrl("sssss.ss");
				menu7.setMenuName("模板管理");				
				str.add(menu7);				
				Menus menu8= new Menus();
				menu8.setUrl("sssss.ss");
				menu8.setMenuName("数据报表");				
				str.add(menu8);				
				System.out.println("投资经理");
				
			}else if(id == 5L){//投后负责人
				
			}else if(id == 6L){//项目运营负责人
				
			}else if(id == 7L){//HR总监				
				Menus menu = new Menus();
				menu.setUrl("ssss.ss");
				menu.setMenuName("工作桌面");									
				str.add(menu);	
				Menus menu1 = new Menus();
				menu1.setUrl("sssss.ss");
				menu1.setMenuName("消息提醒");				
				str.add(menu1);
				Menus menu2 = new Menus();
				menu2.setUrl("sssss.ss");
				menu2.setMenuName("待办任务");				
				str.add(menu2);
				Menus menu3 = new Menus();
				menu3.setUrl("sssss.ss");
				menu3.setMenuName("完善简历");				
				str.add(menu3);
				Menus menu5 = new Menus();
				menu5.setUrl("sssss.ss");
				menu5.setMenuName("尽职报告");		
				str.add(menu5);
				Menus menu7= new Menus();
				menu7.setUrl("sssss.ss");
				menu7.setMenuName("模版管理");				
				str.add(menu7);
				Menus menu4 = new Menus();
				menu4.setUrl("sssss.ss");
				menu4.setMenuName("档案管理");
				str.add(menu4);
				System.out.println("HRZJ");				
			}else if(id == 8L){//hr经理
				Menus menu = new Menus();
				menu.setUrl("ssss.ss");
				menu.setMenuName("工作桌面");									
				str.add(menu);	
				Menus menu1 = new Menus();
				menu1.setUrl("sssss.ss");
				menu1.setMenuName("消息提醒");				
				str.add(menu1);
				Menus menu2 = new Menus();
				menu2.setUrl("sssss.ss");
				menu2.setMenuName("待办任务");				
				str.add(menu2);
				Menus menu3 = new Menus();
				menu3.setUrl("sssss.ss");
				menu3.setMenuName("完善简历");				
				str.add(menu3);
				Menus menu5 = new Menus();
				menu5.setUrl("sssss.ss");
				menu5.setMenuName("尽职报告");		
				str.add(menu5);
				Menus menu7= new Menus();
				menu7.setUrl("sssss.ss");
				menu7.setMenuName("模版管理");				
				str.add(menu7);
				Menus menu4 = new Menus();
				menu4.setUrl("sssss.ss");
				menu4.setMenuName("档案管理");
				str.add(menu4);
				System.out.println("HRJL");	
			}else if(id == 9L){//法务总监
				Menus menu = new Menus();
				menu.setUrl("ssss.ss");
				menu.setMenuName("工作桌面");									
				str.add(menu);	
				Menus menu1 = new Menus();
				menu1.setUrl("sssss.ss");
				menu1.setMenuName("消息提醒");				
				str.add(menu1);
				Menus menu2 = new Menus();
				menu2.setUrl("sssss.ss");
				menu2.setMenuName("待办任务");				
				str.add(menu2);
				Menus menu3 = new Menus();
				menu3.setUrl("sssss.ss");
				menu3.setMenuName("尽职报告");				
				str.add(menu3);
				Menus menu5 = new Menus();
				menu5.setUrl("sssss.ss");
				menu5.setMenuName("股权交割");		
				str.add(menu5);
				Menus menu7= new Menus();
				menu7.setUrl("sssss.ss");
				menu7.setMenuName("档案模板");				
				str.add(menu7);
				Menus menu4 = new Menus();
				menu4.setUrl("sssss.ss");
				menu4.setMenuName("档案查询");
				str.add(menu4);
				System.out.println("FWZJ");	
			}else if(id == 10L){//法务经理
				Menus menu = new Menus();
				menu.setUrl("ssss.ss");
				menu.setMenuName("工作桌面");									
				str.add(menu);	
				Menus menu1 = new Menus();
				menu1.setUrl("sssss.ss");
				menu1.setMenuName("消息提醒");				
				str.add(menu1);
				Menus menu2 = new Menus();
				menu2.setUrl("sssss.ss");
				menu2.setMenuName("待办任务");				
				str.add(menu2);
				Menus menu3 = new Menus();
				menu3.setUrl("sssss.ss");
				menu3.setMenuName("尽职报告");				
				str.add(menu3);
				Menus menu5 = new Menus();
				menu5.setUrl("sssss.ss");
				menu5.setMenuName("股权交割");		
				str.add(menu5);
				Menus menu7= new Menus();
				menu7.setUrl("sssss.ss");
				menu7.setMenuName("档案模板");				
				str.add(menu7);
				Menus menu4 = new Menus();
				menu4.setUrl("sssss.ss");
				menu4.setMenuName("档案查询");
				str.add(menu4);
				System.out.println("FWJL");	
			}else if(id == 11L){//财务总监
				Menus menu = new Menus();
				menu.setUrl("ssss.ss");
				menu.setMenuName("工作桌面");									
				str.add(menu);	
				Menus menu1 = new Menus();
				menu1.setUrl("sssss.ss");
				menu1.setMenuName("消息提醒");				
				str.add(menu1);
				Menus menu2 = new Menus();
				menu2.setUrl("sssss.ss");
				menu2.setMenuName("待办任务");				
				str.add(menu2);
				Menus menu3 = new Menus();
				menu3.setUrl("sssss.ss");
				menu3.setMenuName("尽职报告");				
				str.add(menu3);
				Menus menu5 = new Menus();
				menu5.setUrl("sssss.ss");
				menu5.setMenuName("付款凭证");		
				str.add(menu5);
				Menus menu7= new Menus();
				menu7.setUrl("sssss.ss");
				menu7.setMenuName("档案模板");				
				str.add(menu7);
				Menus menu4 = new Menus();
				menu4.setUrl("sssss.ss");
				menu4.setMenuName("档案查询");
				str.add(menu4);
				System.out.println("CWZJ");					
			}else if(id == 12L){//财务经理
				Menus menu = new Menus();
				menu.setUrl("ssss.ss");
				menu.setMenuName("工作桌面");									
				str.add(menu);	
				Menus menu1 = new Menus();
				menu1.setUrl("sssss.ss");
				menu1.setMenuName("消息提醒");				
				str.add(menu1);
				Menus menu2 = new Menus();
				menu2.setUrl("sssss.ss");
				menu2.setMenuName("待办任务");				
				str.add(menu2);
				Menus menu3 = new Menus();
				menu3.setUrl("sssss.ss");
				menu3.setMenuName("尽职报告");				
				str.add(menu3);
				Menus menu5 = new Menus();
				menu5.setUrl("sssss.ss");
				menu5.setMenuName("付款凭证");		
				str.add(menu5);
				Menus menu7= new Menus();
				menu7.setUrl("sssss.ss");
				menu7.setMenuName("档案模板");				
				str.add(menu7);
				Menus menu4 = new Menus();
				menu4.setUrl("sssss.ss");
				menu4.setMenuName("档案查询");
				str.add(menu4);
				System.out.println("CWJL");	
			}else if(id == 13L){
				
			}else if(id == 14L){
				
			}else if(id == 15L){
				
			}else if(id == 16L){//管理员				
				Menus menu = new Menus();
				menu.setUrl("ssss.ss");
				menu.setMenuName("用户管理");									
				str.add(menu);	
				Menus menu1 = new Menus();
				menu1.setUrl("sssss.ss");
				menu1.setMenuName("数据字典");				
				str.add(menu1);
				System.out.println("1");
			} 			
		    responseBody.setEntityList(str);	
			return responseBody;
	}

	@Override
	protected BaseService<Menus> getBaseService() {
	
		return null;
	}

}
