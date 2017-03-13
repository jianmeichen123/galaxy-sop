package com.galaxyinternet.bo.hologram;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Transient;

import com.galaxyinternet.framework.core.utils.StringEx;
import com.galaxyinternet.model.hologram.InformationTitle;

public class InformationTitleBo extends InformationTitle {
	private static final long serialVersionUID = 1L;

	
	
	
	private String idcodekey;

	//是否包含转义字符
	@Transient
	protected Boolean idcodeEscapeChar;
	
	public String getIdcodekey() {
		return idcodekey == null ? null : idcodekey.trim();
	}

	public void setIdcodekey(String idcodekey) {
		this.idcodekey = idcodekey == null ? null : idcodekey.trim();
	}
	
	public Boolean getIdcodeEscapeChar() {
		this.getIdcodeNewKeyword();
		return idcodeEscapeChar;
	}
	public void setIdcodeEscapeChar(Boolean idcodeEscapeChar) {
		this.idcodeEscapeChar = idcodeEscapeChar;
	}
	
	
	private void getIdcodeNewKeyword(){
		if(idcodeEscapeChar == null){
			idcodeEscapeChar = false;
		}
		if(StringUtils.isNotEmpty(idcodekey)&&!idcodeEscapeChar){
			String newkeyword = StringEx.checkSql(idcodekey);
			if(!idcodekey.equals(newkeyword)){
				this.setEscapeChar(true);
				this.setKeyword(newkeyword);
			}
		}
	}


	
	
	
	

	
	
	

}
