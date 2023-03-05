package com.coen6731.group8.repository;
import java.util.List;


import org.springframework.stereotype.Repository;
import com.coen6731.group8.resort.Resort;
import java.util.ArrayList;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@Repository
public class  customRepoImpl implements customRepo {
    final
    MongoTemplate mongoTemplate;

    public customRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Resort> findResortByProperties(Integer skierID, Integer resortID, Integer liftID, Integer seasonID, Integer dayID, Integer time) {
//    final Query query = new Query().with(page);
//        query.fields();
        System.out.println(resortID.toString() + dayID.toString() + "here");
        final List<Criteria> criteria = new ArrayList<>();
        if (skierID != null)
            criteria.add(Criteria.where("skierID").is(skierID));
        if (resortID != null)
            criteria.add(Criteria.where("resortID").is(resortID));
        if (liftID != null)
            criteria.add(Criteria.where("liftID").is(liftID));
        if (seasonID != null)
            criteria.add(Criteria.where("seasonID").is(seasonID));
        if (dayID != null)
            criteria.add(Criteria.where("dayId").is(dayID));
        if (time != null)
            criteria.add(Criteria.where("time").is(time));

        Query query = new Query();
        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        System.out.println(query.toString());
        List<Resort> k = mongoTemplate.find(query, Resort.class);
        System.out.println(k.toString());
        return k;
    }

    @Override
    public Integer findNoSkiers(Integer resortID, Integer seasonID, Integer dayID) {
        return null;
    }
}

//
//    public Integer findResorSkiertByProperties(Integer resortID, Integer seasonID, Integer dayID) {
//
//        final List<Criteria> criteria1 = new ArrayList<Criteria>();
//
//        if (resortID != null )
//            criteria1.add(Criteria.where("resortID").is(resortID));
//
//        if (seasonID != null)
//            criteria1.add(Criteria.where("seasonID").is(seasonID));
//        if (dayID != null)
//            criteria1.add(Criteria.where("dayId").is(dayID));
//
//
//        Query query1 = new Query();
//        if (!criteria1.isEmpty())
//            query1.addCriteria(new Criteria().andOperator(criteria1.toArray(new Criteria[criteria1.size()])));
//
//        System.out.println(query1.toString());
//        List<Integer> k = mongoTemplate.findDistinct(query1,"skierID",Resort.class,Integer.class);
//        System.out.println(k.toString());
//        return k.size();
//    }
//
//}





