package com.jcwc.dbpractice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"store", "number"})
@ToString(exclude = {"inventories", "store"})
@Builder
@Entity
@Table(name = "department", uniqueConstraints = @UniqueConstraint(columnNames = {"store_id", "number"}))
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer number;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    private Integer capacity;

    @Builder.Default
    @OneToMany(mappedBy = "department")
    private List<Inventory> inventories = new ArrayList<>();
}
