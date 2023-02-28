package com.coen6731.group8;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@SpringBootTest
class Group8ApplicationTests {
	@Test
	void testAudiopost() throws Exception {
		Skier skier = new Skier();

		Body body = new Body(skier.getTime(),skier.getLiftID());
		WebClient webClient = WebClient.create("http://localhost:8083");
		String responseBody = webClient.post()
				.uri("/skiers/"+String.valueOf(skier.getSkierID())+"/seasons/"+String.valueOf(skier.getSeasonID())+"/days/"+String.valueOf(skier.getDayID())+"/skiers/"+String.valueOf(skier.getSkierID()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(body), Body.class)
				.retrieve()
				.bodyToMono(String.class)
				.block();

		System.out.println(responseBody);
	}
}
