package com.coen6731.group8.clients.multiThread;

public class Body {
    private Integer time;
    private Integer liftID;

    public Body(Integer time, Integer liftID) {
        this.time = time;
        this.liftID = liftID;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getLiftID() {
        return liftID;
    }

    public void setLiftID(Integer liftID) {
        this.liftID = liftID;
    }
}
