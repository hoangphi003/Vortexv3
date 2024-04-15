package com.techvortex.vortex.entity;

import java.io.Serializable;

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
@Table(name = "ReviewImg")
@Entity
public class ReviewImg implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ReviewsImgId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ReviewId")
    Review review;

    private String Image;

    public Integer getReviewsImgId() {
        return ReviewsImgId;
    }

    public void setReviewsImgId(Integer reviewsImgId) {
        ReviewsImgId = reviewsImgId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

}
