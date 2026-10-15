package com.techvidyalaya.orderflow.service;

import com.techvidyalaya.orderflow.entity.CustomerOrder;
import com.techvidyalaya.orderflow.repository.OrderRepository;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderQueryService {
    private final OrderRepository orders;
    private final EntityManagerFactory entityManagerFactory;

    public OrderQueryService(OrderRepository orders, EntityManagerFactory entityManagerFactory) {
        this.orders = orders;
        this.entityManagerFactory = entityManagerFactory;
    }

    // INTENTIONALLY BAD: loads all orders, then lazily loads each order's items.
    @Transactional(readOnly = true)
    public QueryResult listOrdersBroken() {
        Statistics stats = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        // Note: statistics are global; query count is reliable in a single-user local demo only.
        stats.clear();
        List<CustomerOrder> results = orders.findAll();
        List<OrderSummary> summaries = results.stream()
            .map(order -> new OrderSummary(order.getId(), order.getItems().size()))
            .toList();
        return new QueryResult(summaries, stats.getPrepareStatementCount());
    }

    public record OrderSummary(Long orderId, int itemCount) {}
    public record QueryResult(List<OrderSummary> orders, long sqlStatements) {}
}
