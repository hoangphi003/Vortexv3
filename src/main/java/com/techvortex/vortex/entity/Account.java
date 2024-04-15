package com.techvortex.vortex.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "Account")
@Entity
public class Account implements Serializable {
    @Id
    @Nationalized
    private String UserName;

    @Nationalized
    private String FullName;

    private String Password;

    // Dùng kiểu String để lưu được số 0
    private String Phone;

    private String Email;

    private Boolean Active;

    @Temporal(TemporalType.DATE)
    private Date Birth;

    private Boolean Gender;

    private String Avatar;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Authority> authorities;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    List<Review> reviews;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    List<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    List<Cart> carts;

    @Override
    public String toString() {
        return "Account [UserName=" + UserName + ", FullName=" + FullName + ", Password=" + Password + ", Phone="
                + Phone + ", Email=" + Email + ", Active=" + Active + ", Birth=" + Birth + ", Gender=" + Gender
                + ", Avatar=" + Avatar + ", authorities=" + authorities + ", reviews=" + reviews + ", orders=" + orders
                + ", carts=" + carts + "]";
    }

}
