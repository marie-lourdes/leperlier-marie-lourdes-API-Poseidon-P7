package com.nnk.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.service.CurvePointService;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	BidListService bidListService;

	@Autowired
	CurvePointService curvePointService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		BidList bid = new BidList("account test", "type", 10.2);
		bidListService.addBid(bid);

		CurvePoint curvePoint = new CurvePoint(12, 14.0, 10.0);
		curvePoint.setId(1);
		curvePointService.addCurvePoint(curvePoint);
	}
}
