package com.paulohva.bustracker.dto;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class Fleet implements Serializable {

    @Id
    private String operator;

    public Fleet(String operator) {
        this.operator = operator;
    }

    public Fleet() {
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
