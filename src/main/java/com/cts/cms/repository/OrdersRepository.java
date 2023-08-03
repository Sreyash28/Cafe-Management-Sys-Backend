package com.cts.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.cms.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
