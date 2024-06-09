package org.fugerit.java.demo.springboot.photobook.rest;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.function.UnsafeSupplier;
import org.fugerit.java.demo.springboot.photobook.dto.ResultDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class RestHelper {

    private RestHelper() {}

    public static <T> ResponseEntity<T> handle(UnsafeSupplier<ResponseEntity<T>, Exception> fun) {
        try {
            return fun.get();
        } catch ( Exception e ) {
            log.error( String.format( "Error : %s", e.getMessage() ), e );
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
