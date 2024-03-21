package com.softdev.fmsb.creditType.model;

public enum CreditState {
    Activo("Activo"),
    Inactivo("Inactivo");

    private final String description;

    CreditState(String description) {
        this.description = description;
    }
}
