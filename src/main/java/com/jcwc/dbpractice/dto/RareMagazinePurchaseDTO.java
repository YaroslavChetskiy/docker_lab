package com.jcwc.dbpractice.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RareMagazinePurchaseDTO {
    private Integer storeId;
    private String storeName;
    private Integer magazineId;
    private String magazineTitle;
    private Long customerId;
    private String customerFullName;
}
