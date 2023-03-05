package com.coen6731.group8;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;

public class Analytics {


    public float mean(ArrayList<Integer> R, float S) {

        float size = R.size();

        return S / size;
    }

    public  float pertectile(ArrayList<Integer> R){
        Collections.sort(R);
        int index=(int)Math.ceil(R.size()*0.99)-1;
        return R.get(index);
    }


    public float median(ArrayList<Integer> R){

        Collections.sort(R);

        float middle;
        if (R.size()%2 == 0) {

            middle = (float) ((R.get(R.size() / 2) + R.get(R.size() / 2 + 1))/2.0);
        } else {
            middle = R.get(R.size() / 2);
        }
       return middle;
    }


    public void max_min(ArrayList<Integer> R){

        Collections.sort(R);
        float max= R.get(R.size()-1);
        float min= R.get(0);
        System.out.println("Maximum Response Time is: "+max+"&"+" Minimum Response Time is:"+min);




    }


    public int status(ArrayList<String> S){
        int count =0;
        for (String status: S){
            if (status.charAt(0)=='2'){
                break;
            }
            count++;

        }
        return count;
    }


    public float getThroughput(Duration W, int D){

        return (float) D*1000/W.toMillis();
    }
}
