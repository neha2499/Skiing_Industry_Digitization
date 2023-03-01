package com.coen6731.group8;




import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;


public class thread_client implements Runnable {
    private Thread t;
    private String threadName;
    private String url;
    int n;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    int count;
    private Instant start_now,end_now;
    private Duration timeElapsed;

    public ArrayList<Duration> getTime_post() {
        return time_post;
    }



    private ArrayList<Duration> time_post;

    thread_client( String name, String url, Integer n) {
        threadName = name;
        this.url =url;
        this.n =n;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );


        try {

            for (int i = 0; i < n; i++) {
                start_now = Instant.now();

                Skier skier = new Skier();

                Body body = new Body(skier.getTime(), skier.getLiftID());
                WebClient webClient = WebClient.create(url);
                try {
                    String responseBody = webClient.post().uri("/skiers/" + String.valueOf(skier.getSkierID()) + "/seasons/" + String.valueOf(skier.getSeasonID()) + "/days/" + String.valueOf(skier.getDayID()) + "/skiers/" + String.valueOf(skier.getSkierID()))
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(Mono.just(body), Body.class)
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();
                    count+=1;
                }catch(Exception e) {
                    for (int j=0;j<4;j++){
                        try {
                            String responseBody = webClient.post().uri("/skiers/" + String.valueOf(skier.getSkierID()) + "/seasons/" + String.valueOf(skier.getSeasonID()) + "/days/" + String.valueOf(skier.getDayID()) + "/skiers/" + String.valueOf(skier.getSkierID()))
                                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                    .body(Mono.just(body), Body.class)
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .block();
                            break;
                        } catch (Exception ex) {
                            if (j >= 3)
                                throw new RuntimeException(ex);
                        }

                    }
                }

                end_now = Instant.now();
//                timeElapsed = Duration.between(start_now, end_now);
//                timeElapsed = timeElapsed.dividedBy(n);
//                time_post.add(timeElapsed);

            }
            //                timeElapsed = timeElapsed.dividedBy(n);
                System.out.println("Creating " + threadName + "executed success");
            }catch(Exception e){
                System.out.println("Thread " + threadName + " interrupted."+e);
            }
        }



    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
    public void join() throws InterruptedException {
        t.join ();
    }

    public Duration getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(Duration timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public Thread getT() {
        return t;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getUrl() {
        return url;
    }

    public int getN() {
        return n;
    }

    public Instant getStart_now() {
        return start_now;
    }

    public Instant getEnd_now() {
        return end_now;
    }



}
