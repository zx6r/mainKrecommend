package com.krecommend.mainws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Path("/newkruserid")
public class krnewuser {

public static String getNextSequence() {
		 
	
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
	}
	return "olmadi" ;
	
		}

	
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public static String newkruserid (){
		//	Görevimiz kruser collection ina yeni bir kayıt atıp en son oluşan yeni kruserid yi geri dönmek
		String  kruserid = "";
		// kruser tablosuna önce bir kayıt atalım, kayit oldukca basit
		
		BasicDBObject krUserRecord = new BasicDBObject("_id", getNextSequence())
			.append("xDate", new java.util.Date());
		
		try {
			MongoClient mongo = new MongoClient( "localhost" , 27017 );	//Baglandik 
			DB db = mongo.getDB( "krecommend" ); //mongo client objesini db ye bağlayalım
			DBCollection coll = db.getCollection("kruser");//collection sectik
			//coll.insert(krUserRecord);
			
			
			
			db.eval("db.kruser.insert({ _id: getNextSequence('kruserid'), xdate : new Date()})");
				
			
			BasicDBObject query = new BasicDBObject("_id", "kruserid");
			DBCursor cursor = coll.find(query) ;
	
			try {
				   while(cursor.hasNext()) {
					JSONParser jsonParser = new JSONParser();
											   try {
												JSONObject jsonObject = (JSONObject) jsonParser.parse(cursor.next().toString() );
												kruserid = jsonObject.get("seq").toString();
												kruserid += cursor.next();
											   	   }
											   catch (ParseException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
										  	        }
				   						   }
			   }
		       finally {
				 cursor.close();
				 	   }
			}
			       catch (Exception e) {
				// TODO: handle exception
			     
		}
		return kruserid;
	}
}

	
	
	