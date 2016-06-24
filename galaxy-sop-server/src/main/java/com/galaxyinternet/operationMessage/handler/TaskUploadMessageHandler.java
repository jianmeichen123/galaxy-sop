package com.galaxyinternet.operationMessage.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.SopFileService;
@Component
public class TaskUploadMessageHandler implements MessageHandler
{
	@Autowired
	private SopFileService fileService;
	/**
	 * 5.5	上传/更新人事尽调
	 */
	private String MESSAGE_TYPE_RSJD = "5.5";
	/**
	 * 5.6	上传/更新财务尽调
	 */
	private String MESSAGE_TYPE_CWJD = "5.6";
	/**
	 * 5.7	上传/更新法务尽调
	 */
	private String MESSAGE_TYPE_FWJD = "5.7";
	/**
	 * 5.10	上传/更新股权变更凭证
	 */
	private String MESSAGE_TYPE_GSZR = "5.10";
	/**
	 * 5.11	上传/更新财务打款凭证
	 */
	private String MESSAGE_TYPE_ZJBF = "5.11";
	
	private Map<String,DictEnum.fileWorktype> map = new HashMap<String,DictEnum.fileWorktype>();
	
	public TaskUploadMessageHandler()
	{
		map.put(MESSAGE_TYPE_RSJD, DictEnum.fileWorktype.人力资源尽职调查报告);
		map.put(MESSAGE_TYPE_CWJD, DictEnum.fileWorktype.财务尽职调查报告);
		map.put(MESSAGE_TYPE_FWJD, DictEnum.fileWorktype.法务尽职调查报告);
		map.put(MESSAGE_TYPE_GSZR, DictEnum.fileWorktype.工商转让凭证);
		map.put(MESSAGE_TYPE_ZJBF, DictEnum.fileWorktype.资金拨付凭证);
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getOrder()
	{
		return 5;
	}

	@Override
	public boolean support(OperationMessage message)
	{
		return message.getMessageType() != null && map.containsKey(message.getMessageType());
	}

	@Override
	public OperationMessage handle(OperationMessage message)
	{
		String fileName = getFileName(message.getProjectId(), map.get(message.getMessageType()).getCode());
		//<用户名>为项目<项目名称><上传/更新>了<文档类型>《<文件名>》。
		StringBuffer content = new StringBuffer();
		content.append(message.getOperator())
		.append("为项目")
		.append(ControllerUtils.getProjectNameLink(message))
		.append(message.getContent())
		.append("了")
		.append(map.get(message.getMessageType()).getName())
		.append("&lt;&lt;")
		.append(fileName)
		.append("&gt;&gt;");
		message.setContent(content.toString());
		return message;
	}
	
	private String getFileName(Long projectId, String fileWorktype)
	{
		String fileName = null;
		SopFileBo query = new SopFileBo();
		query.setProjectId(projectId);
		query.setFileWorktype(fileWorktype);
		SopFile sopFile = fileService.queryOne(query);
		if(sopFile != null)
		{
			fileName = sopFile.getFileName();
		}
		return fileName;
	}

}
