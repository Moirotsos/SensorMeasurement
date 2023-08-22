package com.Sensors.Measurement.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@Table(name = "measurement")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sensorId")
    private Sensor sensorId;
    @JoinColumn(name = "readingType")
    private String readingType;
    @JoinColumn(name = "readingValue")
    private Long readingValue;
    @JoinColumn(name = "readingDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    private Date readingDate;
    @JoinColumn(name = "time")
    private Time time;

    public Measurement(Sensor sensorId, String readingType, Long readingValue, Date readingDate, Time time) {
        this.sensorId = sensorId;
        this.readingType = readingType;
        this.readingValue = readingValue;
        this.readingDate = readingDate;
        this.time = time;
    }


}