package com.dmacjam.session.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.dmacjam.entity.Score;
import com.dmacjam.results.Result;

@Stateless
public class ScoreManagementFacadeBean implements ScoreManagementFacadeBeanRemote{

	private static final Logger LOG = Logger.getLogger(ScoreManagementFacadeBean.class.getName());
	
	@PersistenceContext
	private EntityManager entityManager;
	@EJB
	private UserManagementExecutiveBean userExecutive;
	
	
	/**
	 * Return overall best score.
	 * TODO parameter na cislo stranky, napr. po 10
	 * @return
	 */
	@Override
	public List<Result> getBestScore(){
		Query q = entityManager.createNamedQuery(Score.BEST_SCORE);
		List<Score> scores = q.getResultList();
		List<Result> results = new ArrayList<Result>();
		for(Score s: scores){
			results.add(new Result(s.getUser().getSurname(), s.getResult()));
		}
		return results;
	}
	
	
	@PostConstruct
	public void initDb(){
		//System.out.println("Db inicializacia");
		//LOG.info("Logger test");
	}

	@Override
	public Score getScore(long id) {
		Score score = entityManager.find(Score.class, id);
		//TODO detach
		return score;
	}
}
