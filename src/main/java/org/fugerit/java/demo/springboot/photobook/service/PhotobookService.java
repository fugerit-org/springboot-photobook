package org.fugerit.java.demo.springboot.photobook.service;

import java.util.Base64;

import org.bson.Document;
import org.fugerit.java.demo.springboot.photobook.service.mongodb.PhotobookDownloadAggregation;
import org.fugerit.java.demo.springboot.photobook.service.mongodb.PhotobookImagesAggregation;
import org.fugerit.java.demo.springboot.photobook.service.mongodb.PhotobookListAggregation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PhotobookService {

    @Autowired
    MongoTemplate mongoTemplate;
	
	public Document listPhotobooks( String langCode, int perPage, int currentPage ) {
		MongoCollection<Document> collection = mongoTemplate.getCollection( "photobook_meta" );
		AggregateIterable<Document> result = collection.aggregate( PhotobookListAggregation.getAggregation(langCode, perPage, currentPage) );
		Document doc = null;
		MongoCursor<Document> cursor = result.iterator();
		if ( cursor.hasNext() ) {
			doc = cursor.next();
		}
		return doc;
	}
	
	public Document listImages( String photobookId, String langCode, int perPage, int currentPage ) {
		MongoCollection<Document> collection = mongoTemplate.getCollection( "photobook_images" );
		AggregateIterable<Document> result = collection.aggregate( PhotobookImagesAggregation.getAggregation(photobookId, langCode, perPage, currentPage) );
		Document doc = null;
		MongoCursor<Document> cursor = result.iterator();
		if ( cursor.hasNext() ) {
			doc = cursor.next();
		}
		return doc;
	}
	

	@Cacheable({"images"})
	public byte[] downloadImage( String photobookId, String imageId ) {
		MongoCollection<Document> collection = mongoTemplate.getCollection( "photobook_images" );
		AggregateIterable<Document> result = collection.aggregate( PhotobookDownloadAggregation.getAggregation(photobookId, imageId) );
		byte[] data = null;
		MongoCursor<Document> cursor = result.iterator();
		if ( cursor.hasNext() ) {
			Document doc = cursor.next();
			String base64 = (String)doc.get( "base64" );
			data = Base64.getDecoder().decode( base64 );
			log.debug( "found! {}, {}, size: {}", photobookId, imageId, data.length );
		} else {
			log.debug( "not found! {}, {}", photobookId, imageId );
		}
		return data;¢
	}
	
}
