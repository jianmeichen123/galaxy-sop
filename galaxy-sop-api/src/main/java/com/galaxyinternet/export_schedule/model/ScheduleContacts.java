package com.galaxyinternet.export_schedule.model;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class ScheduleContacts  extends PagableEntity{
	
    /**
	 * 开发日历联系人  2017/4/11
	 */
	private static final long serialVersionUID = 1L;

    private String name;

    private String phone1;

    private String phone2;

    private String phone3;

    private String email;

    private String post;

    private String company;

    private String address;

 

    private Long createdId;

   

    private Long updatedId;

    //添加是用来识别首字母的字段 进行姓名的排序2017/5/12
    private String firstpinyin;
    //是否删除的字段 2017/5/12
    private Integer isDel;
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1 == null ? null : phone1.trim();
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2 == null ? null : phone2.trim();
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3 == null ? null : phone3.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

 
    public Long getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Long createdId) {
        this.createdId = createdId;
    }

   
    public Long getUpdatedId() {
        return updatedId;
    }

    public void setUpdatedId(Long updatedId) {
        this.updatedId = updatedId;
    }

	public String getFirstpinyin() {
		return firstpinyin;
	}

	public void setFirstpinyin(String firstpinyin) {
		this.firstpinyin = firstpinyin;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
    
}