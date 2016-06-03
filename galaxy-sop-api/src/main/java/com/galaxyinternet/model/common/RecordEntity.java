package com.galaxyinternet.model.common;

import com.galaxyinternet.common.enums.DictEnum.RecordType;
import com.galaxyinternet.framework.core.model.BaseEntity;

public class RecordEntity extends BaseEntity implements RecordBean {

	private static final long serialVersionUID = 1L;
	protected byte recordType = RecordType.PROJECT.getType();
	@Override
	public byte getRecordType() {
		// TODO Auto-generated method stub
		return recordType;
	}

	@Override
	public void setRecordType(byte recordType) {
		// TODO Auto-generated method stub
		this.recordType = recordType;
	}

}
