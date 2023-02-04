package com.coen6731.group8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.coen6731.group8.resort.Resort;

@Repository
public interface ResortRepositry extends MongoRepository<Resort, String> {
}
