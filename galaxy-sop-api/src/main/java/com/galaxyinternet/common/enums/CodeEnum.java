package com.galaxyinternet.common.enums;

public enum CodeEnum {
	
	o2oDS(11, "O2O及电商", 10),
	bigdata(8, "大数据云计算", 11),
	internetEducation(14, "互联网教育", 12),
	internetSteel(25, "互联网钢铁", 13),
	socialMedia(16, "媒体社交", 14),
	internetAgriculture(12, "互联网农业", 15),
	internetChemical(24, "互联网化工", 16),
	internetDigital(5, "数字娱乐", 17),
	internetCatering(7, "互联网餐饮", 18),
	internetClothing(15, "互联网服装", 19),
	internetLand(26, "互联网房地产", 20),
	/*intelligentDevices(13, "智能设备", 21),*/
	internetFinance(6, "互联网金融", 22),
	internetCar(17, "互联网汽车", 23),
	/*internetOfThings(16, "物联网", 24),*/
	internetMedical(9, "互联网医疗", 25), 
	internetTourism(10, "互联网旅游", 26),
	artificialIntelligence(13, "人工智能事业部", 27),
	enterpriseService(28, "企业服务", 28),
	rongKuai(29, "融快事业部", 29);
	
	
	private long id;
	private String careerLine;
	private int code;
	
	private CodeEnum(long id, String careerLine, int code){
		this.id = id;
		this.careerLine = careerLine;
		this.code = code;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCareerLine() {
		return careerLine;
	}
	public void setCareerLine(String careerLine) {
		this.careerLine = careerLine;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
