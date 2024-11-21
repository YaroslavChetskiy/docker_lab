package com.jcwc.dbpractice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreCustomerCountDTO {

    private Integer storeId;
    private String storeName;
    private Integer customerCount;
}
