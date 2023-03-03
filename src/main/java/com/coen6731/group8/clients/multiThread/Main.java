package com.coen6731.group8.clients.multiThread;

public class Main {


    public static void main(String[] args) throws Exception {

        MultiThread multiThread=new MultiThread();
        multiThread.check_api();
        multiThread.threaded_clients();
    }
}
