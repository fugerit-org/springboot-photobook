package org.fugerit.java.demo.springboot.photobook.service.mongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.fugerit.java.core.lang.helpers.StringUtils;

/*
 
 this is the aggregation for the shell : 
 
[
  {
    $match: {
      photobookId: "springio23",
    },
  },
  {
    $sort:
      {
        imageId: 1,
      },
  },
  {
    $set: {
      info: {
        $ifNull: ["$labels.en", "$labels.def"],
      },
    },
  },
  {
    $project: {
	  _id:0,
	  imageId:1,
	  author:1,
	  type:1,
	  info:1
    },
  },
  {
    $facet: {
      metadata: [
        {
          $count: "total",
        },
        {
          $addFields: {
            page: NumberInt(1),
          },
        },
      ],
      data: [
        {
          $skip: 0,
        },
        {
          $limit: 30,
        },
      ],
    },
  },
]
 
 */

public class PhotobookImagesAggregation {

	public static List<Document> getAggregation( String photobookId, String langCode, int perPage, int currentPage, String text ) {
		/*
		 * could be improved by limiting the result before manipulation,
		 * but to do that it would be probably become more complex.
		 * For relatively small data set it should be fine.
		 */
		List<Document> agg = new ArrayList<>();
		// step 1 - optional atlas search
		if ( StringUtils.isNotEmpty( text ) ) {
			agg.add( new Document("$search", 
					    new Document("index", "images_search_index")
					            .append("text", 
					    new Document("query", text)
					                .append("path", 
					    new Document("wildcard", "*")))) );
		}
		// step 2 - filter photobook
		agg.add( new Document("$match", new Document("photobookId", photobookId) ) );
		// step 3 - sort
		agg.add( new Document("$sort", new Document("imageId", 1L) ) );
		// step 4 - set language info
		agg.add( new Document("$set", 
			    new Document("info", 
			    new Document("$ifNull", Arrays.asList("$labels."+langCode, "$labels.def")))) );
		// step 5 - fields projections
		agg.add( new Document("$project", 
	    		   new Document("_id", 0L)
	               .append("imageId", 1L)
	               .append("author", 1L)
	               .append("type", 1L)
	               .append("info", 1L)) );
		// step 6 - add metadata
		agg.add( new Document(  new Document("$facet", 
			    new Document("metadata", Arrays.asList(new Document("$count", "total"), 
		                new Document("$addFields", 
		                new Document("page", currentPage))))
		            .append("data", Arrays.asList(new Document("$skip", (perPage*(currentPage-1))), 
		                new Document("$limit", perPage))))) );
		return agg;
	}
	
}
