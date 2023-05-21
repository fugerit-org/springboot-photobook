package org.fugerit.java.demo.springboot.photobook.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResultDTO<T> {

	@NonNull
	T content;
	
}
