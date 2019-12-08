package com.paulohva.bustracker.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

public class Operator implements Serializable {

    @Id
    private String operator;

    public Operator(String operator) {
        this.operator = operator;
    }

    public Operator() {
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
