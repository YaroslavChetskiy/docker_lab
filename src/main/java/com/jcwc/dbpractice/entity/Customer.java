package com.jcwc.dbpractice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "email")
@ToString(exclude = "orders")
@Builder
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Builder.Default
    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();
}
