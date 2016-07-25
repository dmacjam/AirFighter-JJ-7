package com.dmacjam.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class that represent game score.
 * @author Jakub
 *
 */

@Entity
@NamedQuery(
		name=Score.BEST_SCORE,
		query="SELECT s FROM Score s ORDER BY s.result DESC"
		)
public class Score implements Serializable{
	private static final long serialVersionUID = 778L;
	
	public static final String BEST_SCORE = "Score.bestScore";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private int result;
	
	@Temporal(TemporalType.TIME)
	private Date datetime;
	
	@ManyToOne()
	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
