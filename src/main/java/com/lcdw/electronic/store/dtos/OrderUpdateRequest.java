package com.lcdw.electronic.store.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderUpdateRequest {

    private String orderStatus;
    private String paymentStatus;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date deliveredDate;

}
