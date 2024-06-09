package org.fugerit.java.demo.springboot.photobook.rest;

import java.util.Properties;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/meta")
@Slf4j
public class MetaController {

	@GetMapping("/version")
	public ResponseEntity<Properties> version() {
		return RestHelper.handle( () -> {
			Properties props = new Properties();
			props.setProperty( "version" , "1.0.0" );
			props.setProperty( "revision" , "NA" );
			return new ResponseEntity<>(props, HttpStatus.OK);
		} );
	}
	
}
