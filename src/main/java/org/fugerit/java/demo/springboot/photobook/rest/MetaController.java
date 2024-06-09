package org.fugerit.java.demo.springboot.photobook.rest;

import java.util.Properties;

import org.fugerit.java.core.util.PropsIO;
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
			Properties buildProps = PropsIO.loadFromClassLoader( "build.properties" );
			log.info( "buildProps : {}", buildProps );
			return new ResponseEntity<>(buildProps, HttpStatus.OK);
		} );
	}
	
}
