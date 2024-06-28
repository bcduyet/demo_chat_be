package co.jp.xeex.chat;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.jp.xeex.chat.common.AppConstant;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class ChatApplication {

	public static void main(String[] args) {

		SpringApplication.run(ChatApplication.class, args);
	}

	@PostConstruct
	public void init() {
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone(AppConstant.UTC));
	}
}
