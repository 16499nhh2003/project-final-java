package com.project.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.spring.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal total;
    @Temporal(TemporalType.DATE)
    private Date date;
    @ManyToOne
    private AppUser user;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "order")
    private List<OrderDetail> orderDetails;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String name;
    private String address;
    private String note;
    private String phone;

}
