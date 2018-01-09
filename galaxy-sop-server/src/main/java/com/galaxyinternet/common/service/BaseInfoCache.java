package com.galaxyinternet.common.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.cache.CacheHelper;
import com.galaxyinternet.platform.constant.PlatformConst;

import redis.clients.jedis.Response;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.util.SafeEncoder;

@Service
public class BaseInfoCache
{
	@Autowired
	private Cache cache;
	
	public Map<Long, String> getUserFromCache()
	{
		ShardedJedis jedis = null;
		Map<Long, String> map;
		try
		{
			CacheHelper helper = new CacheHelper();
			jedis = cache.getJedis();
			Map<Long,Response<byte[]>> idNameMap = new HashMap<>();
			Set<String> userIds = jedis.smembers(PlatformConst.CACHE_USER_IDS);
			if( userIds == null || userIds.size() == 0)
			{
				return new HashMap<>();
			}
			map = new HashMap<>(userIds.size());
			ShardedJedisPipeline pip = jedis.pipelined();
			for(String userId : userIds)
			{
				Long id = Long.parseLong(userId);
				idNameMap.put(id, pip.hget(SafeEncoder.encode(PlatformConst.CACHE_PREFIX_USER+id), SafeEncoder.encode("realName")));
			}
			pip.sync();
			for(String userId : userIds)
			{
				Long id = Long.parseLong(userId);
				String name = helper.bytesToObject(idNameMap.get(id).get())+"";
				map.put(id,name);
			}
		}
		finally
		{
			if(jedis != null)
			{
				cache.returnJedis(jedis);
			}
		}
		return map;
	}
}
