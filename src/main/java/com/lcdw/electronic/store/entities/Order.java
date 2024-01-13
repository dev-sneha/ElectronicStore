package com.lcdw.electronic.store.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    private String orderId;

    //pending //dispatch // delivered

    private String orderStatus;

    //NOT_PAID ,PAID
    //enum//

    //boolean-false=>NOTPAID || true=> PAID
    private String paymentStatus;

    private int OrderAmount;

    @Column(length=1000)
    private String billingAddress;

    private String billingPhone;
    private String billingName;
    private Date orderDate;

    private Date deliveredDate;


    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name= "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();


}
