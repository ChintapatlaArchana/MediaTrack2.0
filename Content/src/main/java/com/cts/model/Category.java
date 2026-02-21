package com.cts.model;


import jakarta.persistence.*;
import lombok.Data;


import java.util.List;


@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
    private String description;

//    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
//    private List<Title> title;


}
