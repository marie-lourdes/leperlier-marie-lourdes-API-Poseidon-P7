package com.nnk.springboot.domain;

import com.nnk.springboot.utils.ConstantsValidation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "rating")
public class Rating {
	// TODO: Map columns in data table RATING with corresponding java fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Pattern(regexp=ConstantsValidation.REGEX_CHARACTER, message=ConstantsValidation.ERROR_NOT_CHARACTER_MSG)
	@NotBlank(message="Moodys rating "+ConstantsValidation.ERROR_BLANK_MSG )
	@Column(name = " moodys_rating")
	private String moodysRating;
	
	@Pattern(regexp=ConstantsValidation.REGEX_CHARACTER, message=ConstantsValidation.ERROR_NOT_CHARACTER_MSG)
	@NotBlank(message="Sand "+ConstantsValidation.ERROR_BLANK_MSG )
	@Column(name = "sand_rating")
	private String sandRating;
	
	@Pattern(regexp=ConstantsValidation.REGEX_CHARACTER, message=ConstantsValidation.ERROR_NOT_CHARACTER_MSG)
	@NotBlank(message="Fitch rating "+ConstantsValidation.ERROR_BLANK_MSG )
	@Column(name = "fitch_rating") 
	private String fitchRating;
	
	@NotNull(message=ConstantsValidation.ERROR_NULL_MSG)
	@Positive(message="Order number must be > 0")
	@Column(name = "order_number")
	private Integer orderNumber;

	public Rating() {
	}

	public Rating(String moodysRating, String sandRating, String fitchRating, Integer orderNumber) {
		this.moodysRating = moodysRating;
		this.sandRating = sandRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}

	@Override
	public String toString() {
		return "Rating{" + "id:" + id + ", moodysRating :" + moodysRating + ", sandRating:" + sandRating
				+ ", fitchRating:" + fitchRating + ", orderNumber" + orderNumber + "}";
	}
}
