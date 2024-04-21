package com.techvortex.vortex.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Table(name = "OrderDiscount")
@Entity
public class OrderDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer OrderDiscountId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "OrderId")
    Order order;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "DiscountId")
    Discount discount;

}
