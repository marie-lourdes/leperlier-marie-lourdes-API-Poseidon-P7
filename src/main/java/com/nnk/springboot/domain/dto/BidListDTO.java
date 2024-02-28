package com.nnk.springboot.domain.dto;

import lombok.Data;

@Data
public class BidListDTO {
	private Integer bidListId;
	private String account;
	private String type;
	private Double bidQuantity;

	public BidListDTO() {
	}

	public BidListDTO(Integer bidListId, String account, String type, Double bidQuantity) {
		this.bidListId = bidListId;
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}

	@Override
	public String toString() {
		return "BidListDTO{" + "bidListId:" + bidListId + ", account :" + account + ", type:" + type + ", bidQuantity:"
				+ bidQuantity + "}";
	}
}