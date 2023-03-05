package com.coen6731.group8.repository;

import com.coen6731.group8.resort.Resort;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface customRepo {
    List<Resort> findResortByProperties(Integer skierID, Integer resortID, Integer liftID, Integer seasonID, Integer dayID, Integer time);
    Integer findNoSkiers(Integer resortID, Integer seasonID, Integer dayID);
}
