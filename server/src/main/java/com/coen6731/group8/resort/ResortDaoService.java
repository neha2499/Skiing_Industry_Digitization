package com.coen6731.group8.resort;

import com.coen6731.group8.repository.ResortRepository;
import com.coen6731.group8.repository.customRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import com.coen6731.group8.resort.Resort;

import java.util.ArrayList;
import java.util.HashMap;
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




    public List<Resort> fetchResortByProperties(Integer skierID, Integer resortID, Integer liftID, Integer seasonID, Integer dayID, Integer time) {

        return resortRepository.findResortByProperties(skierID, resortID, liftID, seasonID,dayID,time);
    }

    public Resort createSeason(Integer resortID, Integer seasonID) {
        Resort resort = new Resort(null,null,resortID,null,seasonID,null,null);
        resortRepository.save(resort);
        return resort;
    }


    public Resort createSkier(Integer resortID, Integer seasonID, Integer dayID, Integer skierID, Integer liftID, Integer Time) {
        Resort resort = new Resort(null,skierID,resortID,liftID,seasonID,dayID,Time);
        System.out.println(resort.toString());
        resortRepository.save(resort);
        return resort;
    }

    public HashMap<String,List<Integer>> findAllResorts() {
        HashMap<String, List<Integer>> out = new HashMap<String, List<Integer>>();
        List<Integer>  tmp = new ArrayList<Integer>();
        out.put("Resorts",tmp);
        return out;
    }
}
