package com.coen6731.group8.clients.singleThread;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;



class SingleThread {
    String url = "http://155.248.226.228:8083";




    void check_api() throws Exception {
        WebClient webClient = WebClient.create(url);
        String responseBody = webClient.post().uri("/check_status")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(responseBody);
    }

//    @Test
//    void testskierpost() throws Exception {
//        Skier skier = new Skier();
//
//        Body body = new Body(skier.getTime(),skier.getLiftID());
//        WebClient webClient = WebClient.create(url);
////		ClientResponse responseBody = webClient.post().uri("/skiers/"+String.valueOf(skier.getSkierID())+"/seasons/"+String.valueOf(skier.getSeasonID())+"/days/"+String.valueOf(skier.getDayID())+"/skiers/"+String.valueOf(skier.getSkierID()))
////				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
////				.bodyValue(body).exchange()
////				.block();
//        String responseBody = webClient.post().uri("/skiers/" + String.valueOf(skier.getSkierID()) + "/seasons/" + String.valueOf(skier.getSeasonID()) + "/days/" + String.valueOf(skier.getDayID()) + "/skiers/" + String.valueOf(skier.getSkierID()))
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(body), Body.class)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
////		int statusCode = responseBody.statusCode().value();
//
//        System.out.println(responseBody);
//    }



    void clients() throws Exception {

        Instant start_now, end_now;
        Duration timeElapsed;
        int total_client = 1;
        CountDownLatch latch = new CountDownLatch(10000);
        int desired_no_post = 10000;
        start_now = Instant.now();
        thread_client R1 = new thread_client(Integer.toString(total_client), url, desired_no_post, latch);
        R1.start();
        R1.join();
        end_now = Instant.now();
        timeElapsed = Duration.between(start_now, end_now);


        System.out.println(timeElapsed);
        ArrayList<Integer> time_dif = new ArrayList<>();
        String fileName = "output_Clientseq.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {


            ArrayList<String> myList = R1.getTime_post();
            for (String str : myList) {
                writer.write(str);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
