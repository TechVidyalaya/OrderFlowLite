package com.techvidyalaya.orderflow.config;

import com.techvidyalaya.orderflow.entity.CustomerOrder;
import com.techvidyalaya.orderflow.entity.Product;
import com.techvidyalaya.orderflow.repository.OrderRepository;
import com.techvidyalaya.orderflow.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;

@Configuration
public class DemoData {
    @Bean
    CommandLineRunner seed(ProductRepository products, OrderRepository orders) {
        return args -> {
            Product product = products.save(new Product("Wireless Mouse", new BigDecimal("599.00")));
            for (int i = 0; i < 10; i++) {
                CustomerOrder order = new CustomerOrder();
                order.addItem(product, i + 1);
                orders.save(order);
            }
        };
    }
}
