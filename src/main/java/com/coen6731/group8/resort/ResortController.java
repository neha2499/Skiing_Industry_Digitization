package com.coen6731.group8.resort;


import com.coen6731.group8.errorHandling.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class ResortController {



    private ResortDaoService service;

    public ResortController(ResortDaoService service) {
        this.service = service;
    }
//
//    @GetMapping("/resorts")
//    public HashMap<String,List<Integer>> retrieveAllUsers() {
//        // Input : Non
//        //Resort name and ResortID
//
//         return service.findAllResorts();
//
//
//    }
//
//
//    @GetMapping("/resorts/{resortID}/seasons/{seasonID}/day/{dayID}/skiers")
//    public List<Resort> retrieveUser(@PathVariable String resortID, @PathVariable String seasonID,@PathVariable String dayID){
//        //Input ResortTD season
//
//        System.out.println(resortID+seasonID+dayID);
//        List<Resort> tmp = service.findResorSkiertByProperties(null,Integer.valueOf(resortID),null,Integer.valueOf(seasonID),Integer.valueOf(dayID),null);
//
//        if(tmp.size()==0){
//            throw new UserNotFoundException("id:"+resortID);
//        } else {
//            return tmp;
//        }
//
//
//    }
//    @GetMapping("/resorts/{resortID}/seasons")
//    public Resort retrieveUser(@PathVariable String resortID){
//        // Input ResortID
//        //output all seasons ID
//        Resort resort = service.findById(resortID);
//
//        if(resort==null){
//            throw new UserNotFoundException("id:"+resortID);
//        } else {
//            return resort;
//        }
//
//
//    }
//
//    @GetMapping("/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}")
//    public Integer getVertical(@PathVariable String resortID, @PathVariable String seasonID,@PathVariable String dayID, @PathVariable String skierID){
//
//        return 8;
//    }
//
//
//    @GetMapping("/skiers/{skierID}/vertical")
//    public void getVerticalTot(@PathVariable String skierID, @RequestParam String resort,@RequestParam String season ){
//
//    }
//
//
//
//


    @PostMapping("/check_status")
    public ResponseEntity<String> api_alive_test(){
        return  ResponseEntity.status(HttpStatus.FOUND).body("Server is working");
    }


    @PostMapping("/resorts/{resortID}/seasons")
    public ResponseEntity<String> createSeasons(@PathVariable String resortID, @RequestBody HashMap<String, Integer> payload){
        try {
        System.out.println(resortID+payload.get("year"));
        Integer year = (Integer) payload.get("year");
        Resort resort = service.createSeason(Integer.valueOf(resortID),year);
            return ResponseEntity.status(HttpStatus.CREATED).body(resort.toString()+"resource created");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }

    @PostMapping("/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}")
    public ResponseEntity<String> createSkier(@PathVariable String resortID, @PathVariable String seasonID, @PathVariable String dayID, @PathVariable String skierID, @RequestBody HashMap<String, Integer> lift ){

         System.out.println(resortID+skierID+seasonID);
         try {
             Integer time = (Integer) lift.get("time");
             Integer liftID = (Integer) lift.get("liftID");
             Resort resort = service.createSkier(Integer.valueOf(resortID), Integer.valueOf(seasonID), Integer.valueOf(dayID), Integer.valueOf(skierID), time, liftID);
             return ResponseEntity.status(HttpStatus.CREATED).body(resort.toString()+"resource created");
         }catch(Exception e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }



    @PostMapping("/resorts")
    public Resort createResort(@RequestBody Resort resort){ return service.createResort(resort);}


}

