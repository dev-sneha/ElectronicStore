package com.lcdw.electronic.store.services.impl;

import com.lcdw.electronic.store.dtos.AddItemToCartRequest;
import com.lcdw.electronic.store.dtos.CartDto;
import com.lcdw.electronic.store.entities.Cart;
import com.lcdw.electronic.store.entities.CartItem;
import com.lcdw.electronic.store.entities.Product;
import com.lcdw.electronic.store.entities.User;
import com.lcdw.electronic.store.exceptions.BadApiRequestException;
import com.lcdw.electronic.store.exceptions.ResourceNotFoundException;
import com.lcdw.electronic.store.repositories.CartItemRepository;
import com.lcdw.electronic.store.repositories.CartRepository;
import com.lcdw.electronic.store.repositories.ProductRepository;
import com.lcdw.electronic.store.repositories.UserRepository;
import com.lcdw.electronic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity= request.getQuantity();
        String productId= request.getProductId();
        if(quantity<=0){
            throw new BadApiRequestException("Requested quantity is not valid !!");
        }
        //fetch the product

        Product product=  productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("product not found !!"));
        //fetch the user from db
        User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found !!"));

        Cart cart= null;
        try{
            cart =cartRepository.findByUser(user).get();
        } catch(NoSuchElementException e){
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }
        //perform  cart opertaions
        // if cart items already present; then update

        AtomicReference<Boolean> update =new AtomicReference<>(false);
        List<CartItem> items =cart.getCartItem();
        items= items.stream().map(item-> {

            if(item.getProduct().getProductId().equals(productId)){
                //item already present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountedPrice());
                update.set(true);
            }
            return item;

        }).collect(Collectors.toList());

        if(!update.get()){
            CartItem cartItem= CartItem.builder().quantity(quantity)
                    .totalPrice(quantity*product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getCartItem().add(cartItem);

        }
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(()-> new ResourceNotFoundException("cart item is not found !!"));
        cartItemRepository.delete(cartItem1);




    }

    @Override
    public void clearCart(String userId) {
        User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found !!"));
        Cart cart= cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("Cart of given user not found !!"));
        cart.getCartItem().clear();
        cartRepository.save(cart);

    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found !!"));
        Cart cart= cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("Cart of given user not found !!"));
      return mapper.map(cart, CartDto.class);


    }
}
