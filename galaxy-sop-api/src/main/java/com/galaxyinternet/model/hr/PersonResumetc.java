package com.galaxyinternet.model.hr;


import java.util.List;

import com.galaxyinternet.framework.core.model.BaseEntity;
import com.galaxyinternet.model.project.PersonPool;

public class PersonResumetc extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6978761180569312218L;

	private PersonInvest personInvest;
	
	private List<PersonLearn> personLearn;
	
	private List<PersonWork> personWork;
	
	private PersonPool personPool;
	
	


	public PersonInvest getPersonInvest() {
		return personInvest;
	}


	public void setPersonInvest(PersonInvest personInvest) {
		this.personInvest = personInvest;
	}


	public List<PersonLearn> getPersonLearn() {
		return personLearn;
	}


	public void setPersonLearn(List<PersonLearn> personLearn) {
		this.personLearn = personLearn;
	}


	public List<PersonWork> getPersonWork() {
		return personWork;
	}


	public void setPersonWork(List<PersonWork> personWork) {
		this.personWork = personWork;
	}


	public PersonPool getPersonPool() {
		return personPool;
	}


	public void setPersonPool(PersonPool personPool) {
		this.personPool = personPool;
	}


	
}