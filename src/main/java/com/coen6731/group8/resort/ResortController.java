package com.coen6731.group8.resort;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResortController {

    private ResortDaoService service;

    public ResortController(ResortDaoService service) {
        this.service = service;
    }


    @PostMapping("/resorts")
    public Resort createResort(@RequestBody Resort resort){ return service.createResort(resort);};

}
