package com.distributed_mircorservice.orderservice.model;

public class Order {

    private Integer id;
    private Integer productId;
    private Integer quantity;

    public Order() {
    }

    public Order(
            Integer id,
            Integer productId,
            Integer quantity) {

        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}