package com.galaxyinternet.operationMessage.component;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
	/**
	 * 获取用户未读消息数目
	 * @param userId
	 * @return
	 */
	public Integer getUnReadCount(Long userId)
	{
		String url = endpoint+"/galaxy/schedule/message/getUnReadCount";
		Map<String, String> vars = new HashMap<>();
		vars.put("uid", userId+"");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> request = new HttpEntity<>(vars, headers);
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
		if(!"OK".equals(resp.getStatus()))
		{
			return 0;
		}
		if( resp.getMap() == null || resp.getMap().size() == 0 || !resp.getMap().containsKey("count"))
		{
			return 0;
		}
		return (Integer)resp.getMap().get("count");
	}
	/**
	 * 标记为已读
	 * @param userId
	 */
	public void markAsReaded(Long userId)
	{
		String url = endpoint+"/galaxy/schedule/message/toReadByTime";
		Map<String, String> vars = new HashMap<>();
		vars.put("uid", userId+"");
		vars.put("time", System.currentTimeMillis()+"");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> request = new HttpEntity<>(vars, headers);
		if( logger.isDebugEnabled())
		{
			logger.debug(String.format("Request URI:%s, Params:%s", url, vars));
		}
		ResponseEntity<MessageResponse> rtn = template.postForEntity(url, request, MessageResponse.class);
		if(logger.isDebugEnabled())
		{
			logger.debug(String.format("Response Status:%s, Content:%s", rtn.getStatusCode(), rtn.getBody()));
		}
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
		
		req.markAsReaded(4L);
	}
	
	
}
