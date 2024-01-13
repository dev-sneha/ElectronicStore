package com.lcdw.electronic.store.services;

import com.lcdw.electronic.store.dtos.CreateOrderRequest;
import com.lcdw.electronic.store.dtos.OrderDto;
import com.lcdw.electronic.store.dtos.OrderUpdateRequest;
import com.lcdw.electronic.store.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(CreateOrderRequest orderDto);

    //remove order
    void removeOrder(String orderId);

    //get order of user

    List<OrderDto> getOrderOfUser(String orderId);

    //get orders
    PageableResponse<OrderDto> getOrders(int pageNumber,int pageSize,String sortBy,String sortDir);

    OrderDto updateOrder(String orderId, OrderUpdateRequest request);

}
