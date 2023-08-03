package com.cts.cms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private int id;

	@Column(name = "name")
	private String itemName;

	@Column(name = "price")
	private Double itemPrice;

	@OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("menu")
	private List<OrderItems> items = new ArrayList<>();

	public Menu(String itemName, Double itemPrice, List<OrderItems> items) {
		super();
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.items = items;
	}

	public Menu(String itemName, Double itemPrice) {
		super();
		this.itemName = itemName;
		this.itemPrice = itemPrice;
	}

}
