package com.sinugaud.springbootdatajpa.service.impl;

import com.sinugaud.springbootdatajpa.exeptions.OrderItemNotFoundException;
import com.sinugaud.springbootdatajpa.exeptions.ResourceNotFoundException;
import com.sinugaud.springbootdatajpa.repository.ProductRepository;
import com.sinugaud.springbootdatajpa.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }


    @Override
    public Product addProduct(Product product) throws OrderItemNotFoundException {
        return productRepository.save(product);


    }

    @Override
    public void deleteProductById(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow();

        productRepository.delete(existingProduct);
    }

//    @Override
//    public void processOrderPlacedEvent(Long orderId) throws InsufficientProductQuantityException {
//        // Fetch order details from an OrderDTO service through API or event
//       log.info("order place event");
//        OrderDTO orderDto = orderServiceClient.getOrderById(orderId);
//        log.info("Order dto :{}",orderDto);
//
//        // Process order items and update products
//        for (OrderItem orderItem : orderDto.getOrderItems()) {
//            Long productId = orderItem.getProductId();
//            Double quantity = orderItem.getQuantity();
//
//            // Fetch product details from your ProductService
//            Product product = productRepository.findById(productId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id ,{}" + productId));
//            log.info("product  from event find by id {},", product);
//            // Update the product quantity or perform any other relevant action
//            double remainingQuantity = (product.getQuantity() - quantity);
//            log.info(" quantity {}",quantity);
//
////            if (remainingQuantity >= 0) {
//                product.setQuantity(remainingQuantity);
//                log.info("remaining quantity {}",remainingQuantity);
//
//                productRepository.save(product);
//                log.info("product update Successfully {}",product.getId());
////            } else {
////                // Handle insufficient quantity scenario
////                // You might also want to implement compensation actions if needed
////                log.info("product not update Successfully {}",product.getId());
//
//                throw new InsufficientProductQuantityException("Insufficient quantity for product with id " + productId);
////            }
//        }
//
//    }

}
