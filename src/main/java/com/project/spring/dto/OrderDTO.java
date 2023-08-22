package com.project.spring.dto;

import com.project.spring.enums.OrderStatus;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long idOrder;
    private Long idUser;
    private BigDecimal total;

    @Temporal(TemporalType.DATE)
    private Date date;

    private List<OrderDetailDTO> orderDetails;
    private String nameUser;
    private OrderStatus status;
    private String email;
    private String phone;
    private String address;
}
