package com.galaxyinternet.iosMessage.handleInter;

import java.io.Serializable;

import org.springframework.core.Ordered;

import com.galaxyinternet.model.iosMessage.IosMessage;

public interface IosMessageHandler extends Ordered,Serializable
{
	public boolean support(IosMessage message);
	public IosMessage handle(IosMessage message);
}
