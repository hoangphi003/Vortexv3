package com.techvortex.vortex.entity;

import lombok.Data;

@Data
public class CancelOrderForm {
    private String reason;
    // Add other form fields if needed

    // Getter and Setter for reason
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
