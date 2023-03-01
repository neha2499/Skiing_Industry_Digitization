package com.coen6731.group8;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class Group8ApplicationTests {
	@Test
	void testAudiopost() throws Exception {
		Skier skier = new Skier();

		Body body = new Body(skier.getTime(),skier.getLiftID());
		WebClient webClient = WebClient.create("http://localhost:8083");
		String responseBody = webClient.post().uri("/skiers/"+String.valueOf(skier.getSkierID())+"/seasons/"+String.valueOf(skier.getSeasonID())+"/days/"+String.valueOf(skier.getDayID())+"/skiers/"+String.valueOf(skier.getSkierID()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(body), Body.class)
				.retrieve()
				.bodyToMono(String.class)
				.block();


		System.out.println(responseBody);
	}


	@Test
	void threaded_clients() throws Exception {

		Instant start_now, end_now;
		Duration timeElapsed;
		Skier skier = new Skier();

		Body body = new Body(skier.getTime(),skier.getLiftID());
		WebClient webClient = WebClient.create("http://localhost:8083");
		String responseBody = webClient.post().uri("/skiers/"+String.valueOf(skier.getSkierID())+"/seasons/"+String.valueOf(skier.getSeasonID())+"/days/"+String.valueOf(skier.getDayID())+"/skiers/"+String.valueOf(skier.getSkierID()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(body), Body.class)
				.retrieve()
				.bodyToMono(String.class)
				.block();


		System.out.println(responseBody);
		String url = "http://localhost:8083";

		Integer total =0;
		Integer no_clients =32;
		Integer no_post  = 10 ;
		List<thread_client> clients = new ArrayList<>();

		//////////////////////////////////////////////////////

		start_now = Instant.now();

		for (int i = 0; i < no_clients; i++) {
			thread_client R1 = new thread_client(Integer.toString(i), url, no_post);
			R1.start();
			clients.add(R1);
		}

//		for (int i = 0; i < no_clients; i++) {
//			try {
//				clients.get(i).join();
//			} catch (InterruptedException e) {
//				System.out.println("Thread interrupted");
//			}
//		}

		int k =0,flag=0;
		while (k<1000000) {
			for (int i = 0; i < no_clients; i++) {
				Thread t = clients.get(i).getT();
				if (t.isAlive()) {
					try {
						t.join();
					} catch (InterruptedException e) {
						// ignore
					}
				} else {
					// interrupt all other threads and remove them from the list
					for (int j = 0; j < no_clients; j++) {
						Thread t2 = clients.get(j).getT();
						if (t2 != t) {
							t2.interrupt();
						}
					}
					flag =1;

				}
			}
			if (flag ==1)
				break;

		}
		for (int j = 0; j < no_clients; j++) {
			total += clients.get(j).getCount();
		}

		no_clients =100;
		int i1 = (1000 - total) / no_clients;
		System.out.println(String.valueOf(i1)+"fjhgfhfyh"+String.valueOf(total));
		no_post =  i1;
		for (int i = 0; i < no_clients; i++) {
			if (i > 31){
				thread_client R1 = new thread_client(Integer.toString(i), url, no_post);
				R1.start();
				clients.add(R1);
				}
			else
				clients.get(i).start();
		}

		for (int i = 0; i < no_clients; i++) {
			try {
				clients.get(i).join();
			} catch (InterruptedException e) {
				System.out.println("Thread interrupted"+e);
			}
		}



		end_now = Instant.now();

		//////////////////////////////////////////////////////

		timeElapsed = Duration.between(start_now, end_now);


		System.out.println(timeElapsed);
//		ArrayList<Integer> time_dif = new ArrayList<>();
//		for (int i = 0; i < no_clients; i++) {
////			System.out.println(clients.get(i).getTimeElapsed().toMillisPart());
//			time_dif.add(clients.get(i).getTimeElapsed().toMillisPart());
//		}

	}
}
