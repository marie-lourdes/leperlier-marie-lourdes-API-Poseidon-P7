package com.nnk.springboot.domain.dto;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.Trade;

@Component
public class TradeMapperImpl implements IMapper<TradeDTO, Trade> {

	public TradeDTO tradeToTradeDTO(Trade trade) {
		return this.entityToObjectDTO(trade);
	}

	@Override
	public TradeDTO entityToObjectDTO(Trade trade) {
		Integer tradeId= trade.getTradeId();
		String account = trade.getAccount();
		String type = trade.getType();
		Double buyQuantity = trade.getBuyQuantity();

		return new TradeDTO(tradeId, account, type, buyQuantity);
	}
}