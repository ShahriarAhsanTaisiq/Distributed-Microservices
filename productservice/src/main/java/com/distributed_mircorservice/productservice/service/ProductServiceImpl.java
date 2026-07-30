package com.distributed_mircorservice.productservice.service;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {


    @Override
    public String getProductDetails(Long productId) {

        return "Product details is fetched successfully. Product Id: " + productId;
    }
}
