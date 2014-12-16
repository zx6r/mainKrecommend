package com.krecommend.mainws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/r2/{userid}/{productid}")
public class r2 {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public static String main(@PathParam("userid") String userid , @PathParam("productid") int productid ) {
		
		
		
		
		return userid;
		
	}
	
}
