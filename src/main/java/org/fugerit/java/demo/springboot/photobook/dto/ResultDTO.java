package org.fugerit.java.demo.springboot.photobook.dto;

import lombok.*;

@RequiredArgsConstructor
public class ResultDTO<T> {

	@NonNull @Getter @Setter
	T content;
	
}
