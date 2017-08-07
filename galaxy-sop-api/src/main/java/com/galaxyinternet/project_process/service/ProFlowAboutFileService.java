package com.galaxyinternet.project_process.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;


public interface ProFlowAboutFileService extends BaseService<Project> {
	


	/**
	 * 根据  项目id、项目 progress ，封装当前文件的信息;  
	 * @param   pro.getId()  pro.getProjectProgress()
	 * @return
	 */
	public List<SopFile> getFileListForProFlow(Project pro);
	
	
	/**
	 * 根据  文档id、fileKey ，判断 insert update 
	 * @param   
	 * 
	 * file.getId() 、 file.FileWorktype 、 file.getFileKey()
	 * file.projectId、file.projectProgress
	 * 
	 * file.FileType 、 file.FileSource
	 * 
	 * file.fileUid   file.CareerLine  
	 *    
	 * @return  null ： 异常
	 */
	public SopFile optFileAboutProgress(HttpServletRequest request,BaseControllerImpl<?, ?> clazz, SopFile file, String tempfilePath);
	
	
	
	
}

