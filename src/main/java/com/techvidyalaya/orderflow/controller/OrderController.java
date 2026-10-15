package com.techvidyalaya.orderflow.controller;

import com.techvidyalaya.orderflow.service.OrderQueryService;
import com.techvidyalaya.orderflow.service.OrderQueryService.QueryResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class OrderController {
    private final OrderQueryService service;
    public OrderController(OrderQueryService service) { this.service = service; }

    @GetMapping("/orders/n-plus-one")
    public QueryResult reproduceNPlusOne() {
        return service.listOrdersBroken();
    }
}
