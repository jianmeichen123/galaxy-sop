package com.galaxyinternet.service;

import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.model.DaNao.DnProject;
import com.galaxyinternet.model.DaNao.DnZixun;

import java.util.Map;

public interface InfoFromDanaoService {

	/**
	 * 查询项目详情 -> 项目compCode
	 * @param projCode
	 * @return
	 * @throws Exception
	 */
	public DnProject queryDanaoProjCompCode(String projCode) throws Exception;
	/**
	 * 查询大脑项目列表,封装项目公司信息
	 * 分条查询项目公司名称
	 */
	public Page<DnProject> queryDnaoProjectPage(Map<String,Object> map) throws Exception;
	/**
	 * 由大脑项目compCode
	 * 查询大脑项目工商信息
	 *   法人信息 legalInfo
	 *   股权结构 equityInfo
	 */
	public Map<String,Object> queryDnaoBusinessInfo(String compCode,String checkToChooseCode) throws Exception;
	/**
	 * 查询项目团队成员 teamInfo
	 * @param projCode
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryDnaoProjTeam(String projCode) throws Exception;
	/**
	 * 查询项目融资历史 financeInfo
	 * @param projCode
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryDnaoProjFinance(String projCode) throws Exception;


	/**
	 * 全局搜索的各类别total数目
	 * return Map
	 *   xhtProject        创投项目
	 *   dnProject         创投大脑的项目
	 *   dnZixun           创投大脑投融快讯
	 *   xhtAppZixun       星河资讯-app资讯
	 */
	public Map<String,Long> globalSearchTypesTotal(String keyword) throws Exception;

	/**
	 * 查询创投大脑 投融快讯列表,
	 *
	 */
	public Page<DnZixun> queryDnaoZixunPage(Map<String, Object> map) throws Exception;
	/**
	 * 查询 星河资讯- app资讯 列表,
	 *
	 */
	public Page<DnZixun> queryXhtAppZixunPage(Map<String, Object> map) throws Exception;

}