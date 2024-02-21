package com.nnk.springboot.domain.dto;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.BidList;

@Component
public class BidListMapperImpl implements IMapper<BidListDTO,BidList> {
	
	public BidListDTO bidListToBidListDTO(BidList bidList) {
		return this.entityToObjectDTO(bidList);
	}
	
	@Override
	public BidListDTO entityToObjectDTO(BidList bidList) {
		Integer id =bidList.getId(); 	
		String account =bidList.getAccount();	
		String type = bidList.getType(); 
		Double bidQuantity=bidList.getBidQuantity(); 

		return new BidListDTO(id, account, type, bidQuantity);
	}
}
