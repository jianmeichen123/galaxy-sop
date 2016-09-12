package com.galaxyinternet.iosMessage.handle;

import org.springframework.stereotype.Component;

import com.galaxyinternet.iosMessage.handleInter.IosMessageHandler;
import com.galaxyinternet.model.iosMessage.IosMessage;
@Component
public class IosCommonHandler implements IosMessageHandler
{

	private static final long serialVersionUID = 1L;

	@Override
	public boolean support(IosMessage message)
	{
		return false;
	}

	@Override
	public IosMessage handle(IosMessage message)
	{
		return message;
	}

	@Override
	public int getOrder()
	{
		return LOWEST_PRECEDENCE;
	}
	
	

}
