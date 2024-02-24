package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBidListRepository extends JpaRepository<BidList, Integer> {

}
