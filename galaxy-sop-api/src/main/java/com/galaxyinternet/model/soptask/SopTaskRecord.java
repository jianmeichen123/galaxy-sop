package com.galaxyinternet.model.soptask;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class SopTaskRecord extends BaseEntity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long taskId;
	private Long beforeUid;
	private Long beforeDepId;
	private Long afterUid;
	private Long afterDepId;
	private String reason;
	private Integer recordType;
	private Integer isDel = 0;
	private Long createdUid;
	private Long updatedUid;
	public Long getTaskId()
	{
		return taskId;
	}
	public void setTaskId(Long taskId)
	{
		this.taskId = taskId;
	}
	public Long getBeforeUid()
	{
		return beforeUid;
	}
	public void setBeforeUid(Long beforeUid)
	{
		this.beforeUid = beforeUid;
	}
	public Long getBeforeDepId()
	{
		return beforeDepId;
	}
	public void setBeforeDepId(Long beforeDepId)
	{
		this.beforeDepId = beforeDepId;
	}
	public Long getAfterUid()
	{
		return afterUid;
	}
	public void setAfterUid(Long afterUid)
	{
		this.afterUid = afterUid;
	}
	public Long getAfterDepId()
	{
		return afterDepId;
	}
	public void setAfterDepId(Long afterDepId)
	{
		this.afterDepId = afterDepId;
	}
	public String getReason()
	{
		return reason;
	}
	public void setReason(String reason)
	{
		this.reason = reason;
	}
	public Integer getRecordType()
	{
		return recordType;
	}
	public void setRecordType(Integer recordType)
	{
		this.recordType = recordType;
	}
	public Integer getIsDel()
	{
		return isDel;
	}
	public void setIsDel(Integer isDel)
	{
		this.isDel = isDel;
	}
	public Long getCreatedUid()
	{
		return createdUid;
	}
	public void setCreatedUid(Long createdUid)
	{
		this.createdUid = createdUid;
	}
	public Long getUpdateUid()
	{
		return updatedUid;
	}
	public void setUpdatedUid(Long updatedUid)
	{
		this.updatedUid = updatedUid;
	}
	
	public enum RecordType
	{
		TRANSFER(1),
		ASSIGN(2),
		GIVEUP(3);
		private Integer code;
		private RecordType(Integer code)
		{
			this.code = code;
		}
		public Integer getCode()
		{
			return code;
		}
		
	}
}
