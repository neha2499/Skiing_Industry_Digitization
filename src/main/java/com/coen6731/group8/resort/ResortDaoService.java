package com.coen6731.group8.resort;

import com.coen6731.group8.repository.ResortRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ResortDaoService {



    @Autowired
    private ResortRepository resortRepository;



    public List<Resort> findAll(){
        return resortRepository.findAll();
    }


    public Resort findById(String id){
      return resortRepository.findById(id).get();
    }


    public Resort createResort(Resort resort){return resortRepository.save(resort);}

}
