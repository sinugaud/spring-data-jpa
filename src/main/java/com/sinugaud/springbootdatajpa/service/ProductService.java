package com.sinugaud.springbootdatajpa.service;

import com.sinugaud.springbootdatajpa.enitty.Product;
import com.sinugaud.springbootdatajpa.exeptions.OrderItemNotFoundException;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product addProduct(Product product) throws OrderItemNotFoundException;
    void deleteProductById(Long id);

//    void processOrderPlacedEvent(Long orderId) throws InsufficientProductQuantityException;
}
