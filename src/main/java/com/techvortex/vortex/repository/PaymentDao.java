package com.techvortex.vortex.repository;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDao implements Serializable {
    private String status;
    private String message;
    private String URL;
}
