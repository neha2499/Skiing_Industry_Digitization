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


    @GetMapping("/resorts/{id}")
    public Resort retrieveUser(@PathVariable String id){
//
        Resort resort = service.findById(id);

        if(resort==null){
            throw new UserNotFoundException("id:"+id);
        } else {
            return resort;
        }


    }

    @PostMapping("/resorts")
    public Resort createResort(@RequestBody Resort resort){ return service.createResort(resort);};

}
