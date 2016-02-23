package com.galaxyinternet.user.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.user.UserDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.user.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, Long>implements UserDao {
}
