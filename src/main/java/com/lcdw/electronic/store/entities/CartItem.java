package com.lcdw.electronic.store.entities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name= "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;


    //(which product is in which cart does not matter)
    // matter is in which cartitem has what product


    @OneToOne
    @JoinColumn(name="product_id")
    private Product product;

    private int quantity;

    private int totalPrice;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;

}
