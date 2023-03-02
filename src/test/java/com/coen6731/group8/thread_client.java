package com.coen6731.group8;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

class start_end{
    private String start;
    private String end;


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "start_end{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    public start_end(String start, String end) {
        this.start = start;
        this.end = end;
    }
}
public class thread_client implements Runnable {
    private final CountDownLatch latch;
    private Thread t;
    private String threadName;
    private String url;
    int n;


    private int count = 0;
    private Instant start_now,end_now;
    private Duration timeElapsed;

    private ArrayList<String> time_post;
    public ArrayList<String> getTime_post() {
        return time_post;
    }





    thread_client(String name, String url, Integer n, CountDownLatch latch) {
        threadName = name;
        this.url =url;
        this.n =n;
        this.latch =latch;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );
        time_post = new ArrayList<>();

        try {
            int i =0;
            while(i<n) {
                start_now = Instant.now();

                System.out.println(threadName+latch);
                if (latch.getCount()==0)
                    break;

                Skier skier = new Skier();

                Body body = new Body(skier.getTime(), skier.getLiftID());
                WebClient webClient = WebClient.create(url);
                int response =0 ;
                try {
                    ClientResponse responseBody = webClient.post().uri("/skiers/"+String.valueOf(skier.getSkierID())+"/seasons/"+String.valueOf(skier.getSeasonID())+"/days/"+String.valueOf(skier.getDayID())+"/skiers/"+String.valueOf(skier.getSkierID()))
                                                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                                    .bodyValue(body).exchange()
                                                    .block();
                    count+=1;
                    response = responseBody.statusCode().value();
                }catch(Exception e) {
                    for (int j=0;j<4;j++){
                        try {
                            ClientResponse responseBody = webClient.post().uri("/skiers/"+String.valueOf(skier.getSkierID())+"/seasons/"+String.valueOf(skier.getSeasonID())+"/days/"+String.valueOf(skier.getDayID())+"/skiers/"+String.valueOf(skier.getSkierID()))
                                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                    .bodyValue(body).exchange()
                                    .block();
                            response = responseBody.statusCode().value();
                            break;
                        } catch (Exception ex) {
                            if (j >= 3)
                                throw new RuntimeException(ex);

                        }


                    }
                }
                i+=1;
                latch.countDown();

                end_now = Instant.now();
//                System.out.println(start_now.toString()+" "+end_now.toString());
                timeElapsed = Duration.between(start_now, end_now);
                start_end tmp =new start_end(start_now.toString(), end_now.toString());
                time_post.add(start_now.toString()+","+"POST"+","+ timeElapsed.toMillis()+","+String.valueOf(response));

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
