package com.galaxyinternet.user.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.galaxyinternet.model.user.User;

public class UserRepository {

	@Autowired
	private RedisTemplate<String, User> template;

	public void add(User user) {
		template.opsForValue().set(user.getMobile(), user);
	}

	public User get(String key) {
		return template.opsForValue().get(key);
	}

}
