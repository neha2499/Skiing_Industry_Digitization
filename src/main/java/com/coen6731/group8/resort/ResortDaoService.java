package com.coen6731.group8.resort;

import com.coen6731.group8.repository.ResortRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResortDaoService {



    @Autowired
    private ResortRepository resortRepository;



    public Resort createResort(Resort resort){return resortRepository.save(resort);}

}
