package org.fugerit.java.demo.springboot.photobook.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.fugerit.java.demo.springboot.photobook.service.mongodb.PhotobookImagesAggregation;
import org.fugerit.java.demo.springboot.photobook.service.mongodb.PhotobookListAggregation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

@Service
public class PhotobookService {

    @Autowired
    MongoTemplate mongoTemplate;
	
	public List<Document> listPhotobooks( String langCode, int perPage, int currentPage ) {
		MongoCollection<Document> collection = mongoTemplate.getCollection( "photobook_meta" );
		AggregateIterable<Document> result = collection.aggregate( PhotobookListAggregation.getAggregation(langCode, perPage, currentPage) );
		List<Document> docs = new ArrayList<>();
		MongoCursor<Document> cursor = result.iterator();
		while ( cursor.hasNext() ) {
			docs.add( cursor.next() );
		}
		return docs;
	}
	
	public List<Document> listImages( String photobookId, String langCode, int perPage, int currentPage ) {
		MongoCollection<Document> collection = mongoTemplate.getCollection( "photobook_images" );
		AggregateIterable<Document> result = collection.aggregate( PhotobookImagesAggregation.getAggregation(photobookId, langCode, perPage, currentPage) );
		List<Document> docs = new ArrayList<>();
		MongoCursor<Document> cursor = result.iterator();
		while ( cursor.hasNext() ) {
			docs.add( cursor.next() );
		}
		return docs;
	}
	
}
