package com.cts.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable=false)
    private String password;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Role {
        Viewer,Editor,AdOps,Operator,Admin
    }

    public enum Status {
        ACTIVE, INACTIVE
    }

    //    @JsonIgnoreProperties("user")
//    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Subscription> subscription;
//
//    @JsonIgnoreProperties("user")
//    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Entitlement> entitlement;
//
//    @JsonIgnoreProperties("user")
//    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Device> device;
//
//    @JsonIgnoreProperties("user")
//    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Notification> notification;
//
//    @JsonIgnoreProperties("user")
//    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<PlaybackSession> playbackSession;

}
