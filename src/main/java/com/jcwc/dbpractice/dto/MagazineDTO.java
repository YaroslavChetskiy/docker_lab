package com.jcwc.dbpractice.dto;

import lombok.Data;

import java.math.BigDecimal;

public record MagazineDTO(Integer id,
                          String title,
                          String isbn,
                          Integer publicationYear,
                          BigDecimal price) {
}