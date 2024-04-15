package com.techvortex.vortex.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Table(name = "OrderDiscount")
@Entity
public class OrderDiscount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer OrderDiscountId;
 
    
    @ManyToOne 
    @JoinColumn(name = "OrderId")
    Order order;

    @ManyToOne 
    @JoinColumn(name = "DiscountId")
    Discount discount;

}
