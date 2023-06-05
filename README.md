# Skiing Industry Digitization -a Distributed System


## About

The goal of our project is to create a client/server
distributed system model which allows all the Ski Resorts to get
digital. The idea is to have ski resorts employ RFID lift ticket
readers to automatically log the skier’s ID and ride duration
each time they use a ski lift. So, in our Project, we will create
the client side with the help of multi threading that it will post all of
the data to the server. The server side is deployed by using the
Oracle free-tier cloud


#### Key features of the project

##### Introduction

- Objective is to create a client/server distributed system model for ski resorts.
- The system uses RFID lift ticket readers to log skier's ID and ride duration automatically.
- The client side is developed using multi-threading, and all data is sent to the server.
- The server side is deployed on the Oracle free-tier cloud.

##### Server Implementation

- The server-side will validate all the parameters and return the response accordingly.
- The server-side will be deployed using Oracle free-tier cloud.
- ResortDaoService provides an abstract interface and business logic used to create POST & GET methods.

#####  Client Implementation

- Two clients are created, one single-threaded and the other multi-threaded.
- Analytics class is created to measure the profiling performance of the client-side.
- Profiling performance includes mean response time, median response time, throughput, 99th percentile, max and min response time.

## Built With

- Spring Boot
- Oracle Cloud Instance
- Docker
- Github Actions

## Getting Started

### Prerequisites

In order to run this web application the docker need to be confifured
 
   ```bash
   docker build -t spring:latest  
   docker run -p 8083:8083 spring  
   ```    

  


## Resort Data Model

```bash
Resort{
"dayID":Integer($int32),
"id":String,
"liftID":Integer($int32),
"resortID":Integer($int32),
"seasonID":Integer($int32),
"skierID":Integer($int32)
"time":Integer($int32),
}
```

## Profiling Performance

![Analysis](/screenshots/total.png?raw=true)
![Analysis](/screenshots/vs.png?raw=true)
