package com.cts.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="cdnendpoint")
public class CDNEndpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long endpointID; // Primary Key

    private String name;
    private String baseURL;
    private String region;

    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status { Active, Inactive }

//    @OneToMany(cascade=CascadeType.ALL)
//    private List<MediaPackage> mediaPackage;

}

