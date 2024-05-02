package com.softdev.fmsb.credit.model;

public enum CreditStatus {
    ACTIVE("Activo"),
    FINISHED("Finalizado");

    private final String description;

    private CreditStatus (String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
