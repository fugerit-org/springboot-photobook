package org.fugerit.java.demo.springboot.photobook;

import com.mongodb.internal.operation.AggregateOperation;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

@SpringBootApplication
@RegisterReflectionForBinding({AggregateOperation.class, AggregationOperation.class})
public class SpringbootPhotobookApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootPhotobookApplication.class, args);
	}

}
