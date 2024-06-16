package org.fugerit.java.demo.springboot.photobook;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.demo.springboot.photobook.dto.ResultDTO;
import org.fugerit.java.demo.springboot.photobook.rest.MetaController;
import org.fugerit.java.demo.springboot.photobook.rest.PhotobookViewController;
import org.fugerit.java.demo.springboot.photobook.rest.RestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.MountableFile;

import java.io.File;

@Slf4j
@SpringBootTest
class SpringbootPhotobookApplicationTests {

	@Autowired
	private PhotobookViewController photobookViewController;

	@Autowired
	private MetaController metaController;

	@Autowired
	private Environment env;

	private static final String MONGO_DB_URI_PROP = "spring.data.mongodb.uri";

	final static GenericContainer mongoDBContainer = new GenericContainer( "mongo:8.0.0-rc8" )
			.withCopyToContainer(MountableFile.forHostPath( new File( "src/test/resources/mongo-db/mongo-init.js" ).getPath() ), "/docker-entrypoint-initdb.d/mongo-init.js" )
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
			// photobook view controller
			log.info( "test photobook view controller" );
			Assertions.assertNotNull( this.photobookViewController );
			log.info( "test photobook list" );
			ResponseEntity<ResultDTO<Document>> resultList = this.photobookViewController.list();
			Assertions.assertNotNull( resultList.getBody() );
			log.info( "test photobook images" );
			ResponseEntity<ResultDTO<Document>> resultImages = this.photobookViewController.images( "1" );
			Assertions.assertNotNull( resultImages.getBody() );
			log.info( "test photobook image fail" );
			Assertions.assertThrows( Exception.class, () -> this.photobookViewController.downloadImage( "imag1" ) );
			log.info( "test photobook image ok" );
			Assertions.assertNotNull( this.photobookViewController.downloadImage( "springio23_1000.jpg" ) );
			// meta controller
			log.info( "test meta controller" );
			Assertions.assertNotNull( this.metaController );
			Assertions.assertNotNull( this.metaController.version().getBody() );
		} catch ( Exception e ) {
			Assertions.fail( String.format( "error %s", e.getMessage() ) );
		} finally {
			log.info( "stop mongo db container" );
			mongoDBContainer.stop();
		}
	}

	@Test
	void testRestHelper() {
		ResponseEntity<Object> res = RestHelper.handle( () -> {
			if ( Boolean.TRUE.booleanValue() ) {
				throw new ConfigRuntimeException( "scenario exception" );
			}
			return null;
		} );
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
	}

}
