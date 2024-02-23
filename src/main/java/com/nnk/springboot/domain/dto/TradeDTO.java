package com.nnk.springboot.domain.dto;

import lombok.Data;

@Data
public class TradeDTO {
	private Integer tradeId;
	private String account;
	private String type;
	private Double buyQuantity;

	public TradeDTO() {
	}

	public TradeDTO(Integer tradeId, String account, String type, Double buyQuantity) {
		this.tradeId = tradeId;
		this.account = account;
		this.type = type;
		this.buyQuantity = buyQuantity;
	}

	@Override
	public String toString() {
		return "TradeDTO{" + "tradeId:" + tradeId + ", account :" + account + ", type:" + type + ", buyQuantity:"
				+ buyQuantity + "}";
	}
}