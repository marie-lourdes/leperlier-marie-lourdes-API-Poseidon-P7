package com.nnk.springboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
@Autowired
BidListService bidListService;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

@Override
public void run(String... args) throws Exception {
	BidList bidCreated = new BidList("Account Test", "Type Test", 10d);
	bidListService.addbid(bidCreated,"admin");
	
	List<BidList> bids =bidListService.getAllBids();
	bids.forEach(bid -> System.out.println(bid));
}
}
