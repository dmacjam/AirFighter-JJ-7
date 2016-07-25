package com.dmacjam.session.beans;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.dmacjam.entity.User;

@Stateless
public class UserManagementFacadeBean implements UserManagementFacadeBeanRemote {
	
	private static final Logger LOG = Logger.getLogger(UserManagementFacadeBean.class.getName());
	
	@PersistenceContext
	private EntityManager entityManager;
	@EJB
	private UserManagementExecutiveBean userExecutive;
	
	
	@Override
	public void createUser(String email, String uid) {
		User user = new User();
		user.setEmail(email);
		user.setToken(uid);
		//TODO dalsie fieldy
		entityManager.persist(user);
		LOG.info("Saved user with email - "+email);
	}

	@Override
	public String getUser(long id) {
		User user = userExecutive.getUserById(id);
		return user.getSurname();
	}

	@Override
	public void setScore(long id, int score) {
		LOG.debug("Score "+score+" for user id: "+id);
		userExecutive.writeScoreForUserId(id,score);
	}


	@Override
	public long setUserByJson(User user) {
		LOG.debug("User in json saving");
		Query q = entityManager.createNamedQuery(User.FIND_BY_EMAIL);
		q.setParameter(User.EMAIL_PARAM, user.getEmail());
		List<User> result = q.getResultList();
		
		if (result.size() > 0){
			LOG.debug("User"+ result.get(0).getSurname() +" already exist");
			return result.get(0).getId();
		}else{
			entityManager.persist(user);
			LOG.info("Saved new user with email: "+user.getEmail()+" and name:"+user.getSurname());
			return user.getId();
		}
	}
	
	

}
