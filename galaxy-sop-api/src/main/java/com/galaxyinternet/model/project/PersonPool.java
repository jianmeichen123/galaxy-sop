package com.galaxyinternet.model.project;

import java.util.Date;
import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.model.hr.PersonLearn;
import com.galaxyinternet.model.hr.PersonWork;

public class PersonPool extends PagableEntity{
	private static final long serialVersionUID = 1L;
	
	public final static String ID = "团队成员id";
	public final static String PERSONNAME = "团队成员名称";
	private Long tid;
	/*uuid*/
	private String uuid;
	/*草稿箱：是否可见*/
	private int tempStatus;
	/*姓名*/
    private String personName;
    /*性别*/
    private Integer personSex;
    /*年龄*/
    private Integer personAge;
    /*最高学历*/
    private String highestDegree;
    /*工龄*/
    private Integer workTime;
    /*当前职务*/
    private String personDuties;
    /*生日*/
    private Date personBirthday;
    /*身份证号码*/
    private String personIdcard;
    /*手机号码*/
    private String personTelephone;
    /*是否为联系人*/
    private int isContacts;
    /*邮箱地址*/
    private String personEmail;
    /*性格特点*/
    private String personCharacter;
    /*优势*/
    private String personGoodness;
    /*劣势*/
    private String personDisparity;
    /*沟通能力*/
    private String talkAbility;
    /*团队协作能力*/
    private String teamAbility;
    /*核心竞争力*/
    private String businessStrength;
    /*是否空闲*/
    private Integer free;
    /*团队角色*/
    private String teamRole;
    /*成员关系*/
    private String memberRelation;
    /*是否有过劳务纠纷*/
    private Integer laborDispute;
    /*能力匹配评星*/
    private Integer abilityStar;
    /*评级评星*/
    private Integer levelStar;
    /*最终评语*/
    private String endComment;
    private Long createId;
    /*备注*/
    private String remark;
    
    /*团队成员的学习经历*/
	private List<PersonLearn> plc;
	/*团队成员的工作经历*/
	private List<PersonWork> pwc;
    
    //添加的 时间字段
    private String personBirthdayStr;
    private String formatAgeStr;
    private String formatWorkTime;
    
    //虚拟扩充字段
    private Long projectId;
    
    
    
    
    
	public String getFormatWorkTime() {
		return formatWorkTime;
	}
	public void setFormatWorkTime(String formatWorkTime) {
		this.formatWorkTime = formatWorkTime;
	}
	
	public String getFormatAgeStr() {
		return formatAgeStr;
	}
	public void setFormatAgeStr(String formatAgeStr) {
		this.formatAgeStr = formatAgeStr;
	}
	
	
	public String getPersonBirthdayStr() {
		return personBirthdayStr;
	}
	public void setPersonBirthdayStr(String personBirthdayStr) {
		this.personBirthdayStr = personBirthdayStr;
	}
	public Long getTid() {
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getTempStatus() {
		return tempStatus;
	}
	public void setTempStatus(int tempStatus) {
		this.tempStatus = tempStatus;
	}
	public String getPersonName() {
        return personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }

    public Integer getPersonSex() {
        return personSex;
    }

    public void setPersonSex(Integer personSex) {
        this.personSex = personSex;
    }

    public Integer getPersonAge() {
    	if(this.formatAgeStr != null && !"".equals(this.formatAgeStr.trim())){
			this.personAge = Integer.parseInt(this.formatAgeStr.trim());
		}
		return personAge;
	}

	public void setPersonAge(Integer personAge) {
		this.personAge = personAge;
	}

	public String getHighestDegree() {
		return highestDegree;
	}

	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}

	public Integer getWorkTime() {
		if(this.formatWorkTime != null && !"".equals(this.formatWorkTime.trim())){
			this.workTime = Integer.parseInt(this.formatWorkTime.trim());
		}
		return workTime;
	}

	public void setWorkTime(Integer workTime) {
		this.workTime = workTime;
	}

	public String getPersonDuties() {
		return personDuties;
	}

	public void setPersonDuties(String personDuties) {
		this.personDuties = personDuties;
	}

	public Date getPersonBirthday() {
        return personBirthday;
    }

    public void setPersonBirthday(Date personBirthday) {
        this.personBirthday = personBirthday;
    }

    public String getPersonIdcard() {
        return personIdcard;
    }

    public void setPersonIdcard(String personIdcard) {
        this.personIdcard = personIdcard == null ? null : personIdcard.trim();
    }

    public String getPersonTelephone() {
        return personTelephone;
    }

    public void setPersonTelephone(String personTelephone) {
        this.personTelephone = personTelephone == null ? null : personTelephone.trim();
    }

    public int getIsContacts() {
		return isContacts;
	}
	public void setIsContacts(int isContacts) {
		this.isContacts = isContacts;
	}
	public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail == null ? null : personEmail.trim();
    }

    public String getPersonCharacter() {
        return personCharacter;
    }

    public void setPersonCharacter(String personCharacter) {
        this.personCharacter = personCharacter == null ? null : personCharacter.trim();
    }

    public String getPersonGoodness() {
        return personGoodness;
    }

    public void setPersonGoodness(String personGoodness) {
        this.personGoodness = personGoodness == null ? null : personGoodness.trim();
    }

    public String getPersonDisparity() {
        return personDisparity;
    }

    public void setPersonDisparity(String personDisparity) {
        this.personDisparity = personDisparity == null ? null : personDisparity.trim();
    }

    public String getBusinessStrength() {
        return businessStrength;
    }

    public void setBusinessStrength(String businessStrength) {
        this.businessStrength = businessStrength == null ? null : businessStrength.trim();
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public String getTeamRole() {
        return teamRole;
    }

    public void setTeamRole(String teamRole) {
        this.teamRole = teamRole == null ? null : teamRole.trim();
    }

    public String getMemberRelation() {
        return memberRelation;
    }

    public void setMemberRelation(String memberRelation) {
        this.memberRelation = memberRelation == null ? null : memberRelation.trim();
    }

    public Integer getLaborDispute() {
        return laborDispute;
    }

    public void setLaborDispute(Integer laborDispute) {
        this.laborDispute = laborDispute;
    }

    public Integer getAbilityStar() {
        return abilityStar;
    }

    public void setAbilityStar(Integer abilityStar) {
        this.abilityStar = abilityStar;
    }

    public Integer getLevelStar() {
        return levelStar;
    }

    public void setLevelStar(Integer levelStar) {
        this.levelStar = levelStar;
    }

    public String getEndComment() {
        return endComment;
    }

    public void setEndComment(String endComment) {
        this.endComment = endComment == null ? null : endComment.trim();
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

	public String getTalkAbility() {
		return talkAbility;
	}

	public void setTalkAbility(String talkAbility) {
		this.talkAbility = talkAbility;
	}

	public String getTeamAbility() {
		return teamAbility;
	}

	public void setTeamAbility(String teamAbility) {
		this.teamAbility = teamAbility;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public List<PersonLearn> getPlc() {
		return plc;
	}
	public List<PersonWork> getPwc() {
		return pwc;
	}
	public void setPlc(List<PersonLearn> plc) {
		this.plc = plc;
	}
	public void setPwc(List<PersonWork> pwc) {
		this.pwc = pwc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(uuid != null && obj != null && obj instanceof PersonPool && ((PersonPool) obj).getUuid() != null){
			return this.getUuid().equals(((PersonPool) obj).getUuid());
		}
		return super.equals(obj);
	}
}