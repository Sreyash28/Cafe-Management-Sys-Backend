package com.cts.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.cms.model.OrderItems;

public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {

}
