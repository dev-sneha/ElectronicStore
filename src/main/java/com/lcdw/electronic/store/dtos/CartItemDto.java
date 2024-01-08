package com.lcdw.electronic.store.dtos;

import com.lcdw.electronic.store.entities.Cart;
import com.lcdw.electronic.store.entities.Product;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private int cartItemId;



    private ProductDto product;

    private int quantity;

    private int totalPrice;




}
