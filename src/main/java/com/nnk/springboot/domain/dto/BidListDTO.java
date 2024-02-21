package com.nnk.springboot.domain.dto;

import lombok.Data;

@Data
public class BidListDTO {
	private Integer id;
	private String account;
	private String type;
	private Double bidQuantity;
	
	public BidListDTO(){} 
	
	public BidListDTO(Integer id,String account,String type,Double bidQuantity){
		this.id=id;
		this.account=account;
		this.type=type;
		this.bidQuantity=bidQuantity;
	} 

	@Override
	public String toString() {
		return "BidListDTO{" + "id:" + id + ", account :" + account  + ", type:" + type+", bidQuantity:" + bidQuantity +"}";
	}
}
