package com.galaxyinternet.timer.test;

import com.galaxyinternet.timer.AutoThread;

public class TimingThread extends AutoThread {

	@Override
	protected void executeTask() {
		System.out.println("Begin running......");
	}

}
