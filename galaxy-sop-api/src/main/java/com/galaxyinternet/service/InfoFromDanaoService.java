package com.galaxyinternet.service;

import java.util.List;
import java.util.Map;

import com.galaxyinternet.model.DongNao.DnProject;
import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;

public interface InfoFromDanaoService {

	/**
	 * 查询项目详情 -> 项目compCode
	 * @param projCode
	 * @return
	 * @throws Exception
	 */
	public String queryDanaoProjCompCode(String projCode) throws Exception;
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
}