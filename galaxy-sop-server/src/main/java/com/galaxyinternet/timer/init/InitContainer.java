package com.galaxyinternet.timer.init;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import com.galaxyinternet.timer.AutoThreadManager;
import com.galaxyinternet.timer.test.TimerEntrance;

public class InitContainer {

	public void init() throws Exception {
		InputStream is = null;
		try {
			// 读取定时线程的配置文件
			TimerEntrance entrance = new TimerEntrance();
			URL path = entrance.getClass().getResource("/autostart.xml");
			is = path.openStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 解析配置的自动运行线程，并把它们全部放入AutoRunThreadManager的一个List集合中
		AutoThreadManager autoMgr = new AutoThreadManager(is);
		autoMgr.setName("AutoRunThreadManager");
		// 启动自动运行线程管理类
		autoMgr.start();
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
