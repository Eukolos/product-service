package com.eukolos.product.dto;

import com.eukolos.product.model.Product;

import java.util.List;

public record ProductAddRequest(
        String name,
        double price,
        double amount,
        List<String> oemList
) {
    public static Product convertToProduct(ProductAddRequest request){
        return new Product(
                request.name,
                request.price,
                request.amount,
                request.oemList
        );
    }
}
