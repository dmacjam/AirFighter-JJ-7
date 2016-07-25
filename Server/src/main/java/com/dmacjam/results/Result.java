package com.dmacjam.results;

import java.io.Serializable;

public class Result implements Serializable{
	private static final long serialVersionUID = 777L;
	private String name;
	private int result;
	
	public Result(String name,int result) {
		this.name = name;
		this.result = result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	

}
