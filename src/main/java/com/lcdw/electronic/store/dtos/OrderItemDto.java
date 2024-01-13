package com.lcdw.electronic.store.dtos;


import com.lcdw.electronic.store.entities.Order;
import com.lcdw.electronic.store.entities.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderItemDto {

    private int orderItem;
    private int quantity;
    private int totalPrice;


    private ProductDto product;
}
