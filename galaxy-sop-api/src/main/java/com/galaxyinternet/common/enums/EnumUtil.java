package com.galaxyinternet.common.enums;


public class EnumUtil {
	
	public static int getCodeByCareerline(long id){
		if(id == CodeEnum.o2oDS.getId()){
			return CodeEnum.o2oDS.getCode();
		}else if(id == CodeEnum.bigdata.getId()){
			return CodeEnum.bigdata.getCode();
		}else if(id == CodeEnum.internetEducation.getId()){
			return CodeEnum.internetEducation.getCode();
		}else if(id == CodeEnum.internetSteel.getId()){
			return CodeEnum.internetSteel.getCode();
		}else if(id == CodeEnum.socialMedia.getId()){
			return CodeEnum.socialMedia.getCode();
		}else if(id == CodeEnum.internetAgriculture.getId()){
			return CodeEnum.internetAgriculture.getCode();
		}else if(id == CodeEnum.internetChemical.getId()){
			return CodeEnum.internetChemical.getCode();
		}else if(id == CodeEnum.internetDigital.getId()){
			return CodeEnum.internetDigital.getCode();
		}else if(id == CodeEnum.internetCatering.getId()){
			return CodeEnum.internetCatering.getCode();
		}else if(id == CodeEnum.internetClothing.getId()){
			return CodeEnum.internetClothing.getCode();
		}else if(id == CodeEnum.internetLand.getId()){
			return CodeEnum.internetLand.getCode();
		/*}else if(id == CodeEnum.intelligentDevices.getId()){
			return CodeEnum.intelligentDevices.getCode();*/
		}else if(id == CodeEnum.internetFinance.getId()){
			return CodeEnum.internetFinance.getCode();
		}else if(id == CodeEnum.internetCar.getId()){
			return CodeEnum.internetCar.getCode();
		/*}else if(id == CodeEnum.internetOfThings.getId()){
			return CodeEnum.internetOfThings.getCode();*/
		}else if(id == CodeEnum.internetMedical.getId()){
			return CodeEnum.internetMedical.getCode();
		}else if(id == CodeEnum.internetTourism.getId()){
			return CodeEnum.internetTourism.getCode();
		}else if(id == CodeEnum.artificialIntelligence.getId()){
			return CodeEnum.artificialIntelligence.getCode();
		}else if(id == CodeEnum.rongKuai.getId()){
			return CodeEnum.rongKuai.getCode();
		}else{
			return 99;
		}
	}

}
