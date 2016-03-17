package com.galaxyinternet.bo;

import java.util.ArrayList;
import java.util.List;

import com.galaxyinternet.model.hr.PersonInvest;
import com.galaxyinternet.model.hr.PersonLearn;
import com.galaxyinternet.model.hr.PersonResumetc;
import com.galaxyinternet.model.hr.PersonWork;
import com.galaxyinternet.model.project.PersonPool;

public class PersonResumetcBo extends PersonResumetc {

	List<PersonLearn> personLearnList = new ArrayList<PersonLearn>();
	List<PersonInvest> personInvestList = new ArrayList<PersonInvest>();
	List<PersonWork> personWorkList = new ArrayList<PersonWork>();
	
	PersonPool personPool = new PersonPool();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<PersonLearn> getPersonLearnList() {
		return personLearnList;
	}
	public void setPersonLearnList(List<PersonLearn> personLearnList) {
		this.personLearnList = personLearnList;
	}
	public List<PersonInvest> getPersonInvestList() {
		return personInvestList;
	}
	public void setPersonInvestList(List<PersonInvest> personInvestList) {
		this.personInvestList = personInvestList;
	}
	public List<PersonWork> getPersonWorkList() {
		return personWorkList;
	}
	public void setPersonWorkList(List<PersonWork> personWorkList) {
		this.personWorkList = personWorkList;
	}
	public PersonPool getPersonPool() {
		return personPool;
	}
	public void setPersonPool(PersonPool personPool) {
		this.personPool = personPool;
	}

	
	
}
