package com.krecommend.mainws;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;


@Path("/record/{krUserid}/{productid}/{krCode}")
public class record {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public static String main(@PathParam("krUserid") String krUserid , @PathParam("productid") String productid , @PathParam("krCode") String krCode ) {
		// Gelen kruserid, productid ve krCode yi mongoDb ye yazacagiz burda

		//Ilk adim krUserid none mı değil mi ona bakacaz.
		
		if (krUserid.equals(new String("0")) ) {
		//krUserid none geldi yeni bir kurUserid yaratıp onu kullanmamız gerekecek.
		
			String newkruserid = getNewKrUserId();
			String result = recordTrackData(krCode,productid,newkruserid);
			
			return result;
			
			}
		else
		{
		 
			String result = recordTrackData(krCode,productid,krUserid);
			return result;
		}
		
		//long randomNum1 = (long) (2.2 + (long)(Math.random()* 3.8));
		//long randomNum2 = (long) (2.2 + (long)(Math.random()* 3.8));
		//int newNum = 0 ;	
		//String thehtml = "";
		//try { 
		//PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("c:\\Users\\Kivanc\\workspace\\isteoldu.txt", true)));
		//out.println(userid + "\t"+ productid +"\t"+ randomNum1 + "." + randomNum2);
		//String userid_long = userid;
		//userid_long = Long.valueOf(userid).longValue();
		
		//MongoClient mongo = new MongoClient( "localhost" , 27017 );	//Baglandik 
		
		//		DB db = mongo.getDB( "krecommend" ); //mongo client objesini db ye bağlayalım 
		//		DBCollection coll = db.getCollection("user");//collection sectik
					    
				
		//Burasının yazılması gerekli sessionidden aratıp user id bulucaz bulamazsak max userid yi tablodan alip 1 ekleyip user id yapacagiz
				//BasicDBObject query = new BasicDBObject("sessionid", userid);
				//DBCursor cursor = coll.find(query) ;
				
				//try {
				//	   while(cursor.hasNext()) {
						
						   // demek ki bu sessionid den birisi var . o zaman eski sessionidsinin userını bulmamız gerek 
						   
				//		JSONParser jsonParser = new JSONParser();
				//		   try {
				//			JSONObject jsonObject = (JSONObject) jsonParser.parse(cursor.next().toString() );
				//			kruserid = jsonObject.get("kruserid").toString();
						// Demek ki bu kullanıcı daha once gelmiş o yuzden eski useridsini kulanalım ...
							
				//		} catch (ParseException e) {
							// TODO Auto-generated catch block
				//			e.printStackTrace();
				//		}
						   
				//	       thehtml += cursor.next();
				//	   }
				//	} finally {
				//	   cursor.close();
				//}
			
		//max useridyi bulmaya calisiyoruz
		//DBObject groupFields = new BasicDBObject( "_id", "$name");
		//groupFields.put("maxValue", new BasicDBObject( "$max", "$userid"));
		//DBObject group = new BasicDBObject("$group", groupFields);
			
		//AggregationOutput output = coll.aggregate( group );
		
		//JSONParser jsonParser = new JSONParser();

        //try {
		//	JSONObject jsonObject = (JSONObject) jsonParser.parse(output.toString());
        //    JSONArray lang = (JSONArray) jsonObject.get("result");

        //    JSONObject innerObj = (JSONObject) lang.get(0);
        //    String maxuser = innerObj.get("maxValue").toString() ;
            
        //    int num = Integer.parseInt(String.valueOf(maxuser).split("\\.")[0]);

         //   newNum = num +1;
            
            //Simdi elimizdeki sessionidye userid ekliyelim dbye kayıt edelim 
        
    	//	String sessionIdOnServer = userid;
		//		BasicDBObject doc = new BasicDBObject( "sessionid" , sessionIdOnServer)
		//        .append("userid", newNum);
		//        coll.insert (doc);
		//        mongo.close();
		        
		        
            
       // } catch (ParseException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}

        // dosyaya da artik userid_long yerine userid yazalim . 
		//out.println(newNum + "\t"+ productid +"\t5.0");
		//out.close();
		
				//PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("c:\\Users\\Kivanc\\workspace\\isteoldu.txt", true)))) {
	         // siteid userid productid
	         //out.println(userid +"\t654654\t87987");
		//    return "oldu .... " + thehtml ;
		 //   } catch (IOException e) {
	   //return "olmadi ... "; 
	 //return   e.toString() ;
	        }

	private static String recordTrackData(String krCode, String productid, String newkruserid) {
		//db ye koyıt edeceğimiz document objesini oluşturalım
		
		BasicDBObject krRecord = new BasicDBObject("krCode", krCode )
		.append("productid", productid)
		.append("krUserid", newkruserid)
		.append("xDate", new java.util.Date());
try {
		MongoClient mongo = new MongoClient( "localhost" , 27017 );	//Baglandik 
		DB db = mongo.getDB( "krecommend" ); //mongo client objesini db ye bağlayalım
		DBCollection coll = db.getCollection("krRecord");//collection sectik
		coll.insert(krRecord);

		mongo.close();	
return "Oldu ..." ;
} catch (Exception e) {
// TODO: handle exception
e.printStackTrace();
return "Olmadı " + e.getMessage();
}

	}

private static String getNewKrUserId() {
		// TODO Auto-generated method stub
		
	String newKrUserID ="";
	//String incone = incKrUserID();
	String nextseq = getNextSequence();
	JSONParser jsonParser = new JSONParser();
	
	try {
		JSONObject jsonObject = (JSONObject) jsonParser.parse(nextseq);
		//String theseq =  jsonObject.get("seq").toString();
		String theseq = String.valueOf(jsonObject.get("seq")).split("\\.")[0];
		
		return theseq;
		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
	
	
	
	//int nextSeq = Integer.parseInt (nextseq) ;
	
	
	return newKrUserID;
		
			}
public static String getNextSequence (){
	try {
		MongoClient mongo = new MongoClient( "localhost" , 27017 );	//Baglandik 
		//DB db = mongo.getDB( "krecommend" ); //mongo client objesini db ye bağlayalım
		//DBCollection coll = db.getCollection("kruser");//collection sectik
		
		BasicDBObject query = new BasicDBObject ("_id" , "kruserid");
		BasicDBObject update = new BasicDBObject ( "$inc" , new BasicDBObject ("seq" , 1 ) );
		
		
		final DBObject modifiedDoc
	        = mongo.getDB("krecommend").getCollection("kruser").findAndModify(query,
	                                                                          null,
	                                                                          null,
	                                                                          false,
	                                                                          update,
	                                                                          true,
	                                                                          false);
		
		//JSONObject ret = (JSONObject) coll.findAndModify(query1, update1);
		return modifiedDoc.toString();
		
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	return "olmadi" ;
	
	
}



	}