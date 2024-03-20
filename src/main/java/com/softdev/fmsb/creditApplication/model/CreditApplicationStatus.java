package com.softdev.fmsb.creditApplication.model;

public enum CreditApplicationStatus {
    ACTIVE("Activo"),
    FINISHED("Finalizado"),
    ON_HOLD_FOR_DICTAMEN("En espera de dictamen"),
    ACCEPTED("Confimado"),
    DENIED("Rechazado");

    private final String description;

    private CreditApplicationStatus (String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
