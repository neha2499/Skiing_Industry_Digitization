package com.coen6731.group8;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Random;

public class Skier {



    private String id;

    private Integer skierID;

    private Integer resortID;

    private Integer liftID;

    private Integer seasonID;

    private Integer dayID;

    private Integer time;


    public Skier() {
        super();
        Random random = new Random();
        this.id=null;
        this.skierID = random.nextInt(99999)+1;
        this.resortID = random.nextInt(9)+1;
        this.liftID = random.nextInt(39)+1;
        this.seasonID = 2022;
        this.dayID = 1;
        this.time = random.nextInt(359)+1;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSkierID() {
        return skierID;
    }

    public void setSkierID(Integer skierID) {
        this.skierID = skierID;
    }

    public Integer getResortID() {
        return resortID;
    }

    public void setResortID(Integer resortID) {
        this.resortID = resortID;
    }

    public Integer getLiftID() {
        return liftID;
    }

    public void setLiftID(Integer liftID) {
        this.liftID = liftID;
    }

    public Integer getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(Integer seasonID) {
        this.seasonID = seasonID;
    }

    public Integer getDayID() {
        return dayID;
    }

    public void setDayID(Integer dayID) {
        this.dayID = dayID;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "Resort{" +
                "id='" + id + '\'' +
                ", skierID=" + skierID +
                ", resortID=" + resortID +
                ", liftID=" + liftID +
                ", seasonID=" + seasonID +
                ", dayID=" + dayID +
                ", time=" + time +
                '}';
    }
}

