package com.galaxyinternet.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;

public interface ProjectService extends BaseService<Project> {
	
	/**
	 * 添加项目
	 * 对于每个新添加的项目来讲，整个生命周期中所关联的文档已确定，这里需要一并添加
	 */
	public long newProject(Project project, SopFile file) throws Exception;
	
	/**
	 * 申请立项会
	 */
	public void toEstablishStage(Project project) throws Exception;
	/**
	 * 申请投决会
	 */
	public void toSureMeetingStage(Project project) throws Exception;
	
	public Map<String, Object> getSummary(Long userId) throws Exception;
	
	public List<Project> queryListById(List<Long> idList);
	
	public int closeProject(Project project);
	
	public Page<Project> queryPageList(ProjectBo query, Pageable pageable);
	
	/**
	 * 为项目补全档案
	 */
	public long perfectFilesForProject(Project project) throws Exception;
	
	/**
	 * 分页条件查询（图表用）
	 * @param query
	 * @param pageRequest
	 * @return
	 */
	public Page<Project> queryPageListByChart(Project query,PageRequest pageRequest);

	List<Long> getProIdsForPrivilege(Map<String, Object> params);
	
	

}