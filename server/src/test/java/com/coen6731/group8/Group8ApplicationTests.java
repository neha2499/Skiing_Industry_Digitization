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
import java.util.Collections;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;


@SpringBootTest
class Group8ApplicationTests {
	String url = "http://155.248.226.228:8083";



	@Test
	void check_api() throws Exception {
		WebClient webClient = WebClient.create(url);
		String responseBody = webClient.post().uri("/check_status")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToMono(String.class)
				.block();
		System.out.println(responseBody);
	}

	@Test
	void testskierpost() throws Exception {
		Skier skier = new Skier();


		Body body = new Body(skier.getTime(),skier.getLiftID());
		WebClient webClient = WebClient.create(url);
//		ClientResponse responseBody = webClient.post().uri("/skiers/"+String.valueOf(skier.getSkierID())+"/seasons/"+String.valueOf(skier.getSeasonID())+"/days/"+String.valueOf(skier.getDayID())+"/skiers/"+String.valueOf(skier.getSkierID()))
//				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//				.bodyValue(body).exchange()
//				.block();
		String responseBody = webClient.post().uri("/skiers/" + String.valueOf(skier.getSkierID()) + "/seasons/" + String.valueOf(skier.getSeasonID()) + "/days/" + String.valueOf(skier.getDayID()) + "/skiers/" + String.valueOf(skier.getSkierID()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(body), Body.class)
				.retrieve()
				.bodyToMono(String.class)
				.block();

//		int statusCode = responseBody.statusCode().value();

		System.out.println(responseBody);
	}


	@Test
	void threaded_clients() throws Exception {

		Instant start_now, end_now;
		Duration timeElapsed;
		int total_client = 50;
		int desired_no_post = 10000;



		CountDownLatch latch = new CountDownLatch(desired_no_post);


		Integer no_clients =32;
		Integer no_post  = 1000 ;
		List<thread_client> clients = new ArrayList<>();

		//////////////////////////////////////////////////////

		start_now = Instant.now();

		for (int i = 0; i < no_clients; i++) {
			thread_client R1 = new thread_client(Integer.toString(i), url, no_post,latch);
			R1.start();
			clients.add(R1);
		}

		int flag=0;
		while (true) {
			for (int i = 0; i < no_clients; i++) {
				Thread t = clients.get(i).getT();
				if (t.isAlive()) continue;
				else{
					flag =1;
				}
			}
			if (flag ==1)
				break;
		}



		int i1 = desired_no_post;
		System.out.println(String.valueOf(i1)+"<><><><><><><><><><><><><><><><><><><><><>");

		for (int i = no_clients; i < total_client; i++) {

			thread_client R1 = new thread_client(Integer.toString(i), url, i1, latch);
			R1.start();
			clients.add(R1);
		}

		for (int i = 0; i < total_client; i++) {
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
		ArrayList<Integer> time_dif = new ArrayList<>();
		String fileName = "output.csv";
		ArrayList<String> x1 = new ArrayList<String>();
		ArrayList<String> x2 = new ArrayList<String>();
		ArrayList<String> x3 = new ArrayList<String>();
		ArrayList<String> status_code = new ArrayList<String>();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			for (int i = 0; i < total_client; i++) {
				System.out.println(clients.get(i).getTime_post().toString());
				ArrayList<String> myList = clients.get(i).getTime_post();
				for(int k =0; k<myList.size();k++){
					String y = myList.get(k);
					String[] temp=y.split(",");
					System.out.println(temp.toString());
					x1.add(temp[0]);
					x2.add(temp[1]);
					x3.add(temp[2]);
					status_code.add(temp[3]);

				}




					for (String str : myList) {
						writer.write(str);
						writer.newLine();


	//			time_dif.add(clients.get(i).getTimeElapsed().toMillisPart());
						}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		float total_response_time=0;
		//System.out.println(x1.toString()+x2.toString()+x3.toString());
		ArrayList<Integer> res_time= new ArrayList<>();
		 for (String s: x3){
			 int i = Integer.parseInt(s);
			 res_time.add(i);
			 total_response_time=i+total_response_time;

		 }
		System.out.println(total_response_time);
		 float size= res_time.size();
		 float mean= total_response_time/size;
		System.out.println(mean);

         pertectile(res_time);
		 median(res_time);
		 max_min(res_time);
		 status(status_code);
	}


   public  void pertectile(ArrayList<Integer> R){
	   Collections.sort(R);
	   int index=(int)Math.ceil(R.size()*0.99)-1;
	   System.out.println(R.get(index));
   }


   public void median(ArrayList<Integer> R){

	   Collections.sort(R);

	   float middle;
	   if (R.size()%2 == 0) {

		   middle = (float) ((R.get(R.size() / 2) + R.get(R.size() / 2 + 1))/2.0);
	   } else {
		   middle = R.get(R.size() / 2);
	   }
	   System.out.println(middle);
   }


   public void max_min(ArrayList<Integer> R){

		Collections.sort(R);
		float max= R.get(R.size()-1);
		float min= R.get(0);
	   System.out.println("max"+max+"&"+"min"+min);




   }



   public void status(ArrayList<String> S){
		int count =0;
		for (String status: S){
			if (status.charAt(0)=='2'){
				break;
			}
			count++;

		}

	   System.out.println("Number of unsuccessful request:  "+count);
   }
}
