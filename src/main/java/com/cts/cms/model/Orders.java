package com.cts.cms.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Orders implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties("order")
	private Users user;

	@Column(name = "date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime orderDate;

	@Column(name = "total_price")
	private double totalPrice;

	@OneToMany(mappedBy = "order")
	@JsonIgnoreProperties("order")
	private List<OrderItems> orderItems = new ArrayList<>();

	public Orders(Users user, LocalDateTime orderDate, double totalPrice, List<OrderItems> orderItems) {
		super();
		this.user = user;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
		this.orderItems = orderItems;
	}

	public Orders(Users user, List<OrderItems> orderItems) {
		super();
		this.user = user;
		this.orderItems = orderItems;
	}
}
