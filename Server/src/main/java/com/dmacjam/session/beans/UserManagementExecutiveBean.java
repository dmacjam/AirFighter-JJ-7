package com.dmacjam.session.beans;

import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.dmacjam.entity.Score;
import com.dmacjam.entity.User;

@Stateless
@LocalBean
public class UserManagementExecutiveBean {
	private static final Logger LOG = Logger.getLogger(UserManagementExecutiveBean.class.getName());
	
	@PersistenceContext
	EntityManager entityManager;
	
	public User getUserById(long id){
		User user = entityManager.find(User.class,id);
		return user;
	}
	
	/**
	 * Persist score for user.
	 * @param id User id.
	 * @param result Score result.
	 */
	public void writeScoreForUserId(long id,int result){
		Score s = new Score();
		User u = getUserById(id);
		
		if(u == null){
			LOG.error("User with id "+id+" does not exists");
			return;
		}
		
		s.setDatetime(new Date());
		s.setResult(result);
		s.setUser(u);
		entityManager.persist(s);
		LOG.info("Score written: "+result);
	}
}
