package com.sinugaud.springbootdatajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private Long id;

    private OrderDTO order;

    private Long productId;

    private Double quantity;

    private double price;

}
