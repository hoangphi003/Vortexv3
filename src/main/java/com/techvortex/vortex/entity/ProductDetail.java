package com.techvortex.vortex.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Table(name = "ProductDetail")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productDetailId")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ProductDetailId;

    @ManyToOne
    @JoinColumn(name = "ProductId")
    Product product;

    @ManyToOne
    @JoinColumn(name = "ColorId")
    Color color;

    @ManyToOne
    @JoinColumn(name = "Material")
    Material material;

    @ManyToOne
    @JoinColumn(name = "Brand")
    Brand brand;

    @NotNull(message = "Không bỏ trống giá")
	@DecimalMin(value = "0.01", message = "Giá phải lớn hơn 0")
    private Integer InventoryQuantity;

    @JsonIgnore
    @OneToMany(mappedBy = "productDetails", cascade = CascadeType.ALL)
    List<Cart> carts;

    @JsonIgnore
    @OneToMany(mappedBy = "productDetails", cascade = CascadeType.ALL)
    List<WishList> wishLists;
    
    // Getter and setter for productDetailId
    public Integer getProductDetailId() {
        return this.ProductDetailId;
    }

    public void setProductDetailId(Integer productDetailId) {
        this.ProductDetailId = productDetailId;
    }

    // Getter and setter for product
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // Getter and setter for color
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // Getter and setter for material
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    // Getter and setter for brand
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    // Getter and setter for inventoryQuantity
    public Integer getInventoryQuantity() {
        return this.InventoryQuantity;
    }

    public void setInventoryQuantity(Integer inventoryQuantity) {
        this.InventoryQuantity = inventoryQuantity;
    }

    // Getter and setter for carts
    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }
}
