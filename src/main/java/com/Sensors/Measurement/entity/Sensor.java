package com.Sensors.Measurement.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Table(name = "sensorDetails")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@NonNull
public class Sensor {

    @Id
    @Column(name = "sensorId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sensorId;
    @Column(name = "type")
    private String type;
    @Column(name = "vendorName")
    private String vendorName;
    @Column(name = "vendorEmail")
    private String vendorEmail;
    @Column(name = "description")
    private String description;
    @Column(name = "location")
    private String location;
    @Column(name = "price")
    private String price;
    @OneToMany(mappedBy = "sensorId", cascade = CascadeType.ALL)
    private List<Measurement> measurements;

    public Sensor(String type, String vendorName, String vendorEmail, String description, String location, String price) {
        this.type = type;
        this.vendorName = vendorName;
        this.vendorEmail = vendorEmail;
        this.description = description;
        this.location = location;
        this.price = price;
    }
}