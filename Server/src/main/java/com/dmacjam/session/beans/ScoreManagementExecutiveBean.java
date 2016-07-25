package com.dmacjam.session.beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ScoreManagementExecutiveBean {
	
	@PersistenceContext
	private EntityManager entityManager;
	

}
