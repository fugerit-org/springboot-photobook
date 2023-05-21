package org.fugerit.java.demo.springboot.photobook.service.mongodb;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;

/*
 
 this is the aggregation for the shell : 
 
 [
  {
    $set:
      {
        info: {
          $ifNull: ["$labels.en", "$labels.def"],
        },
      },
  },
  {
    $sort:
      {
        photobookId: 1,
      },
  },
  {
    $project:
      {
        _id: 0,
        photobookId: 1,
        author: 1,
        info: 1,
      },
  },
  {
    $facet:
      {
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
            $limit: 10,
          },
        ], 
      },
  },
]
 
 */

public class PhotobookListAggregation {

	public static List<Document> getAggregation( String langCode, int perPage, int currentPage ) {
		/*
		 * could be improved by limiting the result before manipulation,
		 * but to do that it would be probably become more complex.
		 * For relatively small data set it should be fine.
		 */
		return Arrays.asList(
				// step 1 - set the language info
				new Document("$set",
			    new Document("info", 
			    new Document("$ifNull", Arrays.asList("$labels."+langCode, "$labels.def")))), 
				// step 2 - sort
			    new Document("$sort", 
			    new Document("photobookId", 1L)), 
			    // step 3 - project needed fields
			    new Document("$project", 
			    new Document("_id", 0L)
			            .append("photobookId", 1L)
			            .append("author", 1L)
			            .append("info", 1L)), 
			    // step 4 - add metadata and limits
			    new Document("$facet", 
			    new Document("metadata", Arrays.asList(new Document("$count", "total"), 
			                new Document("$addFields", 
			                new Document("page", currentPage))))
			            .append("data", Arrays.asList(new Document("$skip", 0L), 
			                new Document("$limit", perPage)))));
	}
	
}
