package com.galaxyinternet.timer;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AutoThreadManager extends Thread {
	
	/**
	 * months表示该年中的那几个月份执行定时任务
	 * days表示月份中的哪些天执行定时任务
	 * points表示天中的那个时间点执行定时任务
	 */
	public static final String _unit_all_ = "all";
	public static final String _unit_year_ = "year";
	public static final String _unit_month_ = "month";
	public static final String _unit_day_ = "day";
	
	public static final long WAIT_TIME = 5 * 1000;
	public static final long MAX_DELAY = 10 * 1000;
	public static long TIME_OUT = 5 * 60 * 1000;

	public static Object lock = new Object();
	public static List<AutoThread> list = new ArrayList<AutoThread>();;

	public AutoThreadManager() {}

	public AutoThreadManager(InputStream is) {
		try {
			// 解析xml配置文件
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(is);
			NodeList tasks = document.getElementsByTagName("task");
			List<Calendar> starts = new ArrayList<Calendar>();
			for (int i = 0; i < tasks.getLength(); i++) {
				starts.clear();
				Node task = tasks.item(i);
				String id = task.getAttributes().getNamedItem("id").getNodeValue();
				String entity = task.getAttributes().getNamedItem("class").getNodeValue();
				String description = task.getAttributes().getNamedItem("description").getNodeValue();
				try {
					if (task != null) {
						Object o = Class.forName(entity).newInstance();
						if (o == null || !(o instanceof AutoThread)) {
							continue;
						}
						AutoThread run = (AutoThread) o;
						if (id != null) {
							run.setId(id);
						} else {
							run.setId("Autorun-" + i);
						}
						if (description != null) {
							run.setDescription(description);
						}
						
						String unit = task.getAttributes().getNamedItem("unit").getNodeValue();
						String points = task.getAttributes().getNamedItem("points").getNodeValue();
						String[] pointArray = points.split(",");
						if(unit.trim().equals(_unit_month_)){
							/**
							 * <task unit="month" points="05-03,07-12" class="xxx.XXX" id="" description=""/>
							 * 表示每个月的设定的指定时间点触发执行定时程序
							 */
							for (int j = 0; j < pointArray.length; j++) {
								if (pointArray[j].trim().length() > 0) {
									Calendar startTime = Calendar.getInstance();
									DateUtil.putDateToMonthBegin(startTime);
									String[] times = pointArray[j].split("-");
									startTime.set(Calendar.DAY_OF_MONTH,Integer.parseInt(times[0]));
									if (times.length > 1) {
										startTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(times[1]));
									}
									run.getStartTimes().add(startTime);
									System.err.println("定时程序[" + entity + "]获得一个启动时间：" + DateUtil.parseCalendarToString(startTime));
								}
							}
						}else if(unit.trim().equals(_unit_day_)){
							/**
							 * <task unit="day" points="12:04,23:36" class="xxx.XXX" id="" description=""/>
							 * 表示每天的设定的几个时间点触发执行定时程序
							 */
							for (int j = 0; j < pointArray.length; j++) {
								if (pointArray[j].trim().length() > 0) {
									Calendar startTime = Calendar.getInstance();
									DateUtil.putDateToDayBegin(startTime);
									String[] times = pointArray[j].split(":");
									startTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(times[0]));
									if (times.length > 1) {
										startTime.set(Calendar.MINUTE,Integer.parseInt(times[1]));
									}
									run.getStartTimes().add(startTime);
									System.err.println("定时程序[" + entity + "]获得一个启动时间：" + DateUtil.parseCalendarToString(startTime));
								}
							}
						}
						list.add(run);
					}
				} catch (Exception e) {
					System.err.println("parse Auto-Task[id:" + id + "] get an exception!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list == null) {
			list = new ArrayList<AutoThread>();
		}
	}

	@Override
	public void run() {
		while (!this.isInterrupted()) {
			if (list.size() != 0) {
				synchronized (AutoThreadManager.lock) {
					for (AutoThread auto : list) {
						boolean isRunning = auto.isRunning();
						if (isRunning) {
							continue;
						}
						Calendar current = Calendar.getInstance();
						Calendar pStartTime = auto.getProviousStartTime();
						if (pStartTime == null
								|| current.getTimeInMillis() - pStartTime.getTimeInMillis() >= TIME_OUT) {
							for (Calendar c : auto.getStartTimes()) {
								c.set(Calendar.YEAR, current.get(Calendar.YEAR));
								c.set(Calendar.MONTH, current.get(Calendar.MONTH));
								c.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
								long min = current.getTimeInMillis() - c.getTimeInMillis();
								if (min >= 0 && min < MAX_DELAY) {
									auto.run();
								}
							}
						}

					}
				}
				try {
					Thread.sleep(WAIT_TIME);
				} catch (InterruptedException e) {
					this.interrupt();
					break;
				}
			}
		}
	}

	/**
	 * 手动停止定时程序管理线程
	 */
	public void stopAutoThreadManager() {
	}

	/**
	 * 手动启动一个定时线程
	 * 
	 * @param 定时线程的
	 *            完全限定名
	 */
	public static String startAutoRunThread(String className) {
		String result = "任务启动失败";
		synchronized (AutoThreadManager.lock) {
			for (AutoThread run : AutoThreadManager.list) {
				if (!run.getClass().getName().equals(className.trim())) {
					continue;
				}

				if (run.isRunning()) {
					result = "任务运行中";
				} else {
					// 启动匹配的自动运行程序
					new Thread(run).start();
					run.setRunning(true);
					result = "任务启动成功";
				}
				break;
			}
		}
		return result;
	}

	/**
	 * 查询指定任务的运行状态
	 * 
	 * @param 定时线程的
	 *            完全限定名
	 */
	public static String getTaskStatusByClassName(String className) {
		for (int i = 0; i < AutoThreadManager.list.size(); i++) {
			AutoThread run = AutoThreadManager.list.get(i);
			if (!run.getClass().getName().equals(className.trim())) {
				continue;
			}
			return run.isRunning() == false ? "该定时线程未运行" : "该定时线程正在运行";
		}
		return "未找到对应定时线程";
	}

	/**
	 * 查询所有自动运行任务 的详细信息
	 */
	public static String allAutoRunTaskDetail() {
		StringBuffer buf = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		buf.append("{\"list\": [");
		for (int i = 0; i < AutoThreadManager.list.size(); i++) {
			AutoThread run = AutoThreadManager.list.get(i);
			buf.append("{\"id\":\"" + run.getId() + "\",\"description\":\"" + run.getDescription() + "\",\"class\":\""
					+ run.getClass().getName() + "\",\"上次运行时间\":\""
					+ (run.getProviousStartTime() == null ? "从未运行" : sdf.format(run.getProviousStartTime().getTime()))
					+ "\"}");
			if (i + 1 < AutoThreadManager.list.size()) {
				buf.append(",");
			}
		}
		buf.append("]}");
		return buf.toString();
	}
}

class DateUtil {
	/**
	 * 设置一日期时间到一天的开始
	 */
	public static void putDateToDayBegin(Calendar c) {
		c.set(Calendar.HOUR, c.getActualMinimum(Calendar.HOUR));
		c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
		c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
		c.set(Calendar.AM_PM, Calendar.AM);
	}
	
	/**
	 * 设置一日期时间到当年的开始
	 * @return 
	 */
	public static void putDateToYearBegin(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		calendar.clear();  
        calendar.set(Calendar.YEAR, year);
	}
	/**
	 * 设置一日期时间到当月的开始
	 * @return 
	 */
	public static void putDateToMonthBegin(Calendar c) {
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR, c.getActualMinimum(Calendar.HOUR));
		c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
		c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
		c.set(Calendar.AM_PM, Calendar.AM);
	}
	
	
	/**
	 * 将Calendar转为String
	 */
	public static String parseCalendarToString(Calendar calendar){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(calendar.getTime());
	}
}