package com.dmacjam.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class representing logged user.
 * @author Jakub
 *
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="`user`")
@NamedQuery(
		name=User.FIND_BY_EMAIL,
		query="SELECT u FROM User u WHERE u.email= :" +User.EMAIL_PARAM
		)
public class User implements Serializable{
	private static final long serialVersionUID = 779L;
	
	public static final String FIND_BY_EMAIL = "User.bestScore";
	public static final String EMAIL_PARAM = "email";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String token;
	
	@Column(unique=true)
	private String email;

	private String firstName;

	private String surname;
	
	@OneToMany(mappedBy="user")
	@OrderBy("result DESC")
	private List<Score> scores;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@JsonIgnore
	public List<Score> getScores() {
		if(scores == null)
		{
			scores = new ArrayList<Score>();
		}
		return scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
