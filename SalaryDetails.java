package com.example.SSGS.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "salary_details")
@Getter
@Setter
public class SalaryDetails {

    @Id
    @Column(name = "staff_id")
    private Integer staffId;

    @Column(name = "name")
    private String name;

    @Column(name = "designation")
    private String designation;

    @Column(name = "dept_section")
    private String deptSection;

    @Column(name = "e_basic")
    private BigDecimal eBasic;

    @Column(name = "e_da")
    private BigDecimal eDa;

    @Column(name = "e_hra")
    private BigDecimal eHra;

    @Column(name = "e_cca")
    private BigDecimal eCca;

    @Column(name = "e_ma")
    private BigDecimal eMa;

    @Column(name = "e_cps")
    private BigDecimal eCps;

    @Column(name = "d_cps")
    private BigDecimal dCps;

    @Column(name = "d_gpf")
    private BigDecimal dGpf;

    @Column(name = "d_aupf")
    private BigDecimal dAupf;

    @Column(name = "d_it")
    private BigDecimal dIt;

    @Column(name = "d_lic")
    private BigDecimal dLic;

    @Column(name = "d_pli")
    private BigDecimal dPli;

    @Column(name = "d_ib")
    private BigDecimal dIb;

    @Column(name = "d_nhis")
    private BigDecimal dNhis;

    @Column(name = "d_assocn")
    private BigDecimal dAssocn;

    @Column(name = "d_fa")
    private BigDecimal dFa;

    @Column(name = "d_spf")
    private BigDecimal dSpf;

    @Column(name = "d_fbf")
    private BigDecimal dFbf;

    @Column(name = "d_staffquaters")
    private BigDecimal dStaffQuaters;

    @Column(name = "d_salaryrecovery")
    private BigDecimal dSalaryRecovery;

    @Column(name = "d_cooptex")
    private BigDecimal dCooptex;

    @Column(name = "d_professionaltax")
    private BigDecimal dProfessionalTax;

    @Column(name = "gross_pay")
    private BigDecimal grossPay;

    @Column(name = "total_deduction")
    private BigDecimal totalDeduction;

    @Column(name = "net_pay")
    private BigDecimal netPay;

    @Column(name = "salary_month")
    private String salaryMonth;
}
