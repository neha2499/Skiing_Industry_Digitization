package com.coen6731.group8;


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



class MultiThread {
	String url = "http://localhost:8083";




	void check_api() throws Exception {
		WebClient webClient = WebClient.create(url);
		String responseBody = webClient.post().uri("/check_status")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToMono(String.class)
				.block();
		System.out.println(responseBody);
	}





	void threaded_clients() throws Exception {

		Instant start_now, end_now;
		Duration timeElapsed;
		int total_client = 50;//50
		int desired_no_post = 500;//500
		Analytics analytics=new Analytics();



		CountDownLatch latch = new CountDownLatch(desired_no_post);


		Integer no_clients =32;
		Integer no_post  = 10 ;//10
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
		String fileName = "output_clientMultiThread.csv";
		ArrayList<String> start_time = new ArrayList<String>();
		ArrayList<String> req_type = new ArrayList<String>();
		ArrayList<Integer> res_time = new ArrayList<Integer>();
		ArrayList<String> status_code = new ArrayList<String>();
		float total_response_time=0;


		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			for (int i = 0; i < total_client; i++) {
				//System.out.println(clients.get(i).getTime_post().toString());
				ArrayList<String> myList = clients.get(i).getTime_post();
				for(int k =0; k<myList.size();k++){
					String y = myList.get(k);
					String[] temp=y.split(",");
//					System.out.println(temp.toString());
					start_time.add(temp[0]);
					req_type.add(temp[1]);
					int tmp = Integer.parseInt(temp[2]);
					res_time.add(tmp);
					total_response_time+=tmp;
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



		System.out.println(total_response_time);

		System.out.println("Mean Response Time is : "+analytics.mean(res_time,total_response_time));

		System.out.println("99th Percentile is: "+analytics.pertectile(res_time));
		System.out.println("Median Response Time is : "+analytics.median(res_time));
		analytics.max_min(res_time);
		System.out.println("Number of Unsuccessful Request: "+analytics.status(status_code));
		System.out.println("Wall Time is: "+timeElapsed);
		System.out.println(timeElapsed.toMillis());
		System.out.println("Throughput is: "+analytics.getThroughput(timeElapsed,desired_no_post));
	}


}
