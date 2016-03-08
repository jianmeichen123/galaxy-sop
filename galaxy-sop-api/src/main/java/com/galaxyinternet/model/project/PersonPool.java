package com.galaxyinternet.model.project;

import java.util.Date;

import com.galaxyinternet.framework.core.model.BaseEntity;
import com.galaxyinternet.framework.core.model.PagableEntity;

public class PersonPool extends PagableEntity{
	private static final long serialVersionUID = 1L;
	
    private String personName;
    private Integer personSex;
    private Integer personAge;
    private String highestDegree;
    private Integer workTime;
    private String personDuties;
    private Date personBirthday;
    private String personIdcard;
    private String personTelephone;
    private String personEmail;
    private String personCharacter;
    private String personGoodness;
    private String personDisparity;
    private Integer talkAbility;
    private Integer teamAbility;
    private String businessStrength;
    private Integer free;
    private String teamRole;
    private String memberRelation;
    private Integer laborDispute;
    private Integer abilityStar;
    private Integer levelStar;
    private String endComment;
    private Long createId;

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

    public Integer getTalkAbility() {
        return talkAbility;
    }

    public void setTalkAbility(Integer talkAbility) {
        this.talkAbility = talkAbility;
    }

    public Integer getTeamAbility() {
        return teamAbility;
    }

    public void setTeamAbility(Integer teamAbility) {
        this.teamAbility = teamAbility;
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
}