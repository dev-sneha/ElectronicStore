package com.lcdw.electronic.store.services.impl;

import com.lcdw.electronic.store.dtos.AddItemToCartRequest;
import com.lcdw.electronic.store.dtos.CartDto;
import com.lcdw.electronic.store.repositories.CartRepository;
import com.lcdw.electronic.store.repositories.ProductRepository;
import com.lcdw.electronic.store.repositories.UserRepository;
import com.lcdw.electronic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity= request.getQuantity();
        String productId= request.getProductId();
        //fetch the product

        //fetch the user from db
        return null;
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {

    }

    @Override
    public void clearCart(String userId) {

    }
}
