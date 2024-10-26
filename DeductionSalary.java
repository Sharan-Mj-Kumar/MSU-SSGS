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
@Table(name= "deduction_salary")
public class DeductionSalary {

    @Id
    @Column(name = "staff_id")
    private int staffId;

    @Column(name = "cps")
    private Double cps;

    @Column(name = "gpf")
    private Double gpf;

    @Column(name = "adjustment_upf")
    private Double adjustmentUpf;

    @Column(name = "it")
    private Double it;

    @Column(name = "lic")
    private Double lic;

    @Column(name = "pli")
    private Double pli;

    @Column(name = "ib")
    private Double ib;

    @Column(name = "nhis")
    private Double nhis;

    @Column(name = "assocn")
    private Double assocn;

    @Column(name = "pf_rec")
    private Double pfRec;

    @Column(name = "fa")
    private Double fa;

    @Column(name = "spf")
    private Double spf;

    @Column(name = "fbf")
    private Double fbf;

    @Column(name = "staff_quarters")
    private Double staffQuarters;

    @Column(name = "salary_recovery")
    private Double salaryRecovery;

    @Column(name = "co_optex")
    private Double coOptex;

    @Column(name = "professional_tax")
    private Double professionalTax;

}
