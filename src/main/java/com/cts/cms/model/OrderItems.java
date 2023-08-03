package com.cts.cms.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "order_items")
public class OrderItems implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_itemId")
	private long id;

	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonIgnoreProperties("orderItems")
	private Orders order;

	@ManyToOne
	@JoinColumn(name = "menu_id")
	@JsonIgnoreProperties("items")
	private Menu menu;

	private int quantity;

	public OrderItems(Orders order, Menu menu, int quantity) {
		super();
		this.order = order;
		this.menu = menu;
		this.quantity = quantity;
	}
}