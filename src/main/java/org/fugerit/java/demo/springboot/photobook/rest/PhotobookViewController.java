package org.fugerit.java.demo.springboot.photobook.rest;

import java.io.IOException;

import org.bson.Document;
import org.fugerit.java.demo.springboot.photobook.dto.ResultDTO;
import org.fugerit.java.demo.springboot.photobook.service.PhotobookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/photobook/view")
@Slf4j
public class PhotobookViewController {

	private static final Integer DEF_PAGE_SIZE = 10;
	
	@Autowired
	private PhotobookService photobookService;

	@GetMapping("/list")
	public ResponseEntity<ResultDTO<Document>> getList() {
		ResponseEntity<ResultDTO<Document>> response = null;
		try {
			Document doc =  this.photobookService.listPhotobooks( "it", 10, 1);
			ResultDTO<Document> dto = new ResultDTO<>( doc );
			response = new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			log.error( "Error : "+e, e );
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@GetMapping("/images/{photobookId}/language/{language}/current_page/{currentPage}/page_size/{pageSize}/search/{text}")
	public ResponseEntity<ResultDTO<Document>> getImages( @PathVariable String photobookId, @PathVariable String language, @PathVariable Integer currentPage, @PathVariable Integer pageSize, @PathVariable String text ) {
		ResponseEntity<ResultDTO<Document>> response = null;
		try {
			Document doc =  this.photobookService.listImages( photobookId, language, pageSize, currentPage, text);
			ResultDTO<Document> dto = new ResultDTO<>( doc );
			response = new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			log.error( "Error : "+e, e );
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping("/images/{photobookId}/language/{language}/current_page/{currentPage}/page_size/{pageSize}")
	public ResponseEntity<ResultDTO<Document>> getImages( @PathVariable String photobookId, @PathVariable String language, @PathVariable Integer currentPage, @PathVariable Integer pageSize ) {
		ResponseEntity<ResultDTO<Document>> response = null;
		try {
			Document doc =  this.photobookService.listImages( photobookId, language, pageSize, currentPage, "");
			ResultDTO<Document> dto = new ResultDTO<>( doc );
			response = new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			log.error( "Error : "+e, e );
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping("/images/{photobookId}")
	public ResponseEntity<ResultDTO<Document>> getImages( @PathVariable String photobookId ) {
		return this.getImages(photobookId, "def", 1, DEF_PAGE_SIZE);
	}
	
	@GetMapping(value = "/download/{imagePath}", produces = MediaType.IMAGE_JPEG_VALUE )
	public @ResponseBody byte[] downloadImage( @PathVariable String imagePath ) throws IOException {
		imagePath = imagePath.substring( 0, imagePath.indexOf( '.' ) );
		String[] split = imagePath.split( "_" );
		log.debug( "photobookId : {}, imageId : {}", split[0], split[1] );
	    return this.photobookService.downloadImage( split[0], split[1] );
	}
	
}
