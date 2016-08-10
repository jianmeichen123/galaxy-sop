package com.galaxyinternet.model.chart;

/**
 * 部门（投资线）项目完成情况
 * @author wangkun
 *
 */
public class LineChart extends Chart{
	
	private static final long serialVersionUID = 1L;
	
	private Long total;	// 数据总数 （分页获取总数时使用）
	private Long department_id;	// 部门id
	private String department_name; // 部门名称
	private Integer target; // 目标数
	private Integer completed; // 完成数
	private Integer notcompleted; // 未完成数
	private Integer wb_completed; // 完成数-外部项目
	private Integer zj_completed; // 完成数-自建项目 
	private float rate; // 完成率
	private float wb_rate; // 完成率-外部项目
	private float zj_rate; // 完成率-自建项目
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Long getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(Long department_id) {
		this.department_id = department_id;
	}
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
		this.target = target;
	}
	public Integer getCompleted() {
		return completed;
	}
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	public Integer getNotcompleted() {
		return notcompleted;
	}
	public void setNotcompleted(Integer notcompleted) {
		this.notcompleted = notcompleted;
	}
	public Integer getWb_completed() {
		return wb_completed;
	}
	public void setWb_completed(Integer wb_completed) {
		this.wb_completed = wb_completed;
	}
	public Integer getZj_completed() {
		return zj_completed;
	}
	public void setZj_completed(Integer zj_completed) {
		this.zj_completed = zj_completed;
	}
	
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public float getWb_rate() {
		return wb_rate;
	}
	public void setWb_rate(float wb_rate) {
		this.wb_rate = wb_rate;
	}
	public float getZj_rate() {
		return zj_rate;
	}
	public void setZj_rate(float zj_rate) {
		this.zj_rate = zj_rate;
	}
	public void setZj_rate(Integer zj_rate) {
		this.zj_rate = zj_rate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	

}
