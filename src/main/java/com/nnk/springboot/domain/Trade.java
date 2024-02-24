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
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "trade")
public class Trade {
	// TODO: Map columns in data table TRADE with corresponding java fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trade_id")
	private Integer tradeId;

	@Size(
			min = ConstantsValidation.MIN_SIZE_ACCOUNTANDTYPE_DATA,
			max = ConstantsValidation.MAX_SIZE_ACCOUNTANDTYPE_DATA
			)
	@NotBlank(message = "Account "+ConstantsValidation.ERROR_BLANK_MSG )
	@Column(name = "account")
	private String account;

	@Size(
			min = ConstantsValidation.MIN_SIZE_ACCOUNTANDTYPE_DATA,
			max = ConstantsValidation.MAX_SIZE_ACCOUNTANDTYPE_DATA
			)
	@NotBlank(message = "Type "+ConstantsValidation.ERROR_BLANK_MSG )
	@Column(name = "type")
	private String type;

	@Positive(message="Trade "+ConstantsValidation.ERROR_NOT_POSITIVE_MSG)
	@Column(name = "buy_quantity")
	private Double buyQuantity;

	@Column(name = "sell_quantity")
	private Double sellQuantity;

	@Column(name = "buy_price")
	private Double buyPrice;

	@Column(name = " sell_price")
	private Double sellPrice;

	@Column(name = " trade_date ")
	private Timestamp tradeDate;

	@Column(name = "security ")
	private String security;

	@Size(min = 0, max = 10)
	@Column(name = "status ")
	private String status;

	@Column(name = "trader")
	private String trader;

	@Column(name = "benchmark")
	private String benchmark;

	@Column(name = "book")
	private String book;

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

	public Trade() {}

	public Trade(String account, String type) {
		this.account = account;
		this.type = type;
	}

}
