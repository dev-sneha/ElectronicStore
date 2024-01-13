package com.lcdw.electronic.store.dtos;

import com.lcdw.electronic.store.entities.OrderItem;
import com.lcdw.electronic.store.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class OrderDto {

    private String orderId;

    //pending //dispatch // delivered

    private String orderStatus="PENDING";

    //NOT_PAID ,PAID
    //enum//

    //boolean-false=>NOTPAID || true=> PAID
    private String paymentStatus="NOTPAID";

    private int OrderAmount;
    private String billingAddress;

    private String billingPhone;
    private String billingName;
    private Date orderDate=new Date();
    private Date deliveredDate;
    private  UserDto user;

    private List<OrderItemDto> orderItems = new ArrayList<>();
}
