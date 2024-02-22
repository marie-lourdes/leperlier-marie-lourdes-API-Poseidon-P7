package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

	@Column(name = " moodys_rating")
	private String moodysRating;

	@Column(name = "sand_rating")
	private String sandRating;

	@Column(name = "fitch_rating")
	private String fitchRating;

	@Column(name = "order_number")
	private String orderNumber;

	public Rating() {
	}

	@Override
	public String toString() {
		return "Rating{" + "id:" + id + ", moodysRating :" + moodysRating + ", sandRating:" + sandRating
				+ ", fitchRating:" + fitchRating + ", orderNumber" + orderNumber + "}";
	}
}
