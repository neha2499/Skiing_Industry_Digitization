package com.coen6731.group8;


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


    void clients() throws Exception {

        Instant start_now, end_now, start_now1, end_now1;
        Duration timeElapsed;

        int desired_no_post = 500;

        start_now = Instant.now();
        ArrayList<Integer> time = new ArrayList<>();
        for(int i =0 ; i<desired_no_post;i++) {
            start_now1 = Instant.now();
            System.out.println(i);
            WebClient webClient = WebClient.create(url);
            String responseBody = webClient.post().uri("/check_status")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            end_now1 = Instant.now();
            String tmp = String.valueOf(Duration.between(start_now1, end_now1).toMillis());
            time.add(Integer.parseInt(tmp));
        }

        end_now = Instant.now();
        timeElapsed = Duration.between(start_now, end_now);


        System.out.println(timeElapsed);
        ArrayList<Integer> time_dif = new ArrayList<>();
        String fileName = "output_Clientseq.csv";


        Analytics analytics = new Analytics();
        analytics.max_min(time);

        System.out.println("Wall Time is: "+timeElapsed);
        System.out.println(timeElapsed.toMillis());
        System.out.println("Throughput is: "+analytics.getThroughput(timeElapsed,desired_no_post));
    }


}
