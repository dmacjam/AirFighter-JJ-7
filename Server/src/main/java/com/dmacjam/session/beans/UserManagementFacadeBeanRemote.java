package com.dmacjam.session.beans;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.dmacjam.entity.User;

@Remote
@Path("/")
public interface UserManagementFacadeBeanRemote {
	
	public void createUser(String email,String uid);
	
	@GET
	@Path("user/{id}")
	@Produces("application/json")
	public String getUser(@PathParam("id") long id);
	
	@POST
	@Path("user/{id}/score/{score}")
	public void setScore(@PathParam("id") long id, @PathParam("score") int score);
	
	@POST
	@Path("users-json")
	@Produces("application/json")
	@Consumes("application/json")
	public long setUserByJson(User user);

}
