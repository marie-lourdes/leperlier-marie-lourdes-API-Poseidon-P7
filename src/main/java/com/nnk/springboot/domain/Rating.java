package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	
	@NotBlank(message="Moodys rating is mandatory")
	@Column(name = " moodys_rating")
	private String moodysRating;
	
	@NotBlank(message="Sand rating is mandatory")
	@Column(name = "sand_rating")
	private String sandRating;
	
	@NotBlank(message="Fitch rating is mandatory")
	@Column(name = "fitch_rating") 
	private String fitchRating;
	
	@NotNull(message="must be not null")
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
