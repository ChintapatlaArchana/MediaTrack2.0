package com.cts.model;
import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.User;
import java.time.LocalDateTime;


@Entity
@Data
@Table(name="device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId; // Primary Key


    private Long userId;
    private LocalDateTime registeredDate;

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @Enumerated(EnumType.STRING)
    private Status status;



    public enum DeviceType { Web, Mobile, Tv }
    public enum Status { Active, Revoked }
}
