package com.krecommend.mainws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

@Path ("/nuit/{krUserid}")
public class newuseridtest {
	
	@GET
	@Produces (MediaType.TEXT_PLAIN)
	public  static String main (@PathParam("krUserid") String krUserid){
		//şimdi deniyoruz bakalim yeni kruserid yaratip üretebiliecek miyiz ? 
				
		try {
			MongoClient mongo = new MongoClient( "localhost" , 27017 );	//Baglandik 
			DB db = mongo.getDB( "krecommend" ); //mongo client objesini db ye bağlayalım
			DBCollection coll = db.getCollection("kruser");//collection sectik
			
			BasicDBObject query1 = new BasicDBObject ("_id" , "kruserid");
			BasicDBObject update1 = new BasicDBObject ("$inc" , new BasicDBObject("seq" , 1));
					
			JSONObject ret = (JSONObject) coll.findAndModify(query1, update1);
			return ret.toString();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			return "olmadi exeption oldu ";
		}			
	}    

}
