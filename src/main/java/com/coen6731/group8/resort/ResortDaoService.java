package com.coen6731.group8.resort;

import com.coen6731.group8.repository.ResortRepository;
import com.coen6731.group8.repository.customRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import com.coen6731.group8.resort.Resort;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.domain.PageRequest;
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




    public List<Resort> fetchStudentsByProperties(Integer skierID, Integer resortID, Integer liftID, Integer seasonID, Integer dayID, Integer time, Integer page) {

        return resortRepository.findResortByProperties(skierID, resortID, liftID, seasonID,dayID,time,PageRequest.of(page, 15));
    }

}
