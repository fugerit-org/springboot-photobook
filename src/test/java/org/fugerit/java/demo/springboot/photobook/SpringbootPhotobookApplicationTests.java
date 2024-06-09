package org.fugerit.java.demo.springboot.photobook;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.fugerit.java.demo.springboot.photobook.dto.ResultDTO;
import org.fugerit.java.demo.springboot.photobook.rest.PhotobookViewController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

@Slf4j
@SpringBootTest
class SpringbootPhotobookApplicationTests {

	@Autowired
	private PhotobookViewController photobookViewController;

	@Autowired
	private Environment env;

	private static final String MONGO_DB_URI_PROP = "spring.data.mongodb.uri";

	final static MongoDBContainer mongoDBContainer = new MongoDBContainer( "mongo:8.0.0-rc7" )
			.withExposedPorts( 27017 );

	@DynamicPropertySource
	static void mongoDbProperties(DynamicPropertyRegistry registry) {
		log.info( "start mongo db container" );
		mongoDBContainer.start();
		int port = mongoDBContainer.getFirstMappedPort();
		String mongoDbUri = String.format( "mongodb://localhost:%s/photobook_demo", port );
		registry.add( MONGO_DB_URI_PROP, () -> mongoDbUri );
	}

	@Test
	void contextLoads() {
		String mongoDbUri = env.getProperty( MONGO_DB_URI_PROP );
		log.info( "{} -> {}", MONGO_DB_URI_PROP, mongoDbUri );
		try {
			log.info( "test controller" );
			Assertions.assertNotNull( this.photobookViewController );
			log.info( "test photobook list" );
			ResponseEntity<ResultDTO<Document>> resultList = this.photobookViewController.list();
			Assertions.assertNotNull( resultList.getBody() );
			log.info( "test photobook images" );
			ResponseEntity<ResultDTO<Document>> resultImages = this.photobookViewController.images( "1" );
			Assertions.assertNotNull( resultImages.getBody() );
			log.info( "test photobook image fail" );
			Assertions.assertThrows( Exception.class, () -> this.photobookViewController.downloadImage( "imag1" ) );
		} catch ( Exception e ) {
			Assertions.fail( String.format( "error %s", e.getMessage() ) );
		} finally {
			log.info( "stop mongo db container" );
			mongoDBContainer.stop();
		}
	}

}
