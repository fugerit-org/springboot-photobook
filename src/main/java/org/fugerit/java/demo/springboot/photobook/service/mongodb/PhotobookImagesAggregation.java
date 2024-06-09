package org.fugerit.java.demo.springboot.photobook.service.mongodb;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;

/**
 * MongoDB aggregation images photobook
 *

 <code>
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
 </code>
 
 */

public class PhotobookImagesAggregation {

	private PhotobookImagesAggregation() {}

	public static List<Document> getAggregation( String photobookId, String langCode, int perPage, int currentPage ) {
		/*
		 * could be improved by limiting the result before manipulation,
		 * but to do that it would be probably become more complex.
		 * For relatively small data set it should be fine.
		 */
		return Arrays.asList(
				// step 1 - filter
				new Document("$match", 
			    new Document("photobookId", photobookId)), 
				// step 2 - sort
			    new Document("$sort", 
			    new Document("imageId", 1L)), 
			    // step 3 - set language info
			    new Document("$set", 
			    new Document("info", 
			    new Document("$ifNull", Arrays.asList("$labels."+langCode, "$labels.def")))), 
			    // step 4 - fields projections
			    new Document("$project", 
			    		   new Document("_id", 0L)
			               .append("imageId", 1L)
			               .append("author", 1L)
			               .append("type", 1L)
			               .append("info", 1L)), 
			    // step 5 - add metadata
			    new Document("$facet", 
			    new Document("metadata", Arrays.asList(new Document("$count", "total"), 
			                new Document("$addFields", 
			                new Document("page", currentPage))))
			            .append("data", Arrays.asList(new Document("$skip", (perPage*(currentPage-1))), 
			                new Document("$limit", perPage)))));
	}
	
}
