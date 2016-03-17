package com.galaxyinternet.common.enums;

public enum CodeEnum {
	
	o2oDS(2, "O2O及电商", 10),
	bigdata(3, "大数据云计算", 11),
	internetEducation(4, "互联网教育", 12),
	internetSteel(5, "互联网钢铁", 13),
	socialMedia(6, "媒体社交", 14),
	internetAgriculture(7, "互联网农业", 15),
	internetChemical(8, "互联网化工", 16),
	internetDigital(9, "数字娱乐", 17),
	internetCatering(10, "互联网餐饮", 18),
	internetClothing(11, "互联网服装", 19),
	internetLand(12, "互联网房地产", 20),
	intelligentDevices(13, "智能设备", 21),
	internetFinance(14, "互联网金融", 22),
	internetCar(15, "互联网汽车", 23),
	internetOfThings(16, "物联网", 24),
	internetMedical(17, "互联网医疗", 25), 
	internetTourism(18, "互联网旅游", 26),
	artificialIntelligence(19, "人工智能事业部", 27);
	
	
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
