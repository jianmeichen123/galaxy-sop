package com.galaxyinternet.chart.controller;


import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.chart.ChartBo;


@Controller
@RequestMapping("/demo")
public class IndexSampleController {

	/**
	 * 项目历时
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/indexSample", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String indexSample(HttpServletRequest request) {
	
		return "indexSampleDemo/xmls";
	}
	
	/**
	 * 项目历时统计
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/progressDurationList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject progressDurationList(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		String json ="{\"result\":{\"status\":\"OK\"},\"pageList\":{\"isHHR\":false},\"mapList\":[{\"project_progress_name\":\"接触访谈\",\"duration_day\":2,\"project_progress\":\"projectProgress:1\"},{\"project_progress_name\":\"内部评审\",\"duration_day\":3,\"project_progress\":\"projectProgress:2\"},{\"project_progress_name\":\"CEO评审\",\"duration_day\":0,\"project_progress\":\"projectProgress:3\"},{\"project_progress_name\":\"立项会\",\"duration_day\":6,\"project_progress\":\"projectProgress:4\"},{\"project_progress_name\":\"投资意向书\",\"duration_day\":0,\"project_progress\":\"projectProgress:5\"},{\"project_progress_name\":\"尽职调查\",\"duration_day\":0,\"project_progress\":\"projectProgress:6\"},{\"project_progress_name\":\"投资决策会\",\"duration_day\":0,\"project_progress\":\"projectProgress:7\"},{\"project_progress_name\":\"投资协议 \",\"duration_day\":0,\"project_progress\":\"projectProgress:8\"},{\"project_progress_name\":\"股权交割\",\"duration_day\":0,\"project_progress\":\"projectProgress:9\"}]}";
		JSONObject reqjson = JSONObject.fromObject(json);
		return reqjson;
	}
	
	/**
	 * 项目进度
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/indexProgress", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String indexProgress(HttpServletRequest request) {
		return "indexSampleDemo/xmjd";
	}
	
	/**
	 * 项目进度数据
	 */
	@ResponseBody
	@RequestMapping(value = "/indexProgressData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject indexProgressData(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		String json = "{\"result\":{\"status\":\"OK\"},\"pageList\":{\"isHHR\":false},\"mapList\":[{\"total\":41,\"rate\":0.6098,\"c\":25,\"name\":\"接触访谈\",\"dict_sort\":1,\"dict_code\":\"projectProgress:1\",\"dict_id\":28},{\"total\":41,\"rate\":0.0244,\"c\":1,\"name\":\"内部评审\",\"dict_sort\":2,\"dict_code\":\"projectProgress:2\",\"dict_id\":29},{\"total\":41,\"rate\":0.0732,\"c\":3,\"name\":\"CEO评审\",\"dict_sort\":3,\"dict_code\":\"projectProgress:3\",\"dict_id\":30},{\"total\":41,\"rate\":0.1220,\"c\":5,\"name\":\"立项会\",\"dict_sort\":4,\"dict_code\":\"projectProgress:4\",\"dict_id\":31},{\"total\":41,\"rate\":0.0732,\"c\":3,\"name\":\"投资意向书\",\"dict_sort\":5,\"dict_code\":\"projectProgress:5\",\"dict_id\":32},{\"c\":0,\"name\":\"尽职调查\",\"dict_sort\":6,\"dict_code\":\"projectProgress:6\",\"dict_id\":33},{\"total\":41,\"rate\":0.0244,\"c\":1,\"name\":\"投资决策会\",\"dict_sort\":7,\"dict_code\":\"projectProgress:7\",\"dict_id\":34},{\"c\":0,\"name\":\"投资协议 \",\"dict_sort\":8,\"dict_code\":\"projectProgress:8\",\"dict_id\":35},{\"c\":0,\"name\":\"股权交割\",\"dict_sort\":9,\"dict_code\":\"projectProgress:9\",\"dict_id\":36},{\"total\":41,\"rate\":0.0732,\"c\":3,\"name\":\"投后运营\",\"dict_sort\":10,\"dict_code\":\"projectProgress:10\",\"dict_id\":37}]}";
		JSONObject reqjson = JSONObject.fromObject(json);
		return reqjson;
	}
	
	/**
	 * 绩效考核
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/indexKpi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String indexKpi(HttpServletRequest request) {
		return "indexSampleDemo/jxkh";
	}
	
	/**
	 * 绩效考核数据
	 */
	@ResponseBody
	@RequestMapping(value = "/indexKpiData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject indexKpiData(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		String json = "{\"result\":{\"status\":\"OK\"},\"pageList\":{\"total\":3,\"content\":[{\"dept_target\":0,\"tjl_rate\":0.7500,\"dept_name\":\"人工智能\",\"ghl_rate\":1.0000,\"company_rank\":1,\"dept_id\":13,\"lxh_pnumber\":7,\"zj_completed\":13,\"tjh_pnumber\":3,\"zj_rate\":0.5417,\"completed_all\":24,\"total_rate\":0.5854,\"hhr\":\"\",\"total_target\":0,\"completed\":24},{\"dept_target\":0,\"tjl_rate\":0.0000,\"dept_name\":\"互联网化工\",\"ghl_rate\":0.0000,\"company_rank\":2,\"dept_id\":24,\"lxh_pnumber\":0,\"zj_completed\":5,\"tjh_pnumber\":0,\"zj_rate\":0.5556,\"completed_all\":9,\"total_rate\":0.2195,\"hhr\":\"\",\"total_target\":0,\"completed\":9},{\"dept_target\":0,\"tjl_rate\":0.0000,\"dept_name\":\"O2O及电商\",\"ghl_rate\":0.0000,\"company_rank\":3,\"dept_id\":11,\"lxh_pnumber\":0,\"zj_completed\":2,\"tjh_pnumber\":0,\"zj_rate\":0.2500,\"completed_all\":8,\"total_rate\":0.1951,\"hhr\":\"\",\"total_target\":0,\"completed\":8}],\"isHHR\":false}}";
		JSONObject reqjson = JSONObject.fromObject(json);
		return reqjson;
	}
	
	/**
	 * 投资资金
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/indexTZ", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String indexTZ(HttpServletRequest request) {
		return "indexSampleDemo/tzje";
	}
	
	/**
	 * 投资资金数据
	 */
	@ResponseBody
	@RequestMapping(value = "/indexTZData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject indexTZData(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		String json = "{\"result\":{\"status\":\"OK\"},\"pageList\":{\"isHHR\":false},\"mapList\":[{\"rate\":2.0060,\"project_valuations\":0.20,\"target\":0,\"preday_completed\":0,\"project_contribution\":0.30,\"rise_rate\":2.0000,\"completed\":3,\"biz_date\":\"2016-08\"}]}";
		JSONObject reqjson = JSONObject.fromObject(json);
		return reqjson;
	}
	
	/**
	 * 项目目标追踪
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/indexXMMB", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String indexXMMB(HttpServletRequest request) {
		return "indexSampleDemo/xmmbzz";
	}
	
	/**
	 * 项目目标追踪数据
	 */
	@ResponseBody
	@RequestMapping(value = "/indexXMMBData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject indexXMMBData(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		//返回对象
		String json = "{\"result\":{\"status\":\"OK\"},\"pageList\":{\"isHHR\":false},\"mapList\":[{\"notcompleted\":0,\"target\":0,\"above\":43,\"year\":2016,\"completed\":43}]}";
		JSONObject reqjson = JSONObject.fromObject(json);
		return reqjson;
	}
	
	/**
	 * 投资金额追踪
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/indexTZJEZZ", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String indexTZJEZZ(HttpServletRequest request) {
		return "indexSampleDemo/tzjezz";
	}
	
	/**
	 * 投资金额追踪数据
	 */
	@ResponseBody
	@RequestMapping(value = "/indexTZJEZZData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject indexTZJEZZData(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		//返回对象
		String json = "{\"result\":{\"status\":\"OK\"},\"pageList\":{\"isHHR\":false},\"mapList\":[{\"notcompleted\":2000,\"target\":0,\"above\":800000,\"year\":2016,\"completed\":800000}]}";
		JSONObject reqjson = JSONObject.fromObject(json);
		return reqjson;
	}
	
	/**
	 * 项目运营
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/indexXMYY", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String indexXMYY(HttpServletRequest request) {
		return "indexSampleDemo/xmyy";
	}
	
	/**
	 * 项目运营数据
	 */
	@ResponseBody
	@RequestMapping(value = "/indexXMYYData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject indexXMYYData(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		//返回对象
		String json = "{\"result\": {\"status\": \"OK\"},\"pageList\":{\"isHHR\":false},\"mapList\":[{\"dept_target\": 0,\"tjl_rate\": 1,\"year\": \"201501\",\"you\": 30,\"cha\": 2,\"liang\":24,\"lxh_pnumber\": 8,\"zj_completed\": 14,\"tjh_pnumber\": 3,\"zj_rate\": 0.4828,\"completed_all\": 30,\"total_rate\": 0.617,\"hhr\": \"\",\"total_target\": 0,\"completed\": 29},{\"dept_target\": 0,\"tjl_rate\": 0,\"year\": \"201502\",\"you\": 3,\"cha\": 2,\"liang\": 24,\"lxh_pnumber\": 0,\"zj_completed\": 5,\"tjh_pnumber\": 0,\"zj_rate\": 0.5556,\"completed_all\": 9,\"total_rate\": 0.1915,\"hhr\": \"\",\"total_target\": 0,\"completed\": 9}]}";
		JSONObject reqjson = JSONObject.fromObject(json);
		return reqjson;
	}
	
	/**
	 * 投资事业线项目目标完成比
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/indexXMMBWCB", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String indexXMMBWCB(HttpServletRequest request) {
		return "indexSampleDemo/xmmbwcb";
	}
	
	/**
	 * 投资事业线项目目标完成比数据
	 */
	@ResponseBody
	@RequestMapping(value = "/indexXMMBWCBData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject indexXMMBWCBData(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		//返回对象
		String json = "{\"result\":{\"status\":\"OK\"},\"pageList\":{\"isHHR\":false},\"mapList\":[{\"id\":13,\"rate\":0,\"name\":\"人工智能\",\"target\":0,\"completed\":31},{\"id\":24,\"rate\":0,\"name\":\"互联网化工\",\"target\":0,\"completed\":9},{\"id\":11,\"rate\":0,\"name\":\"O2O及电商\",\"target\":0,\"completed\":8},{\"id\":7,\"rate\":0,\"name\":\"互联网餐饮\",\"target\":0,\"completed\":0},{\"id\":8,\"rate\":0,\"name\":\"云计算大数据\",\"target\":0,\"completed\":0},{\"id\":25,\"rate\":0,\"name\":\"互联网钢铁\",\"target\":0,\"completed\":0},{\"id\":9,\"rate\":0,\"name\":\"互联网医疗\",\"target\":0,\"completed\":0},{\"id\":26,\"rate\":0,\"name\":\"互联网房地产\",\"target\":0,\"completed\":0},{\"id\":10,\"rate\":0,\"name\":\"互联网旅游\",\"target\":0,\"completed\":0},{\"id\":28,\"rate\":0,\"name\":\"企业服务\",\"target\":0,\"completed\":0},{\"id\":12,\"rate\":0,\"name\":\"互联网农业\",\"target\":0,\"completed\":0},{\"id\":14,\"rate\":0,\"name\":\"互联网教育\",\"target\":0,\"completed\":0},{\"id\":15,\"rate\":0,\"name\":\"互联网服装\",\"target\":0,\"completed\":0},{\"id\":5,\"rate\":0,\"name\":\"数字娱乐\",\"target\":0,\"completed\":0},{\"id\":16,\"rate\":0,\"name\":\"媒体社交\",\"target\":0,\"completed\":0},{\"id\":6,\"rate\":0,\"name\":\"互联网金融\",\"target\":0,\"completed\":0},{\"id\":17,\"rate\":0,\"name\":\"互联网汽车\",\"target\":0,\"completed\":0}]}";
		JSONObject reqjson = JSONObject.fromObject(json);
		return reqjson;
	}
	
	/**
	 * 项目完成率分析
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/indexXMWCLFX", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String indexXMWCLFX(HttpServletRequest request) {
		return "indexSampleDemo/xmwclfx";
	}
	
	/**
	 * 项目完成率分析数据
	 */
	@ResponseBody
	@RequestMapping(value = "/indexXMWCLFXData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject indexXMWCLFXData(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		//返回对象
		String json = "{\"result\":{\"status\":\"OK\"},\"pageList\":{\"isHHR\":false},\"mapList\":[{\"rate\":0.0000,\"project_valuations\":1008.62,\"target\":0,\"preday_completed\":0,\"project_contribution\":585.00,\"rise_rate\":0.0000,\"completed\":6,\"biz_date\":\"2016-06\"},{\"rate\":0.0000,\"project_valuations\":6547443.61,\"target\":0,\"preday_completed\":6,\"project_contribution\":5561464.00,\"rise_rate\":6.0000,\"completed\":42,\"biz_date\":\"2016-07\"}]}";
		JSONObject reqjson = JSONObject.fromObject(json);
		return reqjson;
	}
	
	/**
	 * 项目进度分布图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/indexXMJDFT", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String indexXMJDFT(HttpServletRequest request) {
		return "indexSampleDemo/xmjdft";
	}
	
	/**
	 * 项目进度分布图数据
	 */
	@ResponseBody
	@RequestMapping(value = "/indexXMJDFTData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject indexXMJDFTData(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		//返回对象
		String json = "{\"result\":{\"status\":\"OK\"},\"pageList\":{\"isHHR\":false},\"mapList\":[{\"total\":48,\"rate\":0.6042,\"c\":29,\"name\":\"接触访谈\",\"dict_sort\":1,\"dict_code\":\"projectProgress:1\",\"dict_id\":28},{\"total\":48,\"rate\":0.0208,\"c\":1,\"name\":\"内部评审\",\"dict_sort\":2,\"dict_code\":\"projectProgress:2\",\"dict_id\":29},{\"total\":48,\"rate\":0.0625,\"c\":3,\"name\":\"CEO评审\",\"dict_sort\":3,\"dict_code\":\"projectProgress:3\",\"dict_id\":30},{\"total\":48,\"rate\":0.1667,\"c\":8,\"name\":\"立项会\",\"dict_sort\":4,\"dict_code\":\"projectProgress:4\",\"dict_id\":31},{\"total\":48,\"rate\":0.0625,\"c\":3,\"name\":\"投资意向书\",\"dict_sort\":5,\"dict_code\":\"projectProgress:5\",\"dict_id\":32},{\"c\":0,\"name\":\"尽职调查\",\"dict_sort\":6,\"dict_code\":\"projectProgress:6\",\"dict_id\":33},{\"c\":0,\"name\":\"投资决策会\",\"dict_sort\":7,\"dict_code\":\"projectProgress:7\",\"dict_id\":34},{\"c\":0,\"name\":\"投资协议 \",\"dict_sort\":8,\"dict_code\":\"projectProgress:8\",\"dict_id\":35},{\"c\":0,\"name\":\"股权交割\",\"dict_sort\":9,\"dict_code\":\"projectProgress:9\",\"dict_id\":36},{\"total\":48,\"rate\":0.0833,\"c\":4,\"name\":\"投后运营\",\"dict_sort\":10,\"dict_code\":\"projectProgress:10\",\"dict_id\":37}]}";
		JSONObject reqjson = JSONObject.fromObject(json);
		return reqjson;
	}
	/**
	 * 项目数统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/indexXMTJ", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String indexXMTJ(HttpServletRequest request) {
		return "indexSampleDemo/xmstj";
	}
	
	/**
	 * 项目数统计数据
	 */
	@ResponseBody
	@RequestMapping(value = "/indexXMTJData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject indexXMTJData(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		//返回对象
		String json = "{\"result\":{\"status\":\"OK\"},\"pageList\":{\"total\":17,\"content\":[{\"wb_rate\":\"0.4839\",\"rate\":\"0.0207\",\"zj_completed\":16,\"notcompleted\":1469,\"department_id\":13,\"zj_rate\":\"0.5161\",\"target\":1500,\"department_name\":\"人工智能\",\"wb_completed\":15,\"completed\":31},{\"wb_rate\":\"0.4444\",\"rate\":\"0.0180\",\"zj_completed\":5,\"notcompleted\":491,\"department_id\":24,\"zj_rate\":\"0.5556\",\"target\":500,\"department_name\":\"互联网化工\",\"wb_completed\":4,\"completed\":9},{\"wb_rate\":\"0.7500\",\"rate\":\"0.0160\",\"zj_completed\":2,\"notcompleted\":492,\"department_id\":11,\"zj_rate\":\"0.2500\",\"target\":500,\"department_name\":\"O2O及电商\",\"wb_completed\":6,\"completed\":8},{\"wb_rate\":\"0.0000\",\"rate\":\"0.0000\",\"zj_completed\":0,\"notcompleted\":0,\"department_id\":5,\"zj_rate\":\"0.0000\",\"target\":0,\"department_name\":\"数字娱乐\",\"wb_completed\":0,\"completed\":0},{\"wb_rate\":\"0.0000\",\"rate\":\"0.0000\",\"zj_completed\":0,\"notcompleted\":0,\"department_id\":6,\"zj_rate\":\"0.0000\",\"target\":0,\"department_name\":\"互联网金融\",\"wb_completed\":0,\"completed\":0},{\"wb_rate\":\"0.0000\",\"rate\":\"0.0000\",\"zj_completed\":0,\"notcompleted\":0,\"department_id\":7,\"zj_rate\":\"0.0000\",\"target\":0,\"department_name\":\"互联网餐饮\",\"wb_completed\":0,\"completed\":0},{\"wb_rate\":\"0.0000\",\"rate\":\"0.0000\",\"zj_completed\":0,\"notcompleted\":0,\"department_id\":8,\"zj_rate\":\"0.0000\",\"target\":0,\"department_name\":\"云计算大数据\",\"wb_completed\":0,\"completed\":0},{\"wb_rate\":\"0.0000\",\"rate\":\"0.0000\",\"zj_completed\":0,\"notcompleted\":500,\"department_id\":9,\"zj_rate\":\"0.0000\",\"target\":500,\"department_name\":\"互联网医疗\",\"wb_completed\":0,\"completed\":0},{\"wb_rate\":\"0.0000\",\"rate\":\"0.0000\",\"zj_completed\":0,\"notcompleted\":0,\"department_id\":10,\"zj_rate\":\"0.0000\",\"target\":0,\"department_name\":\"互联网旅游\",\"wb_completed\":0,\"completed\":0},{\"wb_rate\":\"0.0000\",\"rate\":\"0.0000\",\"zj_completed\":0,\"notcompleted\":0,\"department_id\":12,\"zj_rate\":\"0.0000\",\"target\":0,\"department_name\":\"互联网农业\",\"wb_completed\":0,\"completed\":0}],\"isHHR\":false}}";
		JSONObject reqjson = JSONObject.fromObject(json);
		return reqjson;
	}
}
