package com.galaxyinternet.common;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;

@Component
public class CallResdisHander {
	final Logger logger = LoggerFactory.getLogger(CallResdisHander.class);
	private static long timeout = 1000;

	@Autowired
	Cache cache;
	
	/**
	 * 获取锁对象
	 * @return
	 */
	public Result lockSyn(String thread){
		try{
		    getSetId(thread);
		}catch(TimeoutException ex){
			return new Result(Status.ERROR,"请求超时,稍后重试!");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("call redis异常:"+e);
			return new Result(Status.ERROR,"系统异常!");
		}
		return new Result(Status.OK,"sucess");
	}
	
	/**
	 * 执行锁对象
	 * @param thread
	 * @return
	 * @throws Exception
	 */
	public void getSetId(String thread) throws Exception {
		Long id = null;
		while (true) {
			boolean success = getLock(thread+"_id_lock");
			if (success) {
				String strId=cache.getValue("id");
				//如果id为空说明未设置id值
				if (strId==null||strId=="") {
					id = 100000001l;
				} else {
					id=Long.valueOf(strId);
					id = id + 1;
				}
			    cache.setValue("id", String.valueOf(id));
			    //释放锁
			    releaseLock(thread+"_id_lock");
			    break;
			} else {
				logger.info("thread="+thread+",锁在使用中");
				Thread.sleep(100);
			}
		}
		logger.info("thread-id="+id);
	}
	
	/**
	 * 是否获取锁
	 * @param lockKey
	 * @return
	 */
	public boolean getLock(String lockKey) {
		boolean success = false; // 默认获取锁失败
		long value = System.currentTimeMillis() + timeout; // 设置锁值
		//SETNX lockKey 成功
		long isExit = cache.setNx(lockKey, String.valueOf(value));
		//isExit=1 说明SETNX成功，成功获取一个锁
		if (isExit == 1)
			success = true;
		//否则 SETNX失败，说明锁仍然被其他对象保持，检查其是否已经超时
		else {
		    long oldValue = Long.valueOf(cache.getValue(lockKey));
			// 锁已经超时
			if (oldValue < System.currentTimeMillis()) {
			    String getValue = cache.getSet(lockKey, String.valueOf(value));
				// 获取锁成功
				if (Long.valueOf(getValue) == oldValue)
				    success = true;
				//已被其他进程捷足先登了
				else
				    success = false;
				}
			//未超时，则直接返回失败
			else
			     success = false;
		}
		return success;
	}

	/**
	 * 释放锁
	 * @param lockKey
	 */
	public void releaseLock(String lockKey) {
		long current = System.currentTimeMillis();
		//避免删除非自己获取得到的锁
		if (current < Long.valueOf(cache.getValue(lockKey)))
		   cache.remove(lockKey);
	}
	
	
}