package com.eukolos.product.service;

import com.eukolos.product.dto.ProductAddRequest;
import com.eukolos.product.dto.ProductDto;
import com.eukolos.product.exception.ProductNotFoundException;
import com.eukolos.product.model.Product;
import com.eukolos.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
    public ProductDto addProduct(ProductAddRequest request) {
        return ProductDto.convertToProductDto(repository.save(ProductAddRequest.convertToProduct(request)));
    }

    public List<ProductDto> getProductList() {
        return ProductDto.convertToProductDtoList(repository.findAll());
    }

    public List<ProductDto> getProductListByOem(String oem) {
        return ProductDto.convertToProductDtoList(repository.findProductByOemListContains(oem)
                .orElseThrow(()-> new ProductNotFoundException("Product which searching by " + oem + " Not Found! " )));
    }

    public ProductDto getProduct(String id) {
        return ProductDto.convertToProductDto(repository.findById(id)
                        .orElseThrow(()-> new ProductNotFoundException("Product Not Found! " + id)));
    }

    public ProductDto updateProduct(String id, ProductAddRequest request) {
        Product product = repository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product Not Found! " + id));
        product.setName(request.name());
        product.setAmount(request.amount());
        product.setPrice(request.price());
        product.setOemList(request.oemList());
        product.setUpdateDate((Date.valueOf(LocalDate.now())));
        return ProductDto.convertToProductDto(product);
    }

    public Boolean deleteProduct(String id){
        return repository.delete(id)
                .orElseThrow(()-> new ProductNotFoundException("Product Not Found! " + id));
    }


}
