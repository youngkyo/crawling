package com.youngkyo.crawling.crawling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication(
	scanBasePackages = "com.youngkyo.crawling.crawling",
	scanBasePackageClasses = Crawling.class
)
public class CrawlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlingApplication.class, args);
	}

}
