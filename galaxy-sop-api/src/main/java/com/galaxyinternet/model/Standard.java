package com.galaxyinternet.model;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class Standard extends PagableEntity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String moduleCode;
	private String moduleName;
	private String standardDetails;
	private Integer status = 1;
	private String statusDesc;
	private Integer isValid = 0;
	private Long createdId;
	private Long updatedId;

	public String getModuleCode()
	{
		return moduleCode;
	}

	public void setModuleCode(String moduleCode)
	{
		this.moduleCode = moduleCode;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getStandardDetails()
	{
		return standardDetails;
	}

	public void setStandardDetails(String standardDetails)
	{
		this.standardDetails = standardDetails;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getStatusDesc()
	{
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc)
	{
		this.statusDesc = statusDesc;
	}

	public Integer getIsValid()
	{
		return isValid;
	}

	public void setIsValid(Integer isValid)
	{
		this.isValid = isValid;
	}

	public Long getCreatedId()
	{
		return createdId;
	}

	public void setCreatedId(Long createdId)
	{
		this.createdId = createdId;
	}

	public Long getUpdatedId()
	{
		return updatedId;
	}

	public void setUpdatedId(Long updatedId)
	{
		this.updatedId = updatedId;
	}

	@Override
	public String toString()
	{
		return "Standard [moduleCode=" + moduleCode + ", moduleName=" + moduleName + ", standardDetails="
				+ standardDetails + ", status=" + status + ", isValid=" + isValid + ", createdId=" + createdId
				+ ", updatedId=" + updatedId + "]";
	}
	
	public enum StandardStatus
	{
		HIDE(0,"隐藏"),
		SHOW(1,"显示");
		private Integer code;
		private String desc;
		private StandardStatus(Integer code, String desc)
		{
			this.code = code;
			this.desc = desc;
		}
		public static String getDesc(Integer code)
		{
			StandardStatus[] values = StandardStatus.values();
			for(StandardStatus item : values)
			{
				if(item.code.intValue() == code.intValue())
				{
					return item.desc;
				}
			}
			return null;
		}
		
	}

}
