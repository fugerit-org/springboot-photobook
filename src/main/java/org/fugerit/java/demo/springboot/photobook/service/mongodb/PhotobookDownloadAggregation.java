package org.fugerit.java.demo.springboot.photobook.service.mongodb;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;

/**
 * MongoDB aggregation to download photobook
 *

 <code>
[
 {
   $match:
     {
       imageId: 1000,
       photobookId: "springio23",
     },
 },
 {
   $project:
     {
       _id: 0,
       base64: 1,
     },
 },
]
 </code>

*/

public class PhotobookDownloadAggregation {

	private PhotobookDownloadAggregation() {}

	public static List<Document> getAggregation( String photobookId, String imageId ) {
		/*
		 * could be improved by limiting the result before manipulation,
		 * but to do that it would be probably become more complex.
		 * For relatively small data set it should be fine.
		 */
		return Arrays.asList(new Document("$match", 
			    new Document("imageId", Integer.parseInt( imageId ))
	            .append("photobookId", photobookId )), 
	    new Document("$project", 
	    new Document("_id", 0L)
	            .append("base64", 1L)));
	}
	
}
