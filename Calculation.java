package com.example.SSGS.Tables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Calculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "da_percentage")
    private double daPercentage;

    @Column(name = "cps_percentage")
    private double cpsPercentage;

    @Column(name = "gpfPercentage")
    private double gpfPercentage;


}
