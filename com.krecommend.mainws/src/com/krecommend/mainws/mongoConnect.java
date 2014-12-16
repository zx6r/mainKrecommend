
package com.krecommend.mainws;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
//import com.mongodb.BulkWriteOperation;
//import com.mongodb.BulkWriteResult;
//import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;





import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import com.mongodb.ParallelScanOptions;

@Path("/mongo/{userid}/{productid}")
public class mongoConnect {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public static String main(@PathParam("userid") String userid , @PathParam("productid") int productid ) 
	{
	String thehtml = "";
				try {
					MongoClient mongo = new MongoClient( "localhost" , 27017 );
					//Baglandik simdi ilk datamizi yazalim
					DB db = mongo.getDB( "test" ); //mongo client objesini db ye bağlayalım 
					DBCollection coll = db.getCollection("testData2");//
			
				//	BasicDBObject doc = new BasicDBObject("name", "MongoDB")
			    //    .append("type", "database")
			    //    .append("count", 1)
			    //    .append("info", new BasicDBObject("x", 203).append("y", 102));
			
				// coll.insert(doc);
			
			
					//DBObject groupFields = new BasicDBObject( "_id", "$name");
					//groupFields.put("maxValue", new BasicDBObject( "$max", "$userid"));
					//DBObject group = new BasicDBObject("$group", groupFields);
					
					
					// run aggregation
					
					//List<DBObject> pipeline = Arrays.asList(group);
					//AggregationOutput output = coll.aggregate(pipeline);
					
					//List<DBObject> pipeline = Arrays.asList( group);
					//AggregationOutput output = coll.aggregate(pipeline);
					
					
					
					//BasicDBObject doc = new BasicDBObject( )
					
					//DBObject myDoc = coll.aggregate( "{$group:{_id:"$name", "maxValue": {$max:"$userid"}}}" );
					

					//thehtml = output.toString();
					
					
						

					DBObject groupFields = new BasicDBObject( "_id", "$name");
					groupFields.put("maxValue", new BasicDBObject( "$max", "$userid"));
					DBObject group = new BasicDBObject("$group", groupFields);
					
				
					AggregationOutput output = coll.aggregate( group );
					
					
					//List<DBObject> pipeline1 = Arrays.asList( group);
					//AggregationOutput output = coll.aggregate(firstOp, additionalOps) (pipeline1);
					
					thehtml  = output.toString();
					//final String filePath = "C:\\Users\\katerina\\Desktop\\jsonTestFile.json";
					//FileReader reader = new FileReader(filePath);

					JSONParser jsonParser = new JSONParser();

		            try {
						JSONObject jsonObject = (JSONObject) jsonParser.parse(thehtml);
						//String maxuser = (String) jsonObject.get("maxValue");
						//thehtml += maxuser ;
						
		                JSONArray lang= (JSONArray) jsonObject.get("result");

//		                for(int i=0; i<lang.size(); i++){
//		    				thehtml +=  "The " + i + " element of the array: "+lang.get(i);
//		    			}
//		    			Iterator i = lang.iterator();
//
//		    			// take each value from the json array separately
//		    			while (i.hasNext()) {
//		    				JSONObject innerObj = (JSONObject) i.next();
//		    				thehtml += "language "+ innerObj.get("lang") + " with level " + innerObj.get("knowledge");
//		    			}
		                
		                JSONObject innerObj = (JSONObject) lang.get(0);
		                
		                String maxuser = innerObj.get("maxValue").toString() ;
		                
		                int num = Integer.parseInt(String.valueOf(maxuser).split("\\.")[0]);

		                int newNum = num +1;
		                
		                //thehtml += num + 1;
		                //Simdi elimizdeki sessionidye userid ekliyelim dbye kayıt edelim 
		            
		        		String sessionIdOnServer = "feıruh3489htklj";
							BasicDBObject doc = new BasicDBObject( "sessionid" , sessionIdOnServer)
					        .append("userid", newNum);
					        coll.insert (doc);
		                
		            } catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            

					
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				return "olmadi ... ";	
				}
				//return myDoc.toString();										
				//return "mongo oldu";
	return thehtml ;

	}	

}
