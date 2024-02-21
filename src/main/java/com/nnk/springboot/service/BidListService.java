package com.nnk.springboot.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDTO;
import com.nnk.springboot.domain.dto.Mapper;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListService {
	@Autowired
	private BidListRepository bidListRepository;
	
	@Autowired
	private Mapper mapper;

	public BidList addbid(BidList bidListCreated, String username)
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
			bidListRegistered.setTrader(username);
		}

		bidListRegistered = bidListRepository.save(bidListRegistered);
		return bidListRegistered;
	}

	public BidListDTO getBidById(Integer id) throws NullPointerException {
		BidList bidlistFoundById =bidListRepository.findById(id).orElseThrow(() -> new NullPointerException("Bid " + id + " not found"));
		BidListDTO bidListDTO = mapper.BidListToBidListDTO(bidlistFoundById);
		return bidListDTO ;
	}

	public List<BidListDTO> getAllBids() throws NullPointerException {
		List<BidList> AllBidLists=bidListRepository.findAll();
		List<BidListDTO> allBidListDto= new ArrayList<BidListDTO>();
		if(AllBidLists!=null) {
			AllBidLists.forEach(bid-> {
				mapper.BidListToBidListDTO(bid);
				allBidListDto.add(mapper.BidListToBidListDTO(bid));
			});
		}
			
		return allBidListDto;
	}

	public BidList updateBidById(Integer id, BidList bidListUpdated)
			throws NullPointerException, IllegalArgumentException {
		BidList bidListToUpdate = new BidList();
		bidListToUpdate = bidListRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Bid " + id + " not found for updating"));
		if (bidListUpdated.getAccount() == null && bidListUpdated.getType() == null) {
			throw new IllegalArgumentException("Empty data of Bid " + id + " provided and updated");
		}
		bidListToUpdate.setAccount(bidListUpdated.getAccount());
		bidListToUpdate.setType(bidListUpdated.getType());
		bidListToUpdate.setBidQuantity(bidListUpdated.getBidQuantity());
		bidListToUpdate = bidListRepository.save(bidListToUpdate);
		return bidListToUpdate;
	}

	public void deleteBidById(Integer id)
			throws NullPointerException {
		bidListRepository.findById(id).orElseThrow(() -> new NullPointerException("Bid " + id + " not found for deleting"));
		bidListRepository.deleteById(id);
	}
}
