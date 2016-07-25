package com.dmacjam.session.beans;

import java.util.List;

import javax.ejb.Remote;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.dmacjam.entity.Score;
import com.dmacjam.results.Result;

@Remote
@Path("/")
public interface ScoreManagementFacadeBeanRemote {

	@GET
	@Path("scores/best")
	@Produces("application/json")
	public List<Result> getBestScore();

	@GET
	@Path("score/{id}")
	@Produces("application/json")
	public Score getScore(@PathParam("id") long id);
}
