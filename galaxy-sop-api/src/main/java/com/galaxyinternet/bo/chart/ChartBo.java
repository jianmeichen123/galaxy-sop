package com.galaxyinternet.bo.chart;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.galaxyinternet.framework.core.utils.DateUtil;

/**
 * 图表查询条件
 * @author wangkun
 *
 */
public class ChartBo {
	
	/**
	 * 开始时间
	 */
	private String sdate;
	/**
	 * 结束时间
	 */
	private String edate;
	/**
	 * 开始月份
	 */
	private String sym = DateUtil.getFormatDateTime(this.getPreSym(new Date()),"yyyy-MM");
	/**
	 * 结束月份
	 */
	private String eym = DateUtil.getFormatDateTime(new Date(),"yyyy-MM");
	/**
	 * 项目类型（自建、外部引入）
	 * 数据字典
	 */
	private String projectType="-1";
	/**
	 * 项目进度，数据字典
	 */
	private String projectProgress="-1";
	/**
	 * 项目状态（其实是各阶段结论）
	 * 0待定，1通过，2否定关闭
	 */
	private String projectStatus="-1";
	/**
	 * 会议类型，数据字典
	 * meetingType:1 内评会
	 * meetingType:2 ceo评审
	 * meetingType:3 立项会
	 * meetingType:4 投决会
	 */
	private String meetingType="meetingType:1";
	/**
	 * 部门编号
	 */
	private Integer deptid=-1;
	/**
	 * 投资经理编号
	 */
	private long userid=-1;
	/**
	 * 筛选，有过立项会记录的传非1
	 */
	private Integer secLxh=-1;
	/**
	 * 筛选，有过投决会记录的传非1
	 */
	private Integer secTjh=-1;
	/**
	 * 过会率：metting_type
	 * 1. 立项会 2.投决会
	 */
	private Integer  mtype=1;
	/**
	 * 1分页模式  2返回全部
	 */
	private Integer model=1;
	/**
	 * 当model＝1时有效
	 * datatype=-1  返回总数
	 * datatype<>-1 返回数据行
	 */
	private Integer datatype=-1;
	/**
	 * 页码，从0开始
	 */
	private Integer pageNum=0;
	/**
	 * 每页多少条
	 */
	private Integer pageSize=20;
	/**
	 * 排序
	 */
	private String order;
	
	/**
	 * 类型标识，默认1
	 */
	private Integer type=1;
	
	/**
	 * 年份
	 * 当前只有整年的目标值,默认当前年份
	 */
	private Integer year = Integer.parseInt(DateUtil.getFormatDateTime(new Date(),"yyyy"));
	
	
	private Date getPreSym(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -12);
        return calendar.getTime();
    }
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getSdate() {
		return sdate;
	}
	/**
	 * 若sdate为null则获取默认开始日期
	 * type = 1 ; 当年第一天
	 * type ＝ 2　; 当月第一天
	 * type = 3 ; 当季度第一天
	 * type = 4 ; 本周第一天
	 * @return
	 */
	public String getDefaultSdate(Integer type){
		String rsdate;
		if(sdate!=null){
			rsdate = sdate;
		}else{
			if(type==1){
				rsdate = DateUtil.getFormatDateTime(new Date(),"yyyy-01-01");
			}else if(type==2){
				rsdate = DateUtil.getFormatDateTime(DateUtil.getCurrentDate(),"yyyy-mm-01");
			}else if(type==3){
				rsdate = sdate;
			}else if(type==4){
				rsdate = sdate;
			}else {
				rsdate = edate;
			}
		}
		this.setSdate(rsdate);
		return rsdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	/**
	 * 若edate为null则获取默认结束时间
	 * type = 1 ; 当天
	 * type ＝ 2　; 当月最后一天
	 * type = 3 ; 当季度最后一天
	 * type = 4 ; 本周最后一天
	 * @return
	 */
	public String getDefaultEdate(Integer type){
		String redate;
		if(edate!=null){
			redate = edate;
		}else{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar ca = Calendar.getInstance(); 
			if(type==1){
				redate = DateUtil.getFormatDateTime(new Date(),"yyyy-MM-dd");
			}else if(type==2){
				ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
				redate = format.format(ca.getTime());
			}else if(type==3){
				redate = sdate;
			}else if(type==4){
				redate = sdate;
			}else {
				redate = edate;
			}
		}
		this.setEdate(redate);
		return redate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getProjectProgress() {
		return projectProgress;
	}
	public void setProjectProgress(String projectProgress) {
		this.projectProgress = projectProgress;
	}
	public Integer getDeptid() {
		return deptid;
	}
	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Integer getModel() {
		return model;
	}
	public void setModel(Integer model) {
		this.model = model;
	}
	public Integer getDatatype() {
		return datatype;
	}
	public void setDatatype(Integer datatype) {
		this.datatype = datatype;
	}
	public Integer getMtype() {
		return mtype;
	}
	public void setMtype(Integer mtype) {
		this.mtype = mtype;
	}
	public String getSym() {
		return sym;
	}
	public void setSym(String sym) {
		this.sym = sym;
	}
	public String getEym() {
		return eym;
	}
	public void setEym(String eym) {
		this.eym = eym;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public Integer getSecLxh() {
		return secLxh;
	}

	public void setSecLxh(Integer secLxh) {
		this.secLxh = secLxh;
	}

	public Integer getSecTjh() {
		return secTjh;
	}

	public void setSecTjh(Integer secTjh) {
		this.secTjh = secTjh;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

}

