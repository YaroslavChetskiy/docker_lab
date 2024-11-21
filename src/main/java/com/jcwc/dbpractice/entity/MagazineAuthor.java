package com.jcwc.dbpractice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "magazine_author",
        uniqueConstraints = @UniqueConstraint(columnNames = {"magazine_id", "author_id"}))
public class MagazineAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magazine_id", nullable = false)
    private Magazine magazine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
        this.magazine.getMagazineAuthors().add(this);
    }

    public void setAuthor(Author author) {
        this.author = author;
        this.author.getMagazineAuthors().add(this);
    }
}
