package com.coen6731.group8.resort;


import com.coen6731.group8.errorHandling.UserNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResortController {



    private ResortDaoService service;

    public ResortController(ResortDaoService service) {
        this.service = service;
    }

    @GetMapping("/resorts")
    public List<Resort> retrieveAllUsers() {



         return service.findAll();


    }


    @GetMapping("/resorts/{resortID}/seasons/{seasonID}/day/{dayID}/skiers")
    public List<Resort> retrieveUser(@PathVariable String resortID, @PathVariable String seasonID,@PathVariable String dayID){
        System.out.println(resortID+seasonID+dayID);
        List<Resort> tmp = service.fetchStudentsByProperties(null,Integer.valueOf(resortID),null,Integer.valueOf(seasonID),Integer.valueOf(dayID),null,2);

        if(tmp.size()==0){
            throw new UserNotFoundException("id:"+resortID);
        } else {
            return tmp;
        }


    }
    @GetMapping("/resorts/{resortID}/seasons")
    public Resort retrieveUser(@PathVariable String resortID){
//
        Resort resort = service.findById(resortID);

        if(resort==null){
            throw new UserNotFoundException("id:"+resortID);
        } else {
            return resort;
        }


    }

    @PostMapping("/resorts")
    public Resort createResort(@RequestBody Resort resort){ return service.createResort(resort);}

}
