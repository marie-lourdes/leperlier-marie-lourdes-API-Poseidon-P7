package com.nnk.springboot.domain.dto;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.BidList;

@Component
public class Mapper {
	public BidListDTO BidListDTOToBidListDTO(BidList bidList) {
		Integer id =bidList.getId(); 	
		String account =bidList.getAccount();	
		String type = bidList.getType(); 
		Double bidQuantity=bidList.getBidQuantity(); 

		return new BidListDTO(id, account, type, bidQuantity);
	}
}
