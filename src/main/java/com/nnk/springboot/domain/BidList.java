package com.nnk.springboot.domain;

import java.sql.Timestamp;

import com.nnk.springboot.utils.ConstantsValidation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "bid_list")
public class BidList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bidlist_id")
	private Integer bidListId;

	@Pattern(regexp = ConstantsValidation.REGEX_CHARACTER, message = ConstantsValidation.ERROR_NOT_CHARACTER_MSG)
	@Size(min = ConstantsValidation.MIN_SIZE_ACCOUNTANDTYPE_DATA, max = ConstantsValidation.MAX_SIZE_ACCOUNTANDTYPE_DATA, message = ConstantsValidation.ERROR_SIZE_CHARACTER_ACCOUNTANDTYPE_MSG)
	@NotBlank(message = "Account " + ConstantsValidation.ERROR_BLANK_MSG)
	@Column(name = "account")
	private String account;

	@Pattern(regexp = ConstantsValidation.REGEX_CHARACTER, message = ConstantsValidation.ERROR_NOT_CHARACTER_MSG)
	@Size(min = ConstantsValidation.MIN_SIZE_ACCOUNTANDTYPE_DATA, max = ConstantsValidation.MAX_SIZE_ACCOUNTANDTYPE_DATA, message = ConstantsValidation.ERROR_SIZE_CHARACTER_ACCOUNTANDTYPE_MSG)
	@NotBlank(message = "Type " + ConstantsValidation.ERROR_BLANK_MSG)
	@Column(name = "type")
	private String type;

	@Positive(message = "bidQuantity " + ConstantsValidation.ERROR_NOT_POSITIVE_MSG)
	@Column(name = "bid_quantity")
	private Double bidQuantity;

	@Column(name = "sell_quantity")
	private Double sellQuantity;

	@Column(name = "bid")
	private Double bid;

	@Column(name = "ask")
	private Double ask;

	@Column(name = "benchmark")
	private String benchmark;

	@Column(name = "book")
	private String book;

	@Column(name = "bidlist_date")
	private Timestamp bidListDate;

	@Column(name = "commentary")
	private String commentary;

	@Column(name = "security")
	private String security;

	@Size(min = 0, max = 10)
	@Column(name = "status")
	private String status;

	@Column(name = "trader")
	private String trader;

	@Column(name = "creation_name")
	private String creationName;

	@Column(name = "creation_date")
	private Timestamp creationDate;

	@Column(name = " revision_name")
	private String revisionName;

	@Column(name = "revision_date")
	private Timestamp revisionDate;

	@Column(name = "deal_name ")
	private String dealName;

	@Column(name = " deal_type")
	private String dealType;

	@Column(name = " sourcelist_id ")
	private String sourcelistId;

	@Column(name = "side ")
	private String side;

	public BidList() {
	}

	public BidList(String account, String type, Double bidQuantity) {
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}
}