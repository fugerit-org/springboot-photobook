package org.fugerit.java.demo.springboot.photobook.rest;

import java.util.List;

import org.bson.Document;
import org.fugerit.java.demo.springboot.photobook.dto.ResultDTO;
import org.fugerit.java.demo.springboot.photobook.service.PhotobookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/photobook-demo/api/photobook/view")
@Slf4j
public class PhotobookViewController {

	@Autowired
	private PhotobookService photobookService;

	@GetMapping("/list")
	public ResponseEntity<ResultDTO<List<Document>>> getList() {
		ResponseEntity<ResultDTO<List<Document>>> response = null;
		try {
			List<Document> docs =  this.photobookService.listPhotobooks( "it", 10, 1);
			ResultDTO<List<Document>> dto = new ResultDTO<>( docs );
			response = new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			log.error( "Error : "+e, e );
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping("/images/{photobookId}")
	public ResponseEntity<ResultDTO<List<Document>>> getImages( @PathVariable String photobookId ) {
		ResponseEntity<ResultDTO<List<Document>>> response = null;
		try {
			List<Document> docs =  this.photobookService.listImages( photobookId, "it", 10, 1);
			ResultDTO<List<Document>> dto = new ResultDTO<>( docs );
			response = new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			log.error( "Error : "+e, e );
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}
