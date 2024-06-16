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

	private MongoTemplate mongoTemplate;

	public PhotobookService(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public Document listPhotobooks( String langCode, int perPage, int currentPage ) {
		MongoCollection<Document> collection = mongoTemplate.getCollection( "photobook_meta" );
		AggregateIterable<Document> result = collection.aggregate( PhotobookListAggregation.getAggregation(langCode, perPage, currentPage) );
		Document doc = null;
		try (MongoCursor<Document> cursor = result.iterator()) {
			if ( cursor.hasNext() ) {
				doc = cursor.next();
			}
		}
		log.info( "listPhotobooks, langCode : {}, perPage : {}, currentPage : {}", langCode, perPage, currentPage );
		return doc;
	}
	
	public Document listImages( String photobookId, String langCode, int perPage, int currentPage ) {
		MongoCollection<Document> collection = mongoTemplate.getCollection( "photobook_images" );
		AggregateIterable<Document> result = collection.aggregate( PhotobookImagesAggregation.getAggregation(photobookId, langCode, perPage, currentPage) );
		Document doc = null;
		try (MongoCursor<Document> cursor = result.iterator()) {
			if ( cursor.hasNext() ) {
				doc = cursor.next();
			}
		}
		log.info( "listImages, photobookId : {}, langCode : {}", photobookId, langCode );
		return doc;
	}
	

	@Cacheable({"images"})
	public byte[] downloadImage( String photobookId, String imageId ) {
		MongoCollection<Document> collection = mongoTemplate.getCollection( "photobook_images" );
		AggregateIterable<Document> result = collection.aggregate( PhotobookDownloadAggregation.getAggregation(photobookId, imageId) );
		byte[] data = null;
		try (MongoCursor<Document> cursor = result.iterator()) {
			if ( cursor.hasNext() ) {
				Document doc = cursor.next();
				String base64 = (String)doc.get( "base64" );
				data = Base64.getDecoder().decode( base64 );
				log.debug( "found! {}, {}, size: {}", photobookId, imageId, data.length );
			} else {
				log.debug( "not found! {}, {}", photobookId, imageId );
			}
		}
		log.info( "downloadImage photobookId : {}, imageId : {}", photobookId, imageId );
		return data;
	}
	
}
