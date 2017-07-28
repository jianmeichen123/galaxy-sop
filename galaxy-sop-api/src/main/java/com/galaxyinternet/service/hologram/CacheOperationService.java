package com.galaxyinternet.service.hologram;

import com.galaxyinternet.model.hologram.InformationTitle;

public interface CacheOperationService {

	//void saveAreaKeyListByRedies(String hasKey_toAddkey, List<InformationTitle> area_tvalues);

	void refreshCache();

	void saveAreaInfoByRedies(String hasKey_toAddkey, InformationTitle area_tinfo);

	void saveAllByRedies(String code, InformationTitle title);
	
/*	public Map<String, List<InformationTitle>> getPagesAreacode();
	public void setPagesAreacode(Map<String, List<InformationTitle>> pagesAreacode);
	public List<String> getCacheAreascode();
	public void setCacheAreascode(List<String> cacheAreascode);
	public Map<Long, String> getTitleIdName();
	public void setTitleIdName(Map<Long, String> titleIdName);
	public Map<Long, String> getValueIdName();
	public void setValueIdName(Map<Long, String> valueIdName);*/
	
	
}
