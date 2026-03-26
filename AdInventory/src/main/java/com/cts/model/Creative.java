package com.cts.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "creative")
@Data
public class Creative {

    public enum Status { Active, Paused, Expired }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creativeId;

    private String advertiser;
    private String mediaUri;
    private Integer duration;
    private String clickThroughUrl;

    @Enumerated(EnumType.STRING)
    private Status status;

//    @OneToMany(mappedBy = "creative", cascade = CascadeType.ALL)
//    private List<Campaign> campaign;
}