package com.techvidyalaya.orderflow.repository;

import com.techvidyalaya.orderflow.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {}
