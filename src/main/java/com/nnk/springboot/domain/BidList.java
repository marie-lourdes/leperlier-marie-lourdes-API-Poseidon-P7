package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "bid_list")
public class BidList {
	// TODO: Map columns in data table BIDLIST with corresponding java fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bidlist_id")
	private Integer id;
	
	@NotNull
	@Column(name = "account")
	private String account;
	
	@NotNull
	@Column(name = "type")
	private String type;

	@Column(name = "bid_quantity ")
	private Double bidQuantity;
	
	@Column(name = "ask_quantity ")
	private Double askQuantity;
	
	@Column(name = "bid")
	private Double bid;
	
	@Column(name = "ask")
	private Double ask;
	
	@Column(name = "benchmark")
	private String benchmark;
	
	@Column(name = "book")
	private String book;

	@Column(name = "bidlist_date")
	private  Timestamp bidListDate;
	
	@Column(name = "commentary")
	private String commentary;
	
	@Column(name = "security")
	private String security;
	
	@Column(name = "status")
	private  String status;
		
	@Column(name = "trader")
	 private  String trader;
	 
	@Column(name = "creation_name")
	private String creationName;
	
	@Column(name = "creation_date")
	private Timestamp creationDate;
	
	@Column(name = " revision_name")
	private String  revisionName;
	
	@Column(name = "revision_date")
	private Timestamp revisionDate;
	
	@Column(name = "deal_name ")
	private String dealName ;
	
	@Column(name = " deal_type")
	private String  dealType;
	
	@Column(name = " sourcelist_id ")
	private String  sourcelistId ;
	
	@Column(name = "side ")
	private String side ;
	
	public BidList() {}
	
	public BidList(String account, String type, Double bidQuantity) {
		this.account=account;
		this.type=type;
		this.bidQuantity=bidQuantity;
	}
}
