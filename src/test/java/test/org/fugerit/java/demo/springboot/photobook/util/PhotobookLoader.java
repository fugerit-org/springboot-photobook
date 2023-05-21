package test.org.fugerit.java.demo.springboot.photobook.util;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhotobookLoader {

	private static final String BASE_DIR = "src/test/resources/sample-photobooks";
	
	public void loadPhotobook( File file ) {
		
	}
	
	public void loadPhotobooks( String folder ) {
		File file = new File( folder );
		for ( File current : file.listFiles() ) {
			log.info( "loading photobook : {}", current );
		}
	}
	
	public static void main( String [] args ) {
		log.info( "start" );
		PhotobookLoader loader = new PhotobookLoader();
		loader.loadPhotobooks( BASE_DIR );
		log.info( "end" );
	}
	
}
