package com.galaxyinternet.operationMessage.component;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class MessageRequest
{
	private static final Logger logger = LoggerFactory.getLogger(MessageRequest.class);
	private String endpoint = "http://fxmob.dev.galaxyinternet.com/starservice";
	private RestTemplate template;
	
	public MessageRequest()
	{
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(2000);
		factory.setReadTimeout(3000);
		template  = new RestTemplate(factory);
	}
	
	public boolean send(String messageType, Long relatedId, Long userId)
	{
		String url = endpoint+"/galaxy/schedule/message/saveSchedule";
		Long[] ids = {relatedId};
		Map<String, Object> vars = new HashMap<>();
		vars.put("userId", userId+"");
		vars.put("ids", ids);
		vars.put("messageType", messageType);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(vars, headers);
		if( logger.isDebugEnabled())
		{
			logger.debug(String.format("Request URI:%s, Params:%s", url, vars));
		}
		ResponseEntity<MessageResponse> rtn = template.postForEntity(url, request, MessageResponse.class);
		if(logger.isDebugEnabled())
		{
			logger.debug(String.format("Response Status:%s, Content:%s", rtn.getStatusCode(), rtn.getBody()));
		}
		MessageResponse resp = rtn.getBody();
		if(rtn.getStatusCode().equals(HttpStatus.OK) && "OK".equals(resp.getStatus()))
		{
			return true;
		}
		return false;
	}
	
	

	public String getEndpoint()
	{
		return endpoint;
	}

	public void setEndpoint(String endpoint)
	{
		this.endpoint = endpoint;
	}
	
	public static void main(String args[])
	{
		MessageRequest req = new MessageRequest();
		//Integer count = req.getUnReadCount(4L);
		//logger.info("Count: "+count);
		
		req.send("1.2.2", 2318L, 53L);
	}
	
	
}
