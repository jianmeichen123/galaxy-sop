package com.galaxyinternet.hologram.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.service.hologram.InformationResultService;

@Controller
@RequestMapping("/galaxy/infomation")
public class InfoJumpController{
	final Logger logger = LoggerFactory.getLogger(InfoJumpController.class);
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	
	@Resource(name = "map")
    private Map<String,List<String>> map;
	
	@Autowired
	private InformationResultService informationResultService;
	
	private Set<String> titleids = new HashSet<String>();
	
	/**
	 * 基本信息全息图-tab页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tabInfomation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String platform(HttpServletRequest request) {
		return "seven_report/hologram/tabInfomation";
	}
     /**
	 *全息图-基本信息模块
	 * @param request
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toBaseInfo", method = RequestMethod.GET)
	public String message(HttpServletRequest request) {
		return "seven_report/hologram/baseInfo";
	}
	
	/**
	 * 全息图-项目模块
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toProjectInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String projects(HttpServletRequest request) {
		return "seven_report/hologram/projectInfo";
	}
	/**
	 * 全息图-团队成员
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toTeamInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String dataBriefing(HttpServletRequest request) {
		return "seven_report/hologram/teamInfo";
	}
	
	/**
	 * 全息图-运营数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toOperateInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String projectAnalysis(HttpServletRequest request) {
		return "seven_report/hologram/operateInfo";
	}
	
	
	/**
	 * 全息图-竞争
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toSaveCompeteTable", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toSaveCompeteTable(HttpServletRequest request) {
		return "seven_report/hologram/gethtml/compete_save";
	}
	/**
	 * 全息图-竞争
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toCompeteInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String pfmAppraisal(HttpServletRequest request) {
		return "seven_report/hologram/competeInfo";
	}
	
	/**
	 * 全息图-战略以及策略
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toPlanInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String operation(HttpServletRequest request) {
		return "seven_report/hologram/planInfo";
	}

	/**
	 * 全息图-财务
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toFinanceInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String paprojectlist(HttpServletRequest request) {
		return "seven_report/hologram/financeInfo";
	}
	
	/**
	 * 全息图-法务
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toJusticeInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String ghlprojectlist(HttpServletRequest request) {
		return "seven_report/hologram/justiceInfo";
	}
	
	/**
	 * 全息图-融资及估值
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toValuationInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String tjlprojectlist(HttpServletRequest request) {
		return "seven_report/hologram/valuationInfo";
	}
	/**
	 * 全息图-融资及估值-融资历史
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toFinaceHistory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toFinaceHistory(HttpServletRequest request) {
		return "seven_report/hologram/finace_history";
	}
//	评测
	@RequestMapping(value = "/toEvalindex", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toEvalindex(HttpServletRequest request) {
		return "seven_report/evaluation/index";
	}
	//尽调报告
	@RequestMapping(value = "/investigate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String investigate(HttpServletRequest request) {
		return "seven_report/investigate/index";
	}
//	决策
	@RequestMapping(value = "/toDecision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toDecision(HttpServletRequest request) {
		return "seven_report/decision/index";
	}
//	初评
	@RequestMapping(value = "/toPreEva", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toPreEva(HttpServletRequest request) {
		return "seven_report/pre_eva/index";
	}
//	融资Financing
	@RequestMapping(value = "/toFinancing", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toFinancing(HttpServletRequest request) {
		return "seven_report/financing/index";
	}
	//运营Operation
	@RequestMapping(value = "/toOperation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toOperation(HttpServletRequest request) {
		return "seven_report/operation/index";
	}
	/**
	 * 获取必须题目的id
	 * @param projectId
	 * @return
	 */
	public List<String> getMustTitleIds(String projectId) {
	    List<String> ids = new ArrayList<String>();
    	if(!map.isEmpty()){
    		titleids.clear();
             for(Map.Entry<String,List<String>> en : map.entrySet()){
            	 if(!en.getKey().contains("-ref")){
            		 titleids.add(en.getKey());
            	 }
    		}
    	}
    	InformationResult ir = new InformationResult();
    	ir.setProjectId(String.valueOf(projectId));
    	ir.setTitleIds(titleids);
    	List<InformationResult> list = informationResultService.queryList(ir);
    	if(list == null || list.size() == 0){
    		return null;
    	}
    	Map<String,String> max = new HashMap<String,String>();
		for(InformationResult in : list){
			//所选值id
			String choose = in.getContentChoose();
			//值ids集合
			List<String> type = map.get(in.getTitleId());
			List<String> unnio = map.get(in.getTitleId()+"-union-ref");
			if(choose == null && unnio != null && unnio.size() > 0){
				choose = unnio.get(0);
			}
			if(choose != null && type.contains(choose)){
				List<String> title = map.get(choose+"-ref");
				if(!ids.containsAll(title)){
					ids.addAll(title);
				}
			}
			if(choose == null){
				continue;
			}
			if(max.containsKey(in.getTitleId())){
			    max.put(in.getTitleId(), max.get(in.getTitleId())+","+choose);
			}else{
				max.put(in.getTitleId(), choose);
			}
		}
		
		if(ids != null && ids.size() > 0){
			if(!max.isEmpty()){
				for(Map.Entry<String, String> m : max.entrySet()){
					/*//获取项目模式id
					List<String> unnio = map.get(m.getKey()+"-union-ref");
					//如果出现交集
					if(unnio != null && unnio.size() > 0){
						//项目阶段id
						String key = unnio.get(0);
						//项目阶段选择
						String choose = max.get(key);
						if(!map.get(key).contains(choose)){
							List<String> removeids = map.get(m.getKey());
							if(removeids != null && removeids.size() > 0){
								List<String> refRemoves = map.get(removeids.get(0)+"-ref");
								ids.removeAll(refRemoves);
							}
						}
					}*/
					if(m.getValue().contains(",")){
						List<String> unnio = map.get(m.getKey()+"-union-remove-ref");
						if(m.getValue().contains(unnio.get(0))){
							List<String> refRemoves = map.get(unnio.get(0)+"-ref");
							ids.removeAll(refRemoves);
						}
					}
				}
			}
		}
	    return ids;
	}
	/***
	 * list 转 string
	 * @param stringList
	 * @return
	 */
	 public static String listToString(List<String> stringList){
	        if (stringList==null) {
	            return null;
	        }
	        StringBuilder result=new StringBuilder();
	        boolean flag=false;
	        for (String string : stringList) {
	            if (flag) {
	                result.append(",");
	            }else {
	                flag=true;
	            }
	            result.append(string);
	        }
	        return result.toString();
	    }
	
	/**
	 * 获取必填数据
	 * @param request
	 * @param projectId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMustInfo/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationTitle> queryTitleInfo(HttpServletRequest request,@PathVariable("projectId") String projectId ) {
		  ResponseData<InformationTitle> responseBody = new ResponseData<InformationTitle>();
		  InformationTitle it = new InformationTitle();
			try{
			   if(StringUtils.isNotEmpty(projectId)){
				   List<String> titleList = getMustTitleIds(String.valueOf(projectId));
				   if(titleList != null && titleList.size() > 0){
					   it.setResultIds(listToString(titleList));
				   }
			   }
			   responseBody.setEntity(it);
			   responseBody.setResult(new Result(Status.OK, ""));
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR,null, "必填选项获取失败"));
				logger.error("queryMustInfo 必填选项获取失败：",e);
			}
	       return responseBody;
	}

	/**
	 * 尽职调查报告页面****************************************开始
	 */
	// 尽调报告-项目信息页面
	@RequestMapping(value = "/toInvestigateP", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toInvestigateP(HttpServletRequest request) {
		return "seven_report/investigate/index";
	}

	// 尽调报告-团队成员
	@RequestMapping(value = "/toInvestigateT", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toInvestigateT(HttpServletRequest request) {
		return "seven_report/investigate/teamInfo";
	}

	// 尽调报告-运营数据
	@RequestMapping(value = "/toInvestigateO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toInvestigateO(HttpServletRequest request) {
		return "seven_report/investigate/operateInfo";
	}

	// 尽调报告-竞争分析
	@RequestMapping(value = "/toInvestigateC", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toInvestigateC(HttpServletRequest request) {
		return "seven_report/investigate/competeInfo";
	}

	// 尽调报告-战略策略
	@RequestMapping(value = "/toInvestigatePlan", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toInvestigatePlan(HttpServletRequest request) {
		return "seven_report/investigate/planInfo";
	}

	// 尽调报告-财务
	@RequestMapping(value = "/toInvestigateF", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toInvestigateF(HttpServletRequest request) {
		return "seven_report/investigate/financeInfo";
	}

	// 尽调报告-法务
	@RequestMapping(value = "/toInvestigateJ", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toInvestigateJ(HttpServletRequest request) {
		return "seven_report/investigate/justiceInfo";
	}

	// 尽调报告-融资以及估值
	@RequestMapping(value = "/toInvestigateV", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toInvestigateV(HttpServletRequest request) {
		return "seven_report/investigate/valuationInfo";
	}

	/**
	 * 尽职调查报告页面 ****************************************结束
	 */
	/**
	 * 融资报告页面****************************************开始
	 */
	// 融资报告-项目信息页面
	@RequestMapping(value = "/toFinancingP", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toFinancingPlanP (HttpServletRequest request) {
		return "seven_report/financing/index";
	}

	// 融资报告-团队成员
	@RequestMapping(value = "/toFinancingT", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toFinancingT(HttpServletRequest request) {
		return "seven_report/financing/teamInfo";
	}

	// 融资报告-运营数据
	@RequestMapping(value = "/toFinancingO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toFinancingO(HttpServletRequest request) {
		return "seven_report/financing/operateInfo";
	}

	// 融资报告-竞争分析
	@RequestMapping(value = "/toFinancingC", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toFinancingPlanC(HttpServletRequest request) {
		return "seven_report/financing/competeInfo";
	}

	// 融资报告-战略策略
	@RequestMapping(value = "/toFinancingPlan", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toFinancingPlan(HttpServletRequest request) {
		return "seven_report/financing/planInfo";
	}

	// 融资报告-财务
	@RequestMapping(value = "/toFinancingF", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toFinancingPlanF(HttpServletRequest request) {
		return "seven_report/financing/financeInfo";
	}

	// 融资报告-法务
	@RequestMapping(value = "/toFinancingJ", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toFinancingPlanJ(HttpServletRequest request) {
		return "seven_report/financing/justiceInfo";
	}

	// 融资报告-融资以及估值
	@RequestMapping(value = "/toFinancingV", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toFinancingPlanV(HttpServletRequest request) {
		return "seven_report/financing/valuationInfo";
	}

	/**
	 * 融资报告页面 ****************************************结束
	 */
	/**
	 * 运营报告页面****************************************开始
	 */
	// 运营报告-投资方案
	@RequestMapping(value = "/toOperationP", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toOperationP(HttpServletRequest request) {
		return "seven_report/operation/index";
	}

	// 运营报告-团队成员
	@RequestMapping(value = "/toOperationT", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toOperationT(HttpServletRequest request) {
		return "seven_report/operation/teamInfo";
	}

	// 运营报告-运营数据
	@RequestMapping(value = "/toOperationO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toOperationO(HttpServletRequest request) {
		return "seven_report/operation/operateInfo";
	}

	// 运营报告-市场与开发
	@RequestMapping(value = "/toOperationMD", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toOperationMD(HttpServletRequest request) {
		return "seven_report/operation/marketDevelop";
	}

	// 运营报告-竞争分析
	@RequestMapping(value = "/toOperationC", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toOperationC(HttpServletRequest request) {
		return "seven_report/operation/competeInfo";
	}

	// 运营报告-战略策略
	@RequestMapping(value = "/toOperationPlan", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toOperationPlan(HttpServletRequest request) {
		return "seven_report/operation/planInfo";
	}

	// 运营报告-财务
	@RequestMapping(value = "/toOperationF", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toOperationF(HttpServletRequest request) {
		return "seven_report/operation/financeInfo";
	}

	// 运营报告-法务
	@RequestMapping(value = "/toOperationJ", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toOperationJ(HttpServletRequest request) {
		return "seven_report/operation/justiceInfo";
	}

	// 运营报告-融资以及估值
	@RequestMapping(value = "/toOperationV", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toOperationV(HttpServletRequest request) {
		return "seven_report/operation/valuationInfo";
	}

	/**
	 * 运营报告页面 ****************************************结束
	 */
	/**
	 * 决策报告页面****************************************开始
	 */
	// 决策报告-投资方案
	@RequestMapping(value = "/toDecisionPlan", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toDecisionPlan(HttpServletRequest request) {
		return "seven_report/decision/index";
	}
	// 决策报告-其他事宜
	@RequestMapping(value = "/toDecisionOther", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toDecisionOther(HttpServletRequest request) {
		return "seven_report/decision/other";
	}
	/**
	 * 决策报告页面****************************************开始
	 */
}