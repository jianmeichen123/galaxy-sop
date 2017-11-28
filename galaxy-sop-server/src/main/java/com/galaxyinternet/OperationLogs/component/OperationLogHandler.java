package com.galaxyinternet.OperationLogs.component;

import java.util.List;

import org.springframework.core.Ordered;

import com.galaxyinternet.model.operationLog.OperationLogs;

public interface OperationLogHandler extends Ordered
{
	public boolean support(OperationLogParams params);
	public List<OperationLogs> handle(OperationLogParams params);
}
