package com.cts.model;


import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name = "title")
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long titleId;
    private String name;
    private String synopsis;
    private String genre;
    private LocalDate releaseDate;
    private String rating;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "title_categoryId",nullable = false)
    private Category category;



    public enum ApplicationStatus {
        coming_soon,
        available,
        expired
    }
}
