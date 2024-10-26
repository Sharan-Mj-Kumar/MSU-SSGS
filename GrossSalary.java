package com.example.SSGS.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "gross_salary")
public class GrossSalary {

    @Id
    private int staffId;

    @Column(nullable = false)
    private double basic;

    private double DA;
    private double HRA;
    private double CCA;
    private double MA;
    private double CPS;

}
