package com.galaxyinternet.common.enums;


public class EnumUtil {
	
	public static int getCodeByCareerline(long id){
		if(id == CodeEnum.o2oDS.getId()){
			return CodeEnum.o2oDS.getCode();
		}else if(id == CodeEnum.bigdata.getId()){
			return CodeEnum.bigdata.getCode();
		}else if(id == CodeEnum.internetEducation.getId()){
			return CodeEnum.internetEducation.getCode();
		}else if(id == CodeEnum.internetEducation2.getId()){
			return CodeEnum.internetEducation2.getCode();
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
		}else if(id == CodeEnum.internetMedical2.getId()){
			return CodeEnum.internetMedical2.getCode();
		}else if(id == CodeEnum.virtualReality.getId()){
			return CodeEnum.virtualReality.getCode();
		}else if(id == CodeEnum.RKo2o.getId()){
			return CodeEnum.RKo2o.getCode();
		}else if(id == CodeEnum.RKjr.getId()){
			return CodeEnum.RKjr.getCode();
		}else if(id == CodeEnum.RKly.getId()){
			return CodeEnum.RKly.getCode();
		}else if(id == CodeEnum.RKcy.getId()){
			return CodeEnum.RKcy.getCode();
		}else if(id == CodeEnum.RKjy.getId()){
			return CodeEnum.RKjy.getCode();
		}else if(id == CodeEnum.RKny.getId()){
			return CodeEnum.RKny.getCode();
		}else if(id == CodeEnum.RKyl.getId()){
			return CodeEnum.RKyl.getCode();
		}else if(id == CodeEnum.RKzn.getId()){
			return CodeEnum.RKzn.getCode();
		}else if(id == CodeEnum.RKgh.getId()){
			return CodeEnum.RKgh.getCode();
		}else if(id == CodeEnum.RKfz.getId()){
			return CodeEnum.RKfz.getCode();
		}else if(id == CodeEnum.RKdc.getId()){
			return CodeEnum.RKdc.getCode();
		}else if(id == CodeEnum.RKdsj.getId()){
			return CodeEnum.RKdsj.getCode();
		}else if(id == CodeEnum.RKqyfw.getId()){
			return CodeEnum.RKqyfw.getCode();
		}else if(id == CodeEnum.RKmt.getId()){
			return CodeEnum.RKmt.getCode();
		}else if(id == CodeEnum.RKjk.getId()){
			return CodeEnum.RKjk.getCode();
		}else if(id == CodeEnum.RKqc.getId()){
			return CodeEnum.RKqc.getCode();
		}else if(id == CodeEnum.RKzt.getId()){
			return CodeEnum.RKzt.getCode();
		}else if(id == CodeEnum.CBLo2o.getId()){
			return CodeEnum.CBLo2o.getCode();
		}else if(id == CodeEnum.CBLjr.getId()){
			return CodeEnum.CBLjr.getCode();
		}else if(id == CodeEnum.CBLly.getId()){
			return CodeEnum.CBLly.getCode();
		}else if(id == CodeEnum.CBLcy.getId()){
			return CodeEnum.CBLcy.getCode();
		}else if(id == CodeEnum.CBLjy.getId()){
			return CodeEnum.CBLjy.getCode();
		}else if(id == CodeEnum.CBLny.getId()){
			return CodeEnum.CBLny.getCode();
		}else if(id == CodeEnum.CBLyl.getId()){
			return CodeEnum.CBLyl.getCode();
		}else if(id == CodeEnum.CBLzn.getId()){
			return CodeEnum.CBLzn.getCode();
		}else if(id == CodeEnum.CBLgh.getId()){
			return CodeEnum.CBLgh.getCode();
		}else if(id == CodeEnum.CBLfz.getId()){
			return CodeEnum.CBLfz.getCode();
		}else if(id == CodeEnum.CBLdc.getId()){
			return CodeEnum.CBLdc.getCode();
		}else if(id == CodeEnum.CBLdsj.getId()){
			return CodeEnum.CBLdsj.getCode();
		}else if(id == CodeEnum.CBLqyfw.getId()){
			return CodeEnum.CBLqyfw.getCode();
		}else if(id == CodeEnum.CBLmt.getId()){
			return CodeEnum.CBLmt.getCode();
		}else if(id == CodeEnum.CBLjk.getId()){
			return CodeEnum.CBLjk.getCode();
		}else if(id == CodeEnum.CBLqc.getId()){
			return CodeEnum.CBLqc.getCode();
		}else{
			return 99;
		}
	}
	
	public static String getCareerline(long id){
		if(id == CodeEnum.o2oDS.getId()){
			return CodeEnum.o2oDS.getCareerLine();
		}else if(id == CodeEnum.bigdata.getId()){
			return CodeEnum.bigdata.getCareerLine();
		}else if(id == CodeEnum.internetEducation.getId()){
			return CodeEnum.internetEducation.getCareerLine();
		}else if(id == CodeEnum.internetSteel.getId()){
			return CodeEnum.internetSteel.getCareerLine();
		}else if(id == CodeEnum.socialMedia.getId()){
			return CodeEnum.socialMedia.getCareerLine();
		}else if(id == CodeEnum.internetAgriculture.getId()){
			return CodeEnum.internetAgriculture.getCareerLine();
		}else if(id == CodeEnum.internetChemical.getId()){
			return CodeEnum.internetChemical.getCareerLine();
		}else if(id == CodeEnum.internetDigital.getId()){
			return CodeEnum.internetDigital.getCareerLine();
		}else if(id == CodeEnum.internetCatering.getId()){
			return CodeEnum.internetCatering.getCareerLine();
		}else if(id == CodeEnum.internetClothing.getId()){
			return CodeEnum.internetClothing.getCareerLine();
		}else if(id == CodeEnum.internetLand.getId()){
			return CodeEnum.internetLand.getCareerLine();
		/*}else if(id == CodeEnum.intelligentDevices.getId()){
			return CodeEnum.intelligentDevices.getCode();*/
		}else if(id == CodeEnum.internetFinance.getId()){
			return CodeEnum.internetFinance.getCareerLine();
		}else if(id == CodeEnum.internetCar.getId()){
			return CodeEnum.internetCar.getCareerLine();
		/*}else if(id == CodeEnum.internetOfThings.getId()){
			return CodeEnum.internetOfThings.getCode();*/
		}else if(id == CodeEnum.internetMedical.getId()){
			return CodeEnum.internetMedical.getCareerLine();
		}else if(id == CodeEnum.internetTourism.getId()){
			return CodeEnum.internetTourism.getCareerLine();
		}else if(id == CodeEnum.artificialIntelligence.getId()){
			return CodeEnum.artificialIntelligence.getCareerLine();
		}else if(id == CodeEnum.rongKuai.getId()){
			return CodeEnum.rongKuai.getCareerLine();
		}else if(id == CodeEnum.internetMedical2.getId()){
			return CodeEnum.internetMedical2.getCareerLine();
		}else if(id == CodeEnum.RKo2o.getId()){
			return CodeEnum.RKo2o.getCareerLine();
		}else if(id == CodeEnum.RKjr.getId()){
			return CodeEnum.RKjr.getCareerLine();
		}else if(id == CodeEnum.RKly.getId()){
			return CodeEnum.RKly.getCareerLine();
		}else if(id == CodeEnum.RKcy.getId()){
			return CodeEnum.RKcy.getCareerLine();
		}else if(id == CodeEnum.RKjy.getId()){
			return CodeEnum.RKjy.getCareerLine();
		}else if(id == CodeEnum.RKny.getId()){
			return CodeEnum.RKny.getCareerLine();
		}else if(id == CodeEnum.RKyl.getId()){
			return CodeEnum.RKyl.getCareerLine();
		}else if(id == CodeEnum.RKzn.getId()){
			return CodeEnum.RKzn.getCareerLine();
		}else if(id == CodeEnum.RKgh.getId()){
			return CodeEnum.RKgh.getCareerLine();
		}else if(id == CodeEnum.RKfz.getId()){
			return CodeEnum.RKfz.getCareerLine();
		}else if(id == CodeEnum.RKdc.getId()){
			return CodeEnum.RKdc.getCareerLine();
		}else if(id == CodeEnum.RKdsj.getId()){
			return CodeEnum.RKdsj.getCareerLine();
		}else if(id == CodeEnum.RKqyfw.getId()){
			return CodeEnum.RKqyfw.getCareerLine();
		}else if(id == CodeEnum.RKmt.getId()){
			return CodeEnum.RKmt.getCareerLine();
		}else if(id == CodeEnum.RKjk.getId()){
			return CodeEnum.RKjk.getCareerLine();
		}else if(id == CodeEnum.RKqc.getId()){
			return CodeEnum.RKqc.getCareerLine();
		}else if(id == CodeEnum.RKzt.getId()){
			return CodeEnum.RKzt.getCareerLine();
		}else if(id == CodeEnum.CBLo2o.getId()){
			return CodeEnum.CBLo2o.getCareerLine();
		}else if(id == CodeEnum.CBLjr.getId()){
			return CodeEnum.CBLjr.getCareerLine();
		}else if(id == CodeEnum.CBLly.getId()){
			return CodeEnum.CBLly.getCareerLine();
		}else if(id == CodeEnum.CBLcy.getId()){
			return CodeEnum.CBLcy.getCareerLine();
		}else if(id == CodeEnum.CBLjy.getId()){
			return CodeEnum.CBLjy.getCareerLine();
		}else if(id == CodeEnum.CBLny.getId()){
			return CodeEnum.CBLny.getCareerLine();
		}else if(id == CodeEnum.CBLyl.getId()){
			return CodeEnum.CBLyl.getCareerLine();
		}else if(id == CodeEnum.CBLzn.getId()){
			return CodeEnum.CBLzn.getCareerLine();
		}else if(id == CodeEnum.CBLgh.getId()){
			return CodeEnum.CBLgh.getCareerLine();
		}else if(id == CodeEnum.CBLfz.getId()){
			return CodeEnum.CBLfz.getCareerLine();
		}else if(id == CodeEnum.CBLdc.getId()){
			return CodeEnum.CBLdc.getCareerLine();
		}else if(id == CodeEnum.CBLdsj.getId()){
			return CodeEnum.CBLdsj.getCareerLine();
		}else if(id == CodeEnum.CBLqyfw.getId()){
			return CodeEnum.CBLqyfw.getCareerLine();
		}else if(id == CodeEnum.CBLmt.getId()){
			return CodeEnum.CBLmt.getCareerLine();
		}else if(id == CodeEnum.CBLjk.getId()){
			return CodeEnum.CBLjk.getCareerLine();
		}else if(id == CodeEnum.CBLqc.getId()){
			return CodeEnum.CBLqc.getCareerLine();
		}else{
			return "";
		}
	}


}
