package com.lcdw.electronic.store.services;

import com.lcdw.electronic.store.dtos.AddItemToCartRequest;
import com.lcdw.electronic.store.dtos.CartDto;

public interface CartService {

    // add items to cart
    // case1; cart for user is not available : we will create and then add the item
    // case2: cart avai add the items to cart
    //also update quantity

    CartDto addItemToCart(String userId, AddItemToCartRequest request);


    //remove item form cart:

    void removeItemFromCart(String userId, int cartItem);

    //remove all items from cart

    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}
