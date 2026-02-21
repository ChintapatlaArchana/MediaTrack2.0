package com.cts.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @Column(nullable = false)
    private String name;

    private double price;

    @Enumerated(EnumType.STRING)
    private BillingCycle billingCycle;

    @Lob
    private String entitlementsJSON;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum BillingCycle { Monthly, Yearly }
    public enum Status { Active, Inactive }

    public long getPlanId() {
        return planId;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BillingCycle getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(BillingCycle billingCycle) {
        this.billingCycle = billingCycle;
    }

    public String getEntitlementsJSON() {
        return entitlementsJSON;
    }

    public void setEntitlementsJSON(String entitlementsJSON) {
        this.entitlementsJSON = entitlementsJSON;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
