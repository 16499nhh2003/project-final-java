package com.project.spring.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class RevenueDayDTO {
    private Date date;
    private BigDecimal total;
}
