package com.jcwc.dbpractice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"magazineAuthors", "series", "publisher"})
@EqualsAndHashCode(of = "isbn")
@Builder
@Entity
@Table(name = "magazine")
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String isbn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    private Integer publicationYear;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Builder.Default
    @OneToMany(mappedBy = "magazine", cascade = CascadeType.ALL)
    private List<MagazineAuthor> magazineAuthors = new ArrayList<>();
}
