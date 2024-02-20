package com.nnk.springboot.service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListService {
	@Autowired
	private BidListRepository bidListRepository;

	public BidList addbid(BidList bidListCreated,String username)
			throws IllegalArgumentException, NullPointerException {
		BidList bidListRegistered = new BidList();
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());

		if (bidListRegistered != null) {
			bidListRegistered.setAccount(bidListCreated.getAccount());
			bidListRegistered.setType(bidListCreated.getType());
			bidListRegistered.setBidQuantity(bidListCreated.getBidQuantity());
			bidListRegistered.setCreationName("bid");
			bidListRegistered.setCreationDate(timestamp);
		}
		
		bidListRepository.save(bidListRegistered);
		return bidListRegistered;
	}
}
