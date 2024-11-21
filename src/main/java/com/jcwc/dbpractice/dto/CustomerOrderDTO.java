package com.jcwc.dbpractice.dto;

import com.jcwc.dbpractice.entity.Customer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CustomerOrderDTO {

    private Customer customer;
    private LocalDate orderDate;
}
