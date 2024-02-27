package com.nnk.springboot.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDTO;
import com.nnk.springboot.domain.dto.BidListMapperImpl;
import com.nnk.springboot.repositories.IBidListRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
public class BidListService {
	private static final Logger log = LogManager.getLogger(BidListService.class);

	@Autowired
	private IBidListRepository bidListRepository;

	@Autowired
	private BidListMapperImpl mapper;

	public BidList addBid(BidList bidListCreated)
			throws NullPointerException {
		BidList bidListRegistered = new BidList();
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		try {
			if (bidListCreated != null) {
				if (bidListCreated.getAccount() == null || bidListCreated.getType() == null) {
					throw new IllegalArgumentException("Empty data required of Bid " + bidListCreated + " provided and created");
				}
	
				bidListRegistered.setAccount(bidListCreated.getAccount());
				bidListRegistered.setType(bidListCreated.getType());
				bidListRegistered.setBidQuantity(bidListCreated.getBidQuantity());
				bidListRegistered.setCreationName("bid");
				bidListRegistered.setCreationDate(timestamp);
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		}
		
		bidListRegistered = bidListRepository.save(bidListRegistered);
		return bidListRegistered;
	}

	public BidList getBidById(Integer id) throws NullPointerException {
		BidList bidlistFoundById = bidListRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Bid " + id + " not found"));
		//BidListDTO bidListDTO = mapper.bidListToBidListDTO(bidlistFoundById);
		return bidlistFoundById ;
	}

	public List<BidListDTO> getAllBids() throws NullPointerException {
		List<BidList> allBidLists = bidListRepository.findAll();
		List<BidListDTO> allBidListDto = new ArrayList<BidListDTO>();
		if (allBidLists.isEmpty()) {
			return new ArrayList<>();
		}
		allBidLists.forEach(bid -> {
			allBidListDto.add(mapper.bidListToBidListDTO(bid));
		});
		return allBidListDto;
	}

	public BidList updateBidById(Integer id, BidList bidListUpdated)
			throws NullPointerException, IllegalArgumentException {
		BidList bidListToUpdate = new BidList();
		bidListToUpdate = bidListRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Bid " + id + " not found for updating"));
		
		if (bidListUpdated.getAccount() == null || bidListUpdated.getType() == null) {
			throw new IllegalArgumentException("Empty data required of Bid " + bidListUpdated+ " provided and updated");
		}
		
		bidListToUpdate.setAccount(bidListUpdated.getAccount());
		bidListToUpdate.setType(bidListUpdated.getType());
		bidListToUpdate.setBidQuantity(bidListUpdated.getBidQuantity());
		bidListToUpdate = bidListRepository.save(bidListToUpdate);
		return bidListToUpdate;
	}

	public void deleteBidById(Integer id) throws NullPointerException {
		bidListRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Bid " + id + " not found for deleting"));
		bidListRepository.deleteById(id);
	}
	
	public void deleteAllBids() throws Exception {
		bidListRepository.deleteAll();
	}
}