package com.nnk.springboot.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeDTO;
import com.nnk.springboot.domain.dto.TradeMapperImpl;
import com.nnk.springboot.repositories.ITradeRepository;

@Service
public class TradeService {
	private static final Logger log = LogManager.getLogger(TradeService.class);

	@Autowired
	private ITradeRepository tradeRepository;

	@Autowired
	private TradeMapperImpl mapper;

	public Trade addTrade(Trade tradeCreated /* String username */) throws NullPointerException {
		Trade tradeRegistered = new Trade();
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		try {
			if (tradeCreated != null) {
				if (tradeCreated.getAccount() == null && tradeCreated.getType() == null) {
					throw new IllegalArgumentException("Empty data of Bid " + tradeCreated + " provided and created");
				}
				tradeRegistered.setAccount(tradeCreated.getAccount());
				tradeRegistered.setType(tradeCreated.getType());
				tradeRegistered.setBuyQuantity(tradeCreated.getBuyQuantity());
				tradeRegistered.setCreationName("bid");
				tradeRegistered.setCreationDate(timestamp);
				// tradeRegistered.setTrader(username);
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		}

		tradeRegistered = tradeRepository.save(tradeRegistered);
		return tradeRegistered;
	}

	public TradeDTO getTradeById(Integer id) throws NullPointerException {
		Trade tradeFoundById = tradeRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Trade " + id + " not found"));
		TradeDTO tradeDTO = mapper.tradeToTradeDTO(tradeFoundById);
		return tradeDTO;
	}

	public List<TradeDTO> getAllTrades() throws NullPointerException {
		List<Trade> allTrades = tradeRepository.findAll();
		List<TradeDTO> allTradeDto = new ArrayList<TradeDTO>();
		if (allTrades.isEmpty()) {
			return new ArrayList<>();
		}
		allTrades.forEach(bid -> {
			allTradeDto.add(mapper.tradeToTradeDTO(bid));
		});
		return allTradeDto;
	}

	public Trade updateTradeById(Integer id, Trade tradeUpdated) throws NullPointerException, IllegalArgumentException {
		Trade tradeToUpdate = new Trade();
		tradeToUpdate = tradeRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Trade" + id + " not found for updating"));

		tradeToUpdate.setAccount(tradeUpdated.getAccount());
		tradeToUpdate.setType(tradeUpdated.getType());
		tradeToUpdate.setBuyQuantity(tradeUpdated.getBuyQuantity());
		tradeToUpdate = tradeRepository.save(tradeToUpdate);
		return tradeToUpdate;
	}

	public void deleteTradeById(Integer id) throws NullPointerException {
		tradeRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Trade" + id + " not found for deleting"));
		tradeRepository.deleteById(id);
	}
}
