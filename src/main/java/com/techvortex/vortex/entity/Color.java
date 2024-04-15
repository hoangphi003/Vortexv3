package com.techvortex.vortex.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Table(name = "Color")
@Entity
public class Color implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ColorId;

    @Nationalized
    @NotBlank(message = "Tên Màu không được để trống") // Thêm thông báo lỗi
    private String ColorName;

    @JsonIgnore
    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
    List<ProductDetail> productDetails;

    public Integer getColorId() {
        return ColorId;
    }

    public void setColorId(Integer colorId) {
        ColorId = colorId;
    }

    public String getColorName() {
        return ColorName;
    }

    public void setColorName(String colorName) {
        ColorName = colorName;
    }

    public List<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }

}
