package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 메인클래스라고 명시하는
@SpringBootApplication
//  springBootApplication 어노테이션에는 대표적으로 아래의 어노테이션이 내장되어 있음
//    @SpringBootConfiguration : 설정을 위한 어노케이션
//    @EnableAutoConfiguration : 특정 어노테이션을 사용할 때, 해당 어노테이션에 필요한 설정을 자동으로 해줌
//    @ComponentScan : 특정 패키지에 어노테이션(@Service, @Repository ..)이 붙은 spring bean 객체를 찾을 때 사용
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args); // 해당 구문 작성시, web application이 만들어짐
	}

}
