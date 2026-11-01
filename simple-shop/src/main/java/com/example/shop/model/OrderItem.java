package com.example.shop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private BigDecimal price;
    private int quantity;

    public OrderItem() {}

    public OrderItem(String productName, BigDecimal price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() { return id; }
    public String getProductName() { return productName; }
    public BigDecimal getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
