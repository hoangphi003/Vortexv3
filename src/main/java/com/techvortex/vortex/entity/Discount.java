package com.techvortex.vortex.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Table(name = "Discount")
@Entity
public class Discount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer DiscountId;

    @Nationalized
    @NotBlank(message = "Giảm giá không được bỏ trống!")
    private String DiscountName;

    @Nationalized
    @NotNull(message = "Mô tả không được bỏ trống!")
    private String Description;

    @NotNull(message = "Phần trăm không được bỏ trống")
    @Max(value = 99, message = "Phần trăm không được vượt quá 99")
    private Float Percents;

    @NotNull(message = "Chọn ngày bắt đầu")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date StartDay;

    @NotNull(message = "Chọn ngày kết thúc")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date EndDay;
    
    @NotNull(message = "Số lượng không được bỏ trống!")
    @Min(value = 1, message = "Số lượng không được nhỏ hơn một")
    private Integer Quantity;

    @OneToMany(mappedBy = "discount")
    List<OrderDiscount> OrderDiscounts;

}