package com.galaxyinternet.model.hr;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class PersonInvest extends BaseEntity {

	/**
	 * gxc
	 */
	private static final long serialVersionUID = 1L;

		private Long id; //人力_投资经历id 
		private Long personId;//关联人力资源id
		private String icompanyName;//公司名称
		private Double investmentAmount;//投资金额 investment_amount
		private Double shareRatio;//股权占比
		private String projectDirector;//项目负责人;
		private String emceedPosition;//担任职位
		private String telephone;//联系方式
		private String acompanyName;//a轮投资公司名称
		private Double ainvestmentAmount;//a轮投资金额
		private Double ashareRatio;//a轮股权占比
		private String atelephone;//a轮联系方式
		private String aprojectDirector;//a轮项目负责人;
		private String aemceedPosition;//a轮担任职位
		

		
		public String getAprojectDirector() {
			return aprojectDirector;
		}
		public void setAprojectDirector(String aprojectDirector) {
			this.aprojectDirector = aprojectDirector;
		}
		public String getAemceedPosition() {
			return aemceedPosition;
		}
		public void setAemceedPosition(String aemceedPosition) {
			this.aemceedPosition = aemceedPosition;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getPersonId() {
			return personId;
		}
		public void setPersonId(Long personId) {
			this.personId = personId;
		}

		public String getIcompanyName() {
			return icompanyName;
		}
		public void setIcompanyName(String icompanyName) {
			this.icompanyName = icompanyName;
		}
		public Double getInvestmentAmount() {
			return investmentAmount;
		}
		public void setInvestmentAmount(Double investmentAmount) {
			this.investmentAmount = investmentAmount;
		}
		
		public Double getShareRatio() {
			return shareRatio;
		}
		public void setShareRatio(Double shareRatio) {
			this.shareRatio = shareRatio;
		}
		public String getProjectDirector() {
			return projectDirector;
		}
		public void setProjectDirector(String projectDirector) {
			this.projectDirector = projectDirector;
		}
		public String getEmceedPosition() {
			return emceedPosition;
		}
		public void setEmceedPosition(String emceedPosition) {
			this.emceedPosition = emceedPosition;
		}
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public String getAcompanyName() {
			return acompanyName;
		}
		public void setAcompanyName(String acompanyName) {
			this.acompanyName = acompanyName;
		}
		public Double getAinvestmentAmount() {
			return ainvestmentAmount;
		}
		public void setAinvestmentAmount(Double ainvestmentAmount) {
			this.ainvestmentAmount = ainvestmentAmount;
		}
		public Double getAshareRatio() {
			return ashareRatio;
		}
		public void setAshareRatio(Double ashareRatio) {
			this.ashareRatio = ashareRatio;
		}
		public String getAtelephone() {
			return atelephone;
		}
		public void setAtelephone(String atelephone) {
			this.atelephone = atelephone;
		}
	
	
}
