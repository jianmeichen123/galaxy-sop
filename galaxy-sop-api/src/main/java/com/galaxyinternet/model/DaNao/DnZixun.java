package com.galaxyinternet.model.DaNao;


import com.galaxyinternet.framework.core.model.BaseEntity;

public class DnZixun extends BaseEntity {
	private static final long serialVersionUID = 1L;

	//升序:asc 降序:desc ,
	private String order;
	//排序字段
	private String orderBy;
	//当前页码
	private Integer pageNo;
	//每页记录数
	private Integer pageSize;

	//搜索关键字
	//private String keyword;

	private String code;
	private String href;     //资讯链接
	private String imgmd5;
	private String zixunImage; // 'http://static.galaxyinternet.com/img/news/'+code+'.PNG'

	private String title;    //资讯标题
	private String overview; //资讯概要
	private String auther; //资讯来源
	private String ctime;  //创建时间
	private String ctimeStr;



	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getImgmd5() {
		return imgmd5;
	}

	public void setImgmd5(String imgmd5) {
		this.imgmd5 = imgmd5;
	}

	public String getZixunImage() {
		return zixunImage;
	}

	public void setZixunImage(String zixunImage) {
		this.zixunImage = zixunImage;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getAuther() {
		return auther;
	}

	public void setAuther(String auther) {
		this.auther = auther;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getCtimeStr() {
		return ctimeStr;
	}

	public void setCtimeStr(String ctimeStr) {
		this.ctimeStr = ctimeStr;
	}
}