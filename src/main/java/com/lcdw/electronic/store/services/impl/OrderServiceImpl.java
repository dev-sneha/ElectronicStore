package com.lcdw.electronic.store.services.impl;

import com.lcdw.electronic.store.dtos.CreateOrderRequest;
import com.lcdw.electronic.store.dtos.OrderDto;
import com.lcdw.electronic.store.dtos.OrderUpdateRequest;
import com.lcdw.electronic.store.dtos.PageableResponse;
import com.lcdw.electronic.store.entities.*;
import com.lcdw.electronic.store.exceptions.BadApiRequestException;
import com.lcdw.electronic.store.exceptions.ResourceNotFoundException;
import com.lcdw.electronic.store.helper.Helper;
import com.lcdw.electronic.store.repositories.CartRepository;
import com.lcdw.electronic.store.repositories.OrderRepository;
import com.lcdw.electronic.store.repositories.UserRepository;
import com.lcdw.electronic.store.services.OrderService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {

        String userId= orderDto.getUserId();
        String cartId= orderDto.getCartId();
        User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found !!"));
        Cart cart= cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("cart not found !!"));
        List<CartItem> cartItems =cart.getCartItem();

        if (cartItems.size()<= 0  ){
            throw new BadApiRequestException("Invalid number of items in cart !!");
        }

        //other check
        Order order =Order.builder()
                .billingName(orderDto.getBillingName())
                .billingAddress(orderDto.getBillingAddress())
                .billingPhone(orderDto.getBillingPhone())
                .orderDate(new Date())
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOderStatus())
                .orderId(UUID.randomUUID().toString())
                .deliveredDate(null)
                .user(user).build();


        //orderitems, amount
        AtomicReference<Integer> orderAmount=new AtomicReference<>(0);
        List<OrderItem> orderItems= cartItems.stream().map(cartItem -> {

            OrderItem orderItem1= OrderItem
                    .builder().quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity()*cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();
            orderAmount.set(orderAmount.get()+ orderItem1.getTotalPrice());

            return orderItem1;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());
        System.out.println(order.getOrderItems().size());

        cart.getCartItem().clear();
        cartRepository.save(cart);
        Order saveOrder =orderRepository.save(order);
        return mapper.map(saveOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order is not found !!"));
        orderRepository.delete(order);

    }

    @Override
    public List<OrderDto> getOrderOfUser(String userId) {
        User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found !!"));
            List<Order> orders = orderRepository.findByUser(user);
            List<OrderDto> orderDtos = orders.stream().map(order -> mapper.map(order, OrderDto.class)).collect(Collectors.toList());
            return orderDtos;
        }




    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> page = orderRepository.findAll(pageable);
        return Helper.getPageableResponse(page, OrderDto.class);
    }

    @Override
    public OrderDto updateOrder(String orderId, OrderUpdateRequest request) {

        //get the order
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BadApiRequestException("Invalid update data"));
        order.setBillingName(request.getBillingName());
        order.setBillingPhone(request.getBillingPhone());
        order.setBillingAddress(request.getBillingAddress());
        order.setPaymentStatus(request.getPaymentStatus());
        order.setOrderStatus(request.getOrderStatus());
        order.setDeliveredDate(request.getDeliveredDate());
        Order updatedOrder = orderRepository.save(order);
        return mapper.map(updatedOrder, OrderDto.class);
    }
}
